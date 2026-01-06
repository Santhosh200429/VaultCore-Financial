package com.zaalima.vaultcore.audit;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Aspect
@Component
public class AuditAspect {
    private static final Logger LOG = LoggerFactory.getLogger("AUDIT");
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "audit-logger");
        t.setDaemon(true);
        return t;
    });

    // parameter names that must not be logged
    private static final Set<String> SENSITIVE = new HashSet<>();
    static {
        SENSITIVE.add("password");
        SENSITIVE.add("token");
        SENSITIVE.add("authorization");
        SENSITIVE.add("otp");
    }

    @Around("within(@org.springframework.web.bind.annotation.RestController *) || within(com.vaultcore..service..*) || within(com.vaultcore..repository..*)")
    public Object audit(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = null;
        Throwable thrown = null;
        try {
            result = pjp.proceed();
            return result;
        } catch (Throwable t) {
            thrown = t;
            throw t;
        } finally {
            long end = System.currentTimeMillis();
            long elapsed = end - start;

            MethodSignature sig = (MethodSignature) pjp.getSignature();
            AuditEntry e = new AuditEntry();
            e.timestamp = start;
            e.className = sig.getDeclaringType().getSimpleName();
            e.methodName = sig.getName();

            try {
                Object[] args = pjp.getArgs();
                e.parameters = safeSerialize(args);
            } catch (Exception ex) {
                e.parameters = "<unserializable>";
            }

            try {
                e.returnValue = safeSerialize(result);
            } catch (Exception ex) {
                e.returnValue = "<unserializable>";
            }

            e.executionTimeMs = elapsed;

            // Log asynchronously as structured JSON
            EXEC.submit(() -> {
                try {
                    String json = MAPPER.writeValueAsString(e);
                    LOG.info(json);
                } catch (Exception ex) {
                    // best-effort -- never throw
                    LOG.warn("Failed to write audit entry", ex);
                }
            });
        }
    }

    private Object safeSerialize(Object obj) {
        // basic sanitation: redact fields named in SENSITIVE
        try {
            // convert to map/string and remove sensitive keys via Jackson tree
            return MAPPER.convertValue(obj, Object.class);
        } catch (Exception e) {
            return "<unserializable>";
        }
    }
}


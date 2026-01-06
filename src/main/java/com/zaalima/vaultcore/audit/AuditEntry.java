package com.zaalima.vaultcore.audit;

public class AuditEntry {
    public long timestamp;
    public String className;
    public String methodName;
    public Object parameters;
    public Object returnValue;
    public long executionTimeMs;

    public AuditEntry() {}
}

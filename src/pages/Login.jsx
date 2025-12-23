import { useState } from "react";
import { login } from "../api/authApi";

export default function Login({ onLoginSuccess }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      const res = await login({ email, password });
      onLoginSuccess(res.data.accessToken);
      alert("Login Success");
    } catch {
      alert("Invalid email or password");
    }
  };

  return (
    <div>
      <h2>VaultCore Login</h2>

      <input
        placeholder="Email"
        value={email}
        onChange={e => setEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={e => setPassword(e.target.value)}
      />

      <button onClick={handleLogin}>Login</button>
    </div>
  );
}

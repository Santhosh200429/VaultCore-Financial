<<<<<<< HEAD
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
=======
import { login } from "../api/authApi";

export default function Login() {

  const handleLogin = async () => {
    const res = await login({
      email: "user@vaultcore.com",
      password: "dummy"
    });
    localStorage.setItem("token", res.data.accessToken);
    alert("Login Success");
>>>>>>> 7ba6446bea7b624c6a4ef7b9fe3ee466670c32ce
  };

  return (
    <div>
      <h2>VaultCore Login</h2>
<<<<<<< HEAD

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

=======
>>>>>>> 7ba6446bea7b624c6a4ef7b9fe3ee466670c32ce
      <button onClick={handleLogin}>Login</button>
    </div>
  );
}

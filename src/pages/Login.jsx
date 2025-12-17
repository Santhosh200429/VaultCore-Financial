import { login } from "../api/authApi";

export default function Login() {

  const handleLogin = async () => {
    const res = await login({
      email: "user@vaultcore.com",
      password: "dummy"
    });
    localStorage.setItem("token", res.data.accessToken);
    alert("Login Success");
  };

  return (
    <div>
      <h2>VaultCore Login</h2>
      <button onClick={handleLogin}>Login</button>
    </div>
  );
}

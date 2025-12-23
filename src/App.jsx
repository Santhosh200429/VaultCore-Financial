import { useState } from "react";
import Login from "./pages/Login";
import SendMoney from "./pages/SendMoney";

function App() {
  const [token, setToken] = useState(localStorage.getItem("token"));

  const handleLoginSuccess = (jwt) => {
    localStorage.setItem("token", jwt);
    setToken(jwt);
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    setToken(null);
  };

  return (
    <>
      {token ? (
        <>
          <button onClick={handleLogout}>Logout</button>
          <SendMoney />
        </>
      ) : (
        <Login onLoginSuccess={handleLoginSuccess} />
      )}
    </>
  );
}

export default App;

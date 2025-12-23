<<<<<<< HEAD
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
=======
import Login from "./pages/Login";

function App() {
  return <Login />;
}

export default App;
>>>>>>> 7ba6446bea7b624c6a4ef7b9fe3ee466670c32ce

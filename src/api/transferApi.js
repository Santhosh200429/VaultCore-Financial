import axios from "axios";

export const sendMoney = (data) => {
  const token = localStorage.getItem("token");

  return axios.post(
    "http://localhost:8080/api/transfer",
    data,
    {
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    }
  );
};

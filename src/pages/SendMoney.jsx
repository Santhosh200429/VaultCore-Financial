import { useState } from "react";
import { sendMoney } from "../api/transferApi";

export default function SendMoney() {
  const [step, setStep] = useState(1);

  const [fromAccount, setFromAccount] = useState("");
  const [toAccount, setToAccount] = useState("");
  const [amount, setAmount] = useState("");

  const next = () => setStep(step + 1);
  const back = () => setStep(step - 1);

  const handleTransfer = async () => {
    // 🔐 Check auth
    const token = localStorage.getItem("token");
    if (!token) {
      alert("Session expired. Please login again.");
      return;
    }

    // ❌ Prevent self-transfer
    if (fromAccount === toAccount) {
      alert("From and To account must be different");
      return;
    }

    try {
      await sendMoney({
        fromAccountId: fromAccount,
        toAccountId: toAccount,
        amount: Number(amount)
      });

      alert("Transfer Successful");

      // Reset
      setStep(1);
      setFromAccount("");
      setToAccount("");
      setAmount("");

    } catch (err) {
      alert("Transfer Failed (Authorization / Concurrency / Insufficient Balance)");
    }
  };

  return (
    <div>
      <h2>Send Money</h2>

      {step === 1 && (
        <>
          <h3>Step 1: Accounts</h3>

          <input
            placeholder="From Account ID"
            value={fromAccount}
            onChange={e => setFromAccount(e.target.value)}
          />

          <input
            placeholder="To Account ID"
            value={toAccount}
            onChange={e => setToAccount(e.target.value)}
          />

          <button
            disabled={!fromAccount || !toAccount}
            onClick={next}
          >
            Next
          </button>
        </>
      )}

      {step === 2 && (
        <>
          <h3>Step 2: Amount</h3>

          <input
            type="number"
            placeholder="Amount"
            value={amount}
            onChange={e => setAmount(e.target.value)}
          />

          <button onClick={back}>Back</button>

          <button
            disabled={Number(amount) <= 0}
            onClick={next}
          >
            Next
          </button>
        </>
      )}

      {step === 3 && (
        <>
          <h3>Confirm Transfer</h3>

          <p>From: {fromAccount}</p>
          <p>To: {toAccount}</p>
          <p>Amount: ₹{amount}</p>

          <button onClick={back}>Back</button>
          <button onClick={handleTransfer}>
            Confirm & Send
          </button>
        </>
      )}
    </div>
  );
}

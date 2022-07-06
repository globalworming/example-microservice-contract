import logo from './logo.svg';
import './App.css';
import axios from "axios";
import {useState} from "react";

function App() {
  const [paymentSuccess, setPaymentSuccess] = useState(undefined)
  const [message, setMessage] = useState(undefined)
  const [errorMessage, setErrorMessage] = useState(undefined)
  const [userName, setUserName] = useState("")

  const sendPaymentRequest = (amount) => {
    axios
        .post(`/pay`, {user: userName, amount: amount})
        .then((result) => {
          setPaymentSuccess(true)
          setMessage(result.data);
        })
        .catch((err) => {
          setErrorMessage(err.response.data)
        });
  }

  return (
      <div className="App">
        <header className="App-header">
          <a href="/">learn react</a>
          <img src={logo} className="App-logo" alt="logo"/>
          <input aria-label="user name input" onChange={(e) => setUserName(e.target.value)} value={userName}/>
          <button aria-label="buy car" onClick={() => sendPaymentRequest(10000.99)}>buy car</button>
          <button aria-label="buy coffee" onClick={() => sendPaymentRequest(5.99)}>buy coffee</button>
          {paymentSuccess && <span aria-label="message">{message}</span>}
          {errorMessage && <span aria-label="error message">{errorMessage}</span>}
        </header>
      </div>
  );
}

export default App;

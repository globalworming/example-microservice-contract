import logo from './logo.svg';
import './App.css';
import axios from "axios";
import {useState} from "react";

function App() {

  const [paymentSuccess, setPaymentSuccess] = useState(undefined)
  const [message, setMessage] = useState(undefined)

  const sendLowRiskRequest = () => {
    axios
        .post(`/pay`, {user: "Athena", amount: 5.99})
        .then((result) => {
          setPaymentSuccess(true)
          setMessage(result.data);
        })
        .catch((err) => console.log("error", err));
  }

  return (
      <div className="App">
        <header className="App-header">
          <a href="/">learn react</a>
          <img src={logo} className="App-logo" alt="logo"/>
          <button aria-label="perform low risk request" onClick={sendLowRiskRequest}>low risk request</button>
          {paymentSuccess && <span aria-label="message">{message}</span>}
        </header>
      </div>
  );
}

export default App;

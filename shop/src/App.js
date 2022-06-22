import logo from './logo.svg';
import './App.css';
import {useState} from "react";
import axios from "axios";

function App() {

  const [user, setUser] = useState('')
  const [paymentSuccess, setPaymentSuccess] = useState(false)
  const [message, setMessage] = useState(undefined)

  const handleChange = ev => {
    ev.preventDefault()
    setUser(ev.currentTarget.value)
  }

  const buyCoffee = () => {
    axios
        .post(`/pay`, {user: user, amount: 5.99})
        .then((result) => {
          setPaymentSuccess(true)
          setMessage(result.data);
        })
        .catch((err) => console.log("error", err));
  }

  return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo"/>
          <p>user <input type={"text"} value={user} aria-label="user-name" onChange={handleChange}/></p>
          <p>
            <button onClick={buyCoffee} aria-label="buy-coffee">buy coffee</button>
            <button aria-label="buy-car">buy car</button>
          </p>
          { paymentSuccess && <p aria-label="payment-success"><span aria-label="message">{message}</span></p>}
        </header>
      </div>
  );
}

export default App;

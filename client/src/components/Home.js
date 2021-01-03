import logo from '../apple.png';
import Button from 'react-bootstrap/Button';
import { Link } from 'react-router-dom';
import '../App.css';

function Home() {
  return (
    <div className="Home">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1>Apple Store</h1>
        <p>We don't sell iPhones, just top quality Apples!</p>
        <div>
          <Link className="nav-button" to="/shop"><Button variant="light">Buy Apples</Button></Link>
          <Link className="nav-button" to="/tracking"><Button variant="light">Track My Order</Button></Link>
        </div>
      </header>
    </div>
  );
}

export default Home;
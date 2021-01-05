import { BrowserRouter as Router, Route} from 'react-router-dom';

import Home from './components/Home'
import TrackingPage from './components/TrackingPage'
import Shop from './components/Shop'
import './App.css';

function App() {
  return (
    <Router>
        <div className="App">
          <Route exact path='/' component={Home}></Route> 
          <Route exact path='/shop' component={Shop}></Route> 
          <Route exact path='/tracking' component={TrackingPage}></Route> 
        </div>
    </Router>
  );
}

export default App;

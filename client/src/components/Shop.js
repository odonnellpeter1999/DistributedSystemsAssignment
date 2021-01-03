import '../App.css';
import React from 'react';
import apples from '../apples.jpeg'
import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button'
import CardDeck from 'react-bootstrap/CardDeck'
import { Link } from 'react-router-dom';
import PaymentModal from './PaymentModal'
import config from '../config.js'

class Shop extends React.Component {

  render() {
    return (
      <div className="Shop">
        <header className="App-header">
          <h1>Shop</h1>
          <p>Gala Apples</p>
          <CardDeck>
            {config.products.map(product => {
              return (
                <Card style={{ width: '18rem' }}>
                  <Card.Img variant="top" src={apples} />
                  <Card.Body>
                    <Card.Title>{product.name}</Card.Title>
                    <PaymentModal product={product}/>
                  </Card.Body>
                </Card>
            )})}
          </CardDeck>
          <div className="nav-button" >
            <Link className="nav-button" to="/"><Button variant="light">Home</Button></Link>
            <Link className="nav-button" to="/tracking"><Button variant="light">Track My Order</Button></Link>
          </div>
        </header>
      </div>
    );
  }
}

  

export default Shop;
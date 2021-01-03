import '../App.css';
import React from 'react'
import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import FormGroup from 'react-bootstrap/esm/FormGroup';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';
import { Link } from 'react-router-dom'

class TrackingPage extends React.Component {
  constructor() {
    super();
    this.state = {
      trackingInfo: null
    }
    this.trackOrder = this.trackOrder.bind(this)
    this.displayTrackingInfo = this.displayTrackingInfo.bind(this)
  }


  trackOrder() {
    let id = document.getElementById("trackingIdInput").value
    this.setState({
      trackingInfo: {
        id: id,
        status: "Dispatched",
        location: "Dublin IRL"
      }
    })
  }

  displayTrackingInfo() {
    if (this.state.trackingInfo != null) {
      let trackingInfo = this.state.trackingInfo
      return (
        <>
          <Container className="trackingInfo">
            <Row>Tracking ID: {trackingInfo.id}</Row>
            <Row>Status: {trackingInfo.status}</Row>
            <Row>Location: {trackingInfo.location}</Row>
          </Container>
        </>
      )
    }
    
  }

  render() {
    return (
      <div className="TrackingPage">
        <header className="App-header">
          <Card style={{ width: '70%', height: '50%' }}>
            <Card.Body>
              <Card.Title>Track My Order</Card.Title>
              <Form>
                <FormGroup>
                  <Form.Control id="trackingIdInput" type="text" placeholder="Enter your Tracking ID"/>
                </FormGroup>
              </Form>
              <Button variant="outline-dark" onClick={() => {this.trackOrder()}}>Track</Button>
              {this.displayTrackingInfo()}
            </Card.Body>
          </Card>
          <div className="nav-button" >
            <Link className="nav-button" to="/"><Button variant="light">Home</Button></Link>
            <Link className="nav-button" to="/shop"><Button variant="light">Shop</Button></Link>
          </div>
        </header>
      </div>
    );
  }
}



export default TrackingPage;
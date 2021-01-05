import React from 'react'
import Axios from 'axios'
import { Link } from 'react-router-dom'

import Card from 'react-bootstrap/Card'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import FormGroup from 'react-bootstrap/esm/FormGroup';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';

import '../App.css';

const trackingServiceURL = 'http://localhost:8762/tracking-service/request-tracking/'

class TrackingPage extends React.Component {
  
  constructor() {
    super();
    this.state = {
      trackingInfo: null
    }
    this.trackOrder = this.trackOrder.bind(this)
    this.displayTrackingInfo = this.displayTrackingInfo.bind(this)
  }

  /* Request Submission Methods */
  trackOrder() {
    let id = document.getElementById("trackingIdInput").value

    console.log("Tracking: " + id)

    Axios.get(trackingServiceURL + id).then(response => {
      if (response.data.error != null) {
        this.setState({
          error: response.error
        })
      } else if (response.data.location == null ){
        this.setState({
          trackingInfo: {
            id: id,
            status: response.data.status,
            facility: "Not at a facility yet"
          }
        })
      } else {
        this.setState({
          trackingInfo: {
            id: id,
            status: response.data.status,
            facility: response.data.location
          }
        })
      }
    })
  }

  /* Rendering Methods */
  displayTrackingInfo() {
    if (this.state.trackingInfo != null) {
      let trackingInfo = this.state.trackingInfo
      return (
        <>
          <Container className="trackingInfo">
            <Row>Tracking ID: {trackingInfo.id}</Row>
            <Row>Status: {trackingInfo.status}</Row>
            <Row>Last known Location: {trackingInfo.facility}</Row>
          </Container>
        </>
      )
    } else if (this.state.error != null) {
      return (
        <p>Error: {this.state.error}</p>
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
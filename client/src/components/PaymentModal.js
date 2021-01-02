import Modal from 'react-bootstrap/Modal'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import ListGroup from 'react-bootstrap/ListGroup'
import React from 'react';
import Axios from 'axios'

class PaymentModal extends React.Component {
  constructor(){
    super();
    this.state = {
      showHide : false,
      quotations: [],
      chosenService: null
    }
    this.toggleShow = this.toggleShow.bind(this)
    this.getQuotations = this.getQuotations.bind(this)
    this.chooseQuotation = this.chooseQuotation.bind(this)
  }

  chooseQuotation(q) {
    console.log("Choosing quotation")
    this.setState({ 
      chosenQuotation: q
    })
    console.log(this.state.chosenQuotation)
  }

  getQuotations() {
    let lat = parseInt(document.getElementById("lat").value)
    let long = parseInt(document.getElementById("long").value)
    console.log('Longitude: ' + long)
    console.log('Latitude: ' + lat)
    
    let packageInfo = this.props.product.packageInfo

    let postObject = {
      "sourceLon": 48,
      "sourceLat": 2,
      "destinationLon": long,
      "destinationLat": lat,
      "parcels": [
        {
          "weightKg": packageInfo.weight,
          "lengthCm": packageInfo.lengthCm,
          "widthCm": packageInfo.width,
          "heightCm": packageInfo.height,
        }
      ]
    }

    console.log(postObject)

    // fetch("http://localhost:8800/request", {
    //   method: "POST",
    //   headers: {
    //     'Accept': '*/*',
    //     'Content-Type': 'application/json',
    //     'Origin': 'localhost:3000',
    //     'Access-Control-Allow-Origin'
    //   },

    //   //make sure to serialize your JSON body
    //   body: JSON.stringify({
    //     "sourceLon": 48,
    //     "sourceLat": 2,
    //     "destinationLon": long,
    //     "destinationLat": lat,
    //     "parcels": [
    //       {
    //         "weightKg": packageInfo.weight,
    //         "lengthCm": packageInfo.lengthCm,
    //         "widthCm": packageInfo.width,
    //         "heightCm": packageInfo.height,
    //       }
    //     ]
    //   })
    // })
    // .then( (response) => { 
    //   console.log(response)
    // });

    Axios.post('http://localhost:8800/request', postObject).then(response => {
      console.log(response.data)
      let quotationsList = []
      response.data.forEach(jsonstring => {
        let jsonObj = JSON.parse(jsonstring);
        console.log(jsonObj)
        quotationsList.push({
          postalService: jsonObj.providerName,
          price: jsonObj.price
        })
      })
      this.setState({
        quotations: quotationsList
      })
    })

    // console.log('getQuotations')
    // this.setState({ quotations: [{postalService: "DHL", price: "3.00"}] })
    // console.log('quotations:',  this.state.quotations)
  }

  toggleShow() {
    this.setState({ 
      showHide: !this.state.showHide,
      quotations: [],
      chosenQuotation: null
    })
  }

  deliveryPrice() {
    if (this.state.chosenQuotation != null) {
      return(<p>Delivery: {this.formatMoney(this.state.chosenQuotation.price)} - {this.state.chosenQuotation.postalService} </p>)
    }
  }

  deliverButton() {
      if (this.state.chosenQuotation != null) {
        return(<Button className="qOptButton" variant="outline-dark" onClick={() => {this.deliver()}}>Deliver</Button>)
    }
  }

  deliver() {
    this.toggleShow()
  }

  formatMoney(number) {
    var formatter = new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'EUR',
    });
    return formatter.format(number)
  }
  
  render() {

    return (
      <>
        <Button variant="outline-dark" onClick={this.toggleShow}>
          Buy Now!
        </Button>
  
        <Modal
          show={this.state.showHide}
          onHide={this.toggleShow}
          backdrop="static"
          keyboard={false}
        >
          <Modal.Header closeButton>
            <Modal.Title>{this.props.product.name}</Modal.Title>
                <hr/>
                </Modal.Header>
                    <Modal.Body>
                        <p>Price: {this.formatMoney(this.props.product.price)}</p>
                        {this.deliveryPrice()}
                        <Form>
                            <p><u>Package Info:</u></p>
                            <p>Weight: {this.props.product.packageInfo.weight}kg</p>
                            <p>Volume: {this.props.product.packageInfo.lengthCm*this.props.product.packageInfo.width*this.props.product.packageInfo.height}cm<sup>3</sup></p>
                            <p><u>Please enter your delivery information</u></p>
                            <Form.Group>
                                <Form.Control id="lat" type="text" placeholder="Latitude" />
                            </Form.Group>
                            <Form.Group>
                                <Form.Control id="long" type="text" placeholder="Longitude" />
                            </Form.Group>
                            <Button variant="outline-dark" onClick={() => this.getQuotations()}>Get Delivery Quotes</Button>
                        </Form>
                        <ListGroup>
                          {this.state.quotations.map(quotation => {
                            console.log('quote')
                            return (
                              <ListGroup.Item >{quotation.postalService}: {this.formatMoney(quotation.price)} <Button className="qOptButton" variant="outline-dark" onClick={() => {this.chooseQuotation(quotation)}}>Choose Quotation</Button></ListGroup.Item>
                            )
                          })}
                        </ListGroup>
                    </Modal.Body>
                <Modal.Footer>
                  {this.deliverButton()}
                  <Button variant="secondary" onClick={this.toggleShow}>
                  Close
                  </Button>
                </Modal.Footer>
        </Modal>
      </>
    );
}
}

export default PaymentModal
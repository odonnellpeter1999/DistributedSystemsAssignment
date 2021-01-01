import Modal from 'react-bootstrap/Modal'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import ListGroup from 'react-bootstrap/ListGroup'
import React from 'react';

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
    let lat = document.getElementById("lat").value
    let long = document.getElementById("long").value
    console.log('Longitude: ' + long)
    console.log('Latitude: ' + lat)
    // Perform request.
    console.log('getQuotations')
    this.setState({ quotations: [{postalService: "DHL", price: "3.00"}] })
    console.log('quotations:',  this.state.quotations)
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
                            <p>Volume: {this.props.product.packageInfo.length*this.props.product.packageInfo.width*this.props.product.packageInfo.height}cm<sup>3</sup></p>
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
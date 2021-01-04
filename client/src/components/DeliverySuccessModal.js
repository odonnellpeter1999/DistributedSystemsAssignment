import React from 'react'
import {Button, ListGroup, Modal} from 'react-bootstrap'

class DeliverySuccessModal extends React.Component {
    constructor() {
        super()
        this.state = {
            showHide : true,
        }
    }

    toggleShow() {
        this.setState({ 
          showHide: !this.state.showHide,
        })
        this.props.close()
      }

    render() {
        return (
            <>
                <Modal show={this.state.showHide} onHide={() => {this.toggleShow()}}>
                    <Modal.Header closeButton>
                    <Modal.Title>Success!</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <p>Your apples are on the way!</p>
                        <ListGroup>
                            <ListGroup.Item>Tracking ID: {this.props.deliveryInfo.trackingID}</ListGroup.Item>
                            <ListGroup.Item>Date Ordered: {this.props.deliveryInfo.dateDelivered}</ListGroup.Item>
                            <ListGroup.Item>Expected Delivery Date: {this.props.deliveryInfo.dateDelivered}</ListGroup.Item>
                        </ListGroup>
                    </Modal.Body>
                    
                    <Modal.Footer>
                    <Button variant="secondary" onClick={() => {this.toggleShow()}}>
                        Close
                    </Button>
                    </Modal.Footer>
                </Modal>
            </>
        );
    }
    
}
  
export default DeliverySuccessModal
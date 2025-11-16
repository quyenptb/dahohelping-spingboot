
import React, { useEffect, useState } from 'react';
import { Form, Button, Modal } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

function ReportModal({isClicked, updateIsReportSubmitted, updateIsReportModalVisible}) {
  const [show, setShow] = useState(false);
  //const [isReportSubmitted, setIsReportSubmitted] = useState(false);

  const handleSubmit = () => {
    setShow(false); // Đặt giá trị false khi đóng Modal
    updateIsReportSubmitted(true)
  }
  const handleClose = () => {
    setShow(false); // Đặt giá trị false khi đóng Modal
    updateIsReportSubmitted(false)
    updateIsReportModalVisible(false)
  }
  const handleShow = () => setShow(true);
  
  useEffect(
    () => {
        if(isClicked)
        setShow(true)
    }, []
  )

  return (
    <>
      <Modal show={show} onHide={
        handleClose
         }>
        <Modal.Header closeButton>
          <Modal.Title>Báo cáo vi phạm</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
              <Form.Label>Tiêu đề</Form.Label>
              <Form.Control
                type="text"
                placeholder="Nhập tiêu đề báo cáo"
                autoFocus
              />
            </Form.Group>
            <Form.Group
              className="mb-3"
              controlId="exampleForm.ControlTextarea1"
            >
              <Form.Label>Nội dung báo cáo</Form.Label>
              <Form.Control as="textarea" rows={3} />
            </Form.Group>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Đóng
          </Button>
          <Button variant="primary" onClick={handleSubmit} 
            >
            Gửi
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}
export default ReportModal
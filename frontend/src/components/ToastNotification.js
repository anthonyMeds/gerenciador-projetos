import React from "react";
import { Toast, ToastContainer } from "react-bootstrap";

const ToastNotification = ({ show, message, isError, onClose }) => (
  <ToastContainer position="top-end" className="p-3">
    <Toast
      show={show}
      onClose={onClose}
      delay={3000}
      autohide
      bg={isError ? "danger" : "success"}
    >
      <Toast.Body>{message}</Toast.Body>
    </Toast>
  </ToastContainer>
);

export default ToastNotification;

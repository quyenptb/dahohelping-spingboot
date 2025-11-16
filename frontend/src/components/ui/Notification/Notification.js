import React, { useState, useEffect } from 'react';
import './Notification.css';

const Notification = ({ message, type, duration = 5000, onClose }) => {
  const [showNotification, setShowNotification] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      hideNotification();
    }, duration);

    return () => clearTimeout(timer);
  }, []);

  const hideNotification = () => {
    setShowNotification(false);
    if (onClose) {
      onClose();
    }
  };

  return (
    showNotification && (
      <div className={`notification ${type}`}>
        <p>{message}</p>
        <button className="close-btn" onClick={hideNotification}>
          <i className="fas fa-times"></i>
        </button>
      </div>
    )
  );
};

export default Notification;

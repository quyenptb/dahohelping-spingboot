import React, { useState } from 'react';
import './NotificationBoard.css';
import del from '../../../assets/icons/delete.png';

const NotificationBoard = () => {
    const [notifications, setNotifications] = useState([
        { id: 1, title: 'Thông báo 1', message: 'Đây là thông báo số 1', src: 'Hệ thống'},
        { id: 2, title: 'Thông báo 2', message: 'Đây là thông báo số 2', src: 'Hệ thống' },
        { id: 3, title: 'Thông báo 3', message: 'Đây là thông báo số 3', src: 'meocondethuong' },
        { id: 4, title: 'Thông báo 4', message: 'Đây là thông báo số 4', src: 'meocondethuong'},
        { id: 5, title: 'Thông báo 5', message: 'Đây là thông báo số 5', src: 'Hệ thống' },
    ]);

    const [selectedNotification, setSelectedNotification] = useState(null);

    const handleNotificationClick = (notification) => {
        setSelectedNotification(notification);
    };

    const handleCloseModal = () => {
        setSelectedNotification(null);
    };

    return (
        <div className="notification-board">
            <h2>Bảng Thông Báo</h2>
            {notifications.length > 0 ? (
                <ul>
                    {notifications.map((notification, index) => (
                        <li key={index} onClick={() => handleNotificationClick(notification)}>
                            <div style={{display: 'flex', justifyContent: 'space-between'} }>
                                <h3>{notification.title}</h3>
                                <h5>{notification.src}</h5>
                                <img src={del} style={{width: '20px', height: '20px'}} alt="Delete"/>
                            </div>
                            <p>{notification.message}</p>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>Không có thông báo mới.</p>
            )}

            {/* Modal hiển thị thông tin thông báo */}
            {selectedNotification && (
                <div className="modal">
                    <div className="modal-content">
                        <span className="close" onClick={handleCloseModal}>&times;</span>
                        <h2>{selectedNotification.title}</h2>
                        <p>{selectedNotification.message}</p>
                    </div>
                </div>
            )}
        </div>
    );
};

export default NotificationBoard;

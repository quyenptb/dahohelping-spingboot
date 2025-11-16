// NotFoundPage.js
import React from 'react';
import { Link } from 'react-router-dom';

const NotFoundPage = () => {
  return (
    <div style={styles.container}>
      <div style={styles.content}>
        <h1 style={styles.title}>404</h1>
        <p style={styles.subtitle}>Trang bạn đang tìm kiếm không tồn tại!</p>
        <p style={styles.message}>Xin lỗi, nhưng trang bạn đang cố gắng truy cập không tồn tại hoặc đã bị di chuyển. Hãy kiểm tra lại URL hoặc quay lại trang chủ.</p>
        <Link to="/homepage" style={styles.link}>Quay lại trang chủ</Link>
      </div>
    </div>
  );
};

const styles = {
  container: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    minHeight: '100vh',
    background: '#f4f4f4',
  },
  content: {
    textAlign: 'center',
  },
  title: {
    fontSize: '6rem',
    color: '#333',
    marginBottom: '10px',
    letterSpacing: '1px',
  },
  subtitle: {
    fontSize: '2rem',
    color: '#555',
    marginBottom: '20px',
  },
  message: {
    fontSize: '1.2rem',
    color: '#777',
    marginBottom: '20px',
  },
  link: {
    fontSize: '1.2rem',
    color: '#007bff',
    textDecoration: 'none',
    transition: 'color 0.3s ease',
  },
};

export default NotFoundPage;

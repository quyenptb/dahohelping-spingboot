// LoginPage.js
import React, { useState, useContext } from 'react';
import daho from '../../assets/icons/DahoHelping1.png'
import { useNavigate } from 'react-router-dom';
import { getUserByUsername, login } from 'src/services/UsersService';
import { Notification } from '../../components/index.js';
import { AppContext } from 'src/context';
import GoogleIcon from 'src/assets/icons/logingg.png'

import './LoginPage.css';
import GoogleLogin from 'src/components/ui/LoginWithGoogle/LoginWithGoogle';

const LoginPage = () => {
  const [clicked, setClicked] = useState(false); //for click in Google Login Button
  const {currentUser, setCurrentUser} = useContext(AppContext);
  const [notisetting, setNotiSetting] = useState([]);
  const [showNotification, setShowNotification] = useState(false);
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    rememberMe: false,
  })

const [passwordError, setPasswordError] = useState('')
const [usernameError, setUsernameError] = useState('')

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    const newValue = type === 'checkbox' ? checked : value;
    setFormData({ ...formData, [name]: newValue });
  };

   const  getCurrentUser = async (username) => {
    try {
      const response = await getUserByUsername(username);
      return response.data;
    }
    catch (e) {
      console.log("Failed to get current user infomation", e);
      throw new Error("Failed to get current user infomation", e)
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.username && formData.password ) {
    try {
      const response = await login(formData.username, formData.password);
      setCurrentUser(response.data.user);
      setShowNotification(true);
      setNotiSetting({ message: 'Đăng nhập thành công! Đang chuyển trang ...', type: 'success' });
      navigate('/homepage');
    } catch (e) {
      console.error('Error logging in:', e);
      setShowNotification(true);
      setNotiSetting({ message: 'Đăng nhập không thành công! Gặp lỗi ' + e, type: 'error' });
    }
  }
  };

  const onButtonClick = () => {
    // Set initial error values to empty
    setUsernameError('')
    setPasswordError('')
  
    // Check if the user has entered both fields correctly
    if ('' === formData.username) {
      setUsernameError('Hãy nhập vào Tên người dùng')
      return
    }
  
    if (!/^[a-zA-Z0-9_]+$/.test(formData.username) && !/^[a-zA-Z]/.test(formData.username)) {
      setUsernameError('Hãy nhập một Tên người dùng hợp lệ')
      return
    }
  
    if ('' === formData.password) {
      setPasswordError('Please enter a password')
      return
    }
  
    if (formData.password.length < 7) {
      setPasswordError('Mật khẩu phải có độ dài tối thiểu 8 kí tự')
      return
    }
  }
  //for Google Login
  const [user, setUser] = useState(null);
  const [error, setError] = useState(null);

  const handleLoginSuccess = (userInfo) => {
    setUser(userInfo);
  };

  const handleLoginError = (error) => {
    setError('Failed to login');
    console.error('Login Failed:', error);
  };
  return (
    
     <div className='login-container'> 
        <img className='login-logo' src= {daho} alt='Logo' />
      <div className='login-card'>
        <h1 className='login-title'>Đăng nhập</h1>
        <form onSubmit={handleSubmit} className='login-login-form'>
          <input
            type="text"
            name="username"
            value={formData.username}
            placeholder="Tên người dùng"
            onChange={handleChange}
            className='login-input'
          />
          <label className="errorLabel" style={{color: 'red', fontSize: '12px'}}>{usernameError}</label>
          <input
            type="password"
            name="password"
            value={formData.password}
            placeholder="Mật khẩu"
            onChange={handleChange}
            className='login-input'
          />
          <label className="errorLabel" style={{color: 'red', fontSize: '12px'}}>{passwordError}</label>
          <label className='login-checkboxLabel'>
            <input
              type="checkbox"
              name="rememberMe"
              checked={formData.rememberMe}
              onChange={handleChange}
              className='login-checkbox'
            />
            Lưu thông tin đăng nhập
          </label>
          <button type="submit" className='login-button' onClick={onButtonClick}>Đăng nhập</button>
        </form>
        
        <img style={{marginTop: "20px", width:"100%", height: "100%"}} src={GoogleIcon} onClick={() => setClicked(!clicked)} />
          {clicked &&
        <GoogleLogin clicked={clicked}
        onSuccess={handleLoginSuccess}
        onError={handleLoginError}
      /> } 
      </div>
      {
  showNotification === true && (
    <Notification
      message={notisetting.message}
      type={notisetting.type}
      onClose={() => setShowNotification(false)} // Đặt setShowNotification(false) khi thông báo được đóng
    />
  )
}
    </div>
  );
};

export default LoginPage;

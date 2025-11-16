import { useEffect, useState } from 'react';
import { useGoogleLogin } from 'react-oauth-google';
import axios from 'axios';
import SpinnerLoader from '../SpinnerLoader/SpinnerLoader';
import { Audio, Rings, RotatingLines } from 'react-loader-spinner';

function GoogleLogin({clicked}) {
  const [user, setUser] = useState(null);
  const [error, setError] = useState(null);

  const login = useGoogleLogin({
    client_id: '940988695510-6if6nnnq6k6chc6u2n60et060p26mbbt.apps.googleusercontent.com',
    redirectUri: 'http://localhost:3000/auth/callback',
    onSuccess: async (tokenResponse) => {
      try {
        const accessToken = tokenResponse.access_token;
        
        // Gọi API của Google để lấy thông tin người dùng
        const userInfoResponse = await axios.get(`https://www.googleapis.com/oauth2/v1/userinfo?access_token=${accessToken}`);
        const userInfo = userInfoResponse.data;

        // Tạo đối tượng user và gửi đến backend
        const userObject = {
          id: userInfo.id,
          name: userInfo.name,
          email: userInfo.email,
          avatar: userInfo.picture,
          token: accessToken,
          password: "password",
        };

        // Gửi userObject đến backend và xử lý phản hồi
        //Em gửi userDetails này về backend rồi anh handle giúp e lun nha, tại
        //e cài đặt login with google ở react luôn mặc dù rủi ro bảo mật
        //nhưng kiểu mún thêm tính năng cho thầy chấm điểm á a :<<<<
        const response = await axios.post('/api/auth/google', userObject);
        if (response.status !== 200) {
          throw new Error('Server error');
        }

        setUser(userInfo);
      } catch (error) {
        console.error('Authentication error', error);
        setError('Đã xảy ra lỗi. Xin hãy đăng nhập/đăng kí thông thường');
      }
    },
    onError: (error) => {
      console.error('Login Failed:', error);
      setError('Failed to login');
    },
  });

  const handleClick = () => {
    login();
  };

  useEffect(() => {
    if (clicked === true) {
      handleClick();
    }
  }, [clicked]);

  if (error) {
    return <div>{error}</div>;
  }

  if (!user) {
    return <div>Loading...</div>;
  }

  return <div>Welcome, {user.name}</div>;
}

export default GoogleLogin;

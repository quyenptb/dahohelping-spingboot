import React, { useContext, useState } from 'react';
import './DRL.css';
import Notification from '../Notification/Notification.js';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { AppContext } from 'src/context';
import { isAuthenticated } from 'src/utils/isAuthenticated';
import { uni_data } from 'src/data';

const university = uni_data;
const DRL = () => {
  const {
    currentUser, //user hiện tại (principal)   
} = useContext(AppContext);
  const [notisetting, setNotiSetting] = useState([]);
  const [showNotification, setShowNotification] = useState(false); // Thêm state để kiểm soát hiển thị thông báo
  const [drl, setDrl] = useState(0);
  const [exchangeScore, setExchangeScore] = useState();
  const currentUni = university.find(uni => uni.id === currentUser.uni_id);
  const uni_drl = currentUni.drl;
  const newDrl = Math.floor(exchangeScore / 10) * uni_drl;

  const handleInputChange = e => {
    setExchangeScore(e.target.value);
  };
  console.log(currentUser.user_id);

  const handleSubmit = async e => {
    e.preventDefault();
    if (exchangeScore <= currentUser.score) { console.log(exchangeScore)
      if (exchangeScore % 10 !== 0) {
        setShowNotification(true);
        setNotiSetting({ message: 'Hãy nhập điểm quy đổi là điểm tròn', type: 'error' });
      } else {
        try { {/*
          const response = await axios.put(`http://localhost:3000/user/?user_id=${currentUser.user_id}`, {
            score: currentUser.score - exchangeScore
          }); */}

            setDrl(newDrl);
            setShowNotification(true);
            setNotiSetting({ message: 'Bạn đã quy đổi sang điểm rèn luyện thành công!', type: 'success' });
          
        } catch (error) {
          console.error('Lỗi khi cập nhật điểm:', error);
          setShowNotification(true);
          setNotiSetting({ message: 'Có lỗi xảy ra khi cập nhật điểm!', type: 'error' });
        }
      }
    } else {
      setShowNotification(true);
      setNotiSetting({ message: 'Hãy nhập điểm quy đổi bé hơn hoặc bằng điểm hiện có', type: 'error' });
    }
  };

  return (
    currentUser ? (
      <div className="drl-container">
      <h1 className="drl-title">QUY ĐỔI ĐIỂM RÈN LUYỆN</h1>
      <p>
        DahoHelping liên kết với các <Link to="/introduction" target='_blank'> trường Đại học và THPT </Link> trong Hệ thống Đại học
        Quốc Gia Tp Hồ Chí Minh để thực hiện quy đổi điểm cho những sinh viên năng nổ hoạt động và tích lũy được nhiều
        điểm hệ thống. Tùy thuộc vào mỗi trường sẽ có quy định mức điểm được chuyển đổi sang điểm rèn luyện:
      </p>
      <table border="1" cellSpacing="0" cellPadding="5" className="bangquydoi">
        <tr>
         <td className='header' colspan={2}> BẢNG QUY ĐỔI </td></tr>
        <tbody>
          <tr>
            <th>Mức điểm DahoHelping</th>
            <th>Mức điểm rèn luyện</th>
          </tr>
          <tr>
            <td> 10 </td>
            <td> {uni_drl} </td>
          </tr>
        </tbody>
        <tr>
          <td  colspan={2} >
            <form>
              <input type="number" placeholder="Nhập vào số điểm" required onChange={handleInputChange} />
              <p className="resultBehaviourScore"> Quy đổi thành công {drl === 0 ? "..." : drl} điểm rèn luyện </p>
              <button id="submit" type="submit" onClick={handleSubmit}>
                Quy đổi
              </button>
            </form>
          
          </td>
        </tr>
      </table>
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
    ): (
      <h1 style={{marginLeft: '10px'}}> Bạn hãy <Link to={'/login'}> đăng nhập </Link> trước khi sử dụng chức năng Quy đổi điểm rèn luyện.
      Nếu chưa có tài khoản, hãy <Link to={'/signin'}>đăng kí</Link> ngay!
       </h1>
    )
  );
      
};


export default DRL;

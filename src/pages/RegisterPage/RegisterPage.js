import { React, useContext, useEffect, useState, useRef, createContext } from 'react';
import { useNavigate } from 'react-router-dom';
import './RegisterPage.css';
import dahohelping from '../../assets/icons/DahoHelping1.png'
import {daho_data, fal_data, maj_data, sub_data, uni_data, user_data} from '../../data/index'
import { signin } from 'src/services/UsersService';
import Notification from 'src/components/ui/Notification/Notification';
import { avatar } from 'src/utils/images';
import AvatarEditor from 'react-avatar-editor';
import { Button, Modal } from 'react-bootstrap';

const fal = fal_data;
const maj = maj_data;
const uni = uni_data;

const hometown = [
  "An Giang", "Bà Rịa - Vũng Tàu", "Bắc Giang", "Bắc Kạn", "Bạc Liêu", "Bắc Ninh",
  "Bến Tre", "Bình Định", "Bình Dương", "Bình Phước", "Bình Thuận", "Cà Mau", "Cao Bằng",
  "Đắk Lắk", "Đắk Nông", "Điện Biên", "Đồng Nai", "Đồng Tháp", "Gia Lai", "Hà Giang",
  "Hà Nam", "Hà Tĩnh", "Hải Dương", "Hậu Giang", "Hòa Bình", "Hưng Yên", "Khánh Hòa",
  "Kiên Giang", "Kon Tum", "Lai Châu", "Lâm Đồng", "Lạng Sơn", "Lào Cai", "Long An",
  "Nam Định", "Nghệ An", "Ninh Bình", "Ninh Thuận", "Phú Thọ", "Quảng Bình", "Quảng Nam",
  "Quảng Ngãi", "Quảng Ninh", "Quảng Trị", "Sóc Trăng", "Sơn La", "Tây Ninh", "Thái Bình",
  "Thái Nguyên", "Thanh Hóa", "Thừa Thiên Huế", "Tiền Giang", "Trà Vinh", "Tuyên Quang",
  "Vĩnh Long", "Vĩnh Phúc", "Yên Bái", "Phú Yên", "Cần Thơ", "Đà Nẵng", "Hải Phòng",
  "Hà Nội", "TP HCM"
];

const uniIcon = {
 0:"default_icon.png", 1: "bku.webp", 2: "khtn.png", 3: "ussh.png", 4: "iu.png", 5: "uit.png", 6: "uel.png", 7: "agu.png", 8: "khsk.png", 9: "thsp.png", 10: "ptnk.png", 11: "bentre.png",
}

const UserContext = createContext();

const UserInfo = () => {

  const { userDetails, setUserDetails } = useContext(UserContext);

  const [isEditing, setIsEditing] = useState(false);


  return (
    <div className="user-page">
        <>
          <div className="user-header">
          <img src={userDetails.avatar} alt={userDetails.username} className="avatar" />
            <h1>
                <img src={require(`src/assets/icons/${uniIcon[userDetails.uni_id]}`)} style={{ width: '60px', height: '60px' }} alt={userDetails.uni_id} className='user-uni' />
                {userDetails.fullName}
            </h1>
                <p><i> {`@${userDetails.username}`}</i></p>
            <p>{userDetails.email}</p>
            <p> {userDetails.fal_id ? userDetails.fal_id : 'Khoa'  }, {userDetails.maj_id? userDetails.maj_id : "ngành"  }</p>
          </div>
          <div className="user-details">
            <h2>Thông tin cá nhân</h2>
            <p>Điểm DahoHelping: 20 điểm </p> <span>Tặng người dùng mới</span>
            <p>Email: {userDetails.email}</p>
            <p>Quê quán: {userDetails.hometown}</p> {/*hometown*/}
            <p>Sở thích: {userDetails.hobby}</p>
          </div>
        </>
        </div>
  );
};

const RegisterPage = () => {
  const nativate = useNavigate();
  const [editor, setEditor] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const editorRef = useRef(null);
  const [notisetting, setNotiSetting] = useState([]);
  const [showNotification, setShowNotification] = useState(false);
  const [userDetails, setUserDetails] = useState({
    fullName: '',
    username: '',
    password: '',
    email: '',
    hometown: '',
    uni_id: 0,
    fal_id: '',
    maj_id: '',
    hobby: '',
    bio: '',
    avatar: '',
  });
  const [imageSrc, setImageSrc] = useState('');
  const UserProvider = UserContext.Provider;
  const handleImageUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImageSrc(reader.result);
        setUserDetails(prevDetails => ({
          ...prevDetails,
          avatar: reader.result
        }))
        console.log(userDetails.avatar);
      };
      reader.readAsDataURL(file);
        }
        setShowModal(true);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserDetails(prevDetails => ({
      ...prevDetails,
      [name]: value
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const isCreated = await signin(userDetails);
      if (isCreated) {
        nativate('/auth/login');
      }
      setShowNotification(true);
      setNotiSetting({ message: 'Đăng ký người dùng thanh công! Xin hãy đăng nhập.', type: 'success' });
      setUserDetails({ fullName: '', username: '', password: '', email: '', hometown: '', uni_id: '', fal_id: '', maj_id: '', hobby: '', bio: '', avatar: '' });
      setImageSrc('');
      nativate('/auth/login');
      //console.log('User created successfully:', newUser);
      // Xử lý thành công, có thể điều hướng người dùng đến trang khác hoặc hiển thị thông báo
    } catch (error) {
      // Xử lý lỗi khi tạo người dùng
      setShowNotification(true);
      setNotiSetting({ message: 'Lỗi khi tạo người dùng! ' + error, type: 'error' });
      //console.error('Error creating user:', error);
    }
  };

  const handleImageSave = () => {
    if (editorRef.current) {
      const canvas = editorRef.current.getImageScaledToCanvas();
      const newImage = canvas.toDataURL();
      setUserDetails(prevDetails => ({
        ...prevDetails,
        avatar: newImage
      }));
      setShowModal(false);
    }
  };
  
  return (
    <UserProvider value={{userDetails, setUserDetails}}>
    <>
    <img className='register-logo-1' src= {dahohelping} alt='Logo' />
    <div className="register-container">
      <div className='register-left-container'>
      <UserInfo>
 </UserInfo>
 </div>
      <form className="register-form" onSubmit={handleSubmit}>
        <input type="text" name="fullName" placeholder="Họ và tên" onChange={handleChange} required />
        <input type="text" name="username" placeholder="Tên tài khoản" onChange={handleChange} required />
        <input type="password" name="password" placeholder="Mật khẩu" onChange={handleChange} required />
        <input type="email" name="email" placeholder="Email" onChange={handleChange} required />
        <select name="hometown" onChange={handleChange} required >
          {hometown.map(home => <option key={home} value={home}>{home}</option>)}
        </select>
        <select name="uni_id" onChange={handleChange} required >
        {uni.map(({id, name}) => <option key={id} value={id}>{name}</option>)}
        </select>
        <select name="fal_id" onChange={handleChange} required >
          {fal.map(({id, name}) => <option key={id} value={id}>{name}</option>)}
        </select>
        <select name="maj_id" onChange={handleChange} required>
          {maj.map(({id, name}) => <option key={id} value={id}>{name}</option>)}
        </select>
        <input type="text" name="hobby" placeholder="Sở thích" onChange={handleChange} required />
        <textarea value={userDetails.bio} name="bio" placeholder="Giới thiệu bản thân" onChange={handleChange}></textarea>
        <input type="file" onChange={handleImageUpload} />
      {imageSrc && <>
          <Modal show={showModal} onHide={() => setShowModal(false)}>
            <Modal.Header closeButton> Chỉnh sửa ảnh đại diện </Modal.Header>
            <Modal.Body>
            <AvatarEditor
        ref={editorRef}
        image={userDetails.avatar}
        width={250}
        height={250}
        border={50}
        color={[255, 255, 255, 0.6]} // RGBA
        scale={1.2}
        rotate={0}
      />
      </Modal.Body>
      <Modal.Footer> <Button type="submit" onClick={handleImageSave} >Lưu ảnh</Button></Modal.Footer>
      </Modal>
        <div>
          <p>Ảnh đại diện:</p>
          
          <img src={userDetails.avatar} alt="Ảnh đại diện" style={{ maxWidth: '100%' }} />
        </div>
        </>
      }
        <button type="submit" onClick={handleSubmit} >Đăng kí</button>
      </form>
      
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
    </>
    </UserProvider>
  );
};

export default RegisterPage;

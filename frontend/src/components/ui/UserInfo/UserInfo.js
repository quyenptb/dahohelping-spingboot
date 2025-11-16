// UserPage.js
import React, { useState, useEffect, useContext } from 'react';
import './UserInfo.css';
import { AppContext } from 'src/context';
import { uni_data } from 'src/data';
import Notification from 'src/components/ui/Notification/Notification.js';

const UserInfo = ({isMyPage, v_user}) => {
  const { currentUser, notisetting, setNotisetting, showNotification, setShowNotification} = useContext(AppContext);
  const [user, setUser] = useState(v_user);
  const [isEditing, setIsEditing] = useState(false);
  const [editedUser, setEditedUser] = useState(null);

  const handleEdit = () => {
    setIsEditing(true);
  };

  const handleCancelEdit = () => {
    setIsEditing(false);
    setEditedUser(user);
  };

  const handleSaveEdit = () => {
    // Update user data
    setUser(editedUser);
    setIsEditing(false);
  };

  const handleChange = e => {
    const { name, value } = e.target; 
    setEditedUser({ ...editedUser, [name]: value });
  };

  return (
    <div className="user-page">
      {user ? (
        <>
          <div className="user-header">
          <img src={user.avatar} alt={user.username} className="avatar" />
            <h1>
            <img src={require(`src/assets/icons/${uni_data.find((uni) => uni.id === user.uni_id).code}`).default} alt={user.uni_id} className='user-uni' />
                {isEditing ? <input type="text" name="fullname" value={editedUser.fullname} onChange={handleChange} /> : user.fullname}</h1>
                <p><i> {isEditing ? `@${user.username}` : `@${user.username}`}</i></p>
            <p>{isEditing ? <input type="text" name="email" value={editedUser.email} onChange={handleChange} /> : user.email}</p>
            <p>{user.fal_id}, {user.maj_id}</p>
          </div>
          <div className="user-details">
            <h2>Thông tin cá nhân</h2>
            <p>Điểm DahoHelping: {user.score} điểm</p>
            <p>Email: {isEditing ? <input type="text" name="email" value={editedUser.email} onChange={handleChange} /> : user.email}</p>
            <p>Quê quán: {isEditing ? <input type="text" name="hometown" value={editedUser.hometown} onChange={handleChange} /> : user.hometown}</p> {/*hometown*/}
            <p>Sở thích: {isEditing ? <input type="text" name="hobby" value={editedUser.hobby} onChange={handleChange} /> : user.hobby}</p>
          </div>
          {isEditing ? (
              <>
                <button onClick={handleSaveEdit}>Lưu</button>
                <button onClick={handleCancelEdit}>Quay lại</button>
              </>
            ) : ( isMyPage ? 
              <button onClick={handleEdit}>Chỉnh sửa</button> : <> </>
            )}
          <div className="user-posts">
            <h2>Câu hỏi</h2>
            {user.posts && user.posts.length > 0 ? (
              <ul>
                {user.posts.map(post => (
                  <li key={post.id}>
                    <h3>{post.title}</h3>
                    <p>{post.body}</p>
                  </li>
                ))}
              </ul>
            ) : (
              <p>Chưa có câu hỏi nào.</p>
            )}
          </div>
        </>
      ) : <p> Đang load</p>
    } 
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
)
}


export default UserInfo;
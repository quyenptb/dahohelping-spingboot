import React, { useState, useRef, useContext, useEffect } from 'react';
import './CustomCard.css'; 
import { Editor } from '@tinymce/tinymce-react';
import awardPic from '../../../assets/icons/award.png';
import ReportModal from '../ReportModal/ReportModal';
import { getUserById } from 'src/services/UsersService';
import {fal_data, maj_data, sub_data, uni_data} from 'src/data/index'
import { Link } from 'react-router-dom';
import { AppContext } from 'src/context';

const CustomCard = ({ card }) => {
  const editorRef = useRef(null);
  const [comments, setComments] = useState([]);
  const [newComment, setNewComment] = useState('');
  const [isReportModalVisible, setIsReportModalVisible] = useState(false);
  const [isReportSubmitted, setIsReportSubmitted] = useState(false);
  const [isSaved, setIsSaved] = useState(false);
  const [cardUser, setCardUser] = useState([]);

  const {currentUser} = useContext(AppContext);
  
  useEffect(() => {
    async function fetchUser() {
      try {
        const v_cardUser = await getUserById(card.user_id);
        setCardUser(v_cardUser);
      } catch (error) {
        console.error(error);
      }
    }
  
    fetchUser();

  },)
  

  //const cardSub = sub.find(s => s.id === card.sub_id);

  const cardSub = sub_data.find(s => s.sub_id === card.sub_id);
  const cardMaj = maj_data.find(m => m.maj_id === cardSub.maj_id); 
  const cardFal = fal_data.find(f => f.fal_id === cardMaj.fal_id); 
  const cardUni = uni_data.find(u => u.id === cardFal.uni_id); 

  const handleEditorChange = (content, editor) => {
    setNewComment(content);
    editorRef.current = editor;
  };

  const handleCommentSubmit = (event) => {
    event.preventDefault();
    if (newComment.trim()) {
      setComments([newComment, ...comments]);
      setNewComment('');
    }
    if (editorRef.current) {
      editorRef.current.setContent('');
    }
  };

  const handleButtonSave = (event) => {
    event.preventDefault();
    setIsSaved(true);
    // Add logic to save the question
  };
  
  const updateIsReportSubmitted = (newValue) => {
    setIsReportSubmitted(newValue);
  };

  const updateIsReportModalVisible = (newValue) => {
    setIsReportModalVisible(newValue);
  };

  const handleReportClick = (event) => {
    event.preventDefault();
    if (!isReportSubmitted) {
      setIsReportModalVisible(true); 
    }
  };

  return (
    <div className="custom-card-container">
      <div className="custom-card-header">
        <div className="custom-card-header-title">
        <h3>{card.title}</h3>
        <span className="card-award"><h2>{card.award}</h2></span>
        <img src={awardPic} alt='Award Icon' style={{width: '50px', height: '50px'}}/>
        </div>
        <div className='custom-card-header-info' style={{display: 'flex', justifyContent: 'space-evenly'}}>
        <Link to={`/users/${card.user_id}`}><span className="card-username">{cardUser.username}</span></Link>
        <span className="card-time">{card.time}</span>
        <Link to={`/sub/${card.sub_id}`}><span className="card-subject">{cardSub.name}</span> </Link>
        <Link to={`/uni/${card.uni_id}`}><span className="card-uni">{cardUni.name}</span></Link> <span className="card-uni">{cardUni.code}</span>
        </div>
        </div>
      <div className="custom-card-body">
        <h5 className="custom-card-question-title">Câu hỏi:</h5>
        <p className="custom-card-question-content">{card.text}</p>
        <form onSubmit={handleCommentSubmit}>
          <label htmlFor="newComment" className="custom-card-form-label">Trả lời</label>
          {currentUser ? 
          <Editor
          onInit={(evt, editor) => (editorRef.current = editor)}
          apiKey='ubvf47mx487okwyj5ynvjw2ruufjrou0oyb6mq4b8tygjopl'
            initialValue=""
            init={{
              placeholder: `Hãy giúp ${cardUser.username} trả lời câu hỏi nhé! Chúc bạn một ngày học tập vui vẻ!`,
              height: 200,
              menubar: false,
              plugins: [
                'advlist autolink lists link image imagetools charmap print preview anchor',
                'searchreplace visualblocks code fullscreen',
                'insertdatetime media table paste code help wordcount'
              ],
              toolbar:
                'image | code | undo redo | formatselect | bold italic underline | \
                alignleft aligncenter alignright alignjustify | \
                bullist numlist outdent indent | removeformat | help'
            }}
            onEditorChange={handleEditorChange}
          /> : <div> <strong> Hãy <Link to={"/login"}> Đăng nhập</Link> để trả lời câu hỏi này nhé! </strong> </div> }
          { currentUser ? <>
          <button className="custom-card-submit-btn" type="submit" onClick={handleCommentSubmit}>Gửi</button>
          <button className="custom-card-save-btn" type="button" onClick={handleButtonSave}>{isSaved ? 'Đã lưu!' : 'Lưu câu hỏi'} </button>
          <span>
          <button className="custom-card-report-btn" type="button" onClick={handleReportClick}>Báo vi phạm</button>
          {isReportModalVisible && <ReportModal isClicked={isReportModalVisible} updateIsReportSubmitted={updateIsReportSubmitted} updateIsReportModalVisible={updateIsReportModalVisible}/>}
          </span> </> : <></> }
          </form>
      </div>
      <div className="custom-card-footer">
        <h5 className="custom-card-comments-title">Trả lời:</h5>
        {comments.map((comment, index) => (
          <div className="custom-card-comments-content">
          <div> <span> Tên người đăng </span> <span>Thời gian đăng</span></div>
          <div key={index} className="custom-card-comment" style={{color: "black"}} dangerouslySetInnerHTML={{ __html: comment }}>
          </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CustomCard;


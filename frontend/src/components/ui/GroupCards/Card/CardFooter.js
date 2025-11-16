import React, { useState } from 'react';  
import { FontAwesomeIcon, faCommentDots, faThumbsUp, faThumbsDown, faFlag, ReportModal, Link } from './importCard.js';


export default function CardFooter({id}) {
    const [upvote, setUpvote] = useState(0); //đang fix lỗi, phải dùng global state để lưu tổng số Upvote
    const [downvote, setDownvote] = useState(0); //đang fix lỗi, phải dùng global state để lưu tổng số Downvote
    const [isUpvoted, setIsUpvoted ] = useState(false) //Trạng thái Clicked của Upvote
    const [isDownvoted, setIsDownvoted ] = useState(false) //Trạng thái Clicked của Downvote
    const [isReportModalVisible, setIsReportModalVisible] = useState(false); //State quản lí việc hiển thị Modal
    const [isReportSubmitted, setIsReportSubmitted] = useState(false);

    const updateIsReportSubmitted = (newValue) => {
        setIsReportSubmitted(newValue);
      };

    const updateIsReportModalVisible = (newValue) => {
        setIsReportModalVisible(newValue);
    }
    
    const handleReportClick = (event) => {
        event.preventDefault();
        event.stopPropagation(); 
        if (!isReportSubmitted) {
        setIsReportModalVisible(true); 
      }
    }
    
    const handleUpvoteClick = (event) => { //Khi Click Upvote
        event.preventDefault();
        if(isUpvoted&&!isDownvoted) { //Upvote đã bị Clicked trước đó
          setIsUpvoted(false)
        }
        else { 
          setIsUpvoted(true);
          setIsDownvoted(false);
        }
        }
    
    const handleDownvoteClick = (event) => {
        event.preventDefault();
        if(!isUpvoted&&isDownvoted) {
          setIsDownvoted(false)
        }
        else {
          setIsUpvoted(false);
          setIsDownvoted(true);
        }
        }

    return (
        <div className="card-footer">
            <div className="answer"><Link to={`/tra-loi/${id}`}><FontAwesomeIcon icon={faCommentDots} /><span>Trả lời</span></Link></div>
            <div className={isUpvoted?"voted":""}>
            <div className="upvote"><a href="#" onClick={handleUpvoteClick}><FontAwesomeIcon icon={faThumbsUp} /><span> +{upvote} Upvote</span></a></div>
            </div>
            <div className={isDownvoted?"voted":""}>
            <div className="downvote"><a href="#" onClick={handleDownvoteClick}><FontAwesomeIcon icon={faThumbsDown} /><span> -{downvote} Downvote</span></a></div>
            </div>
            <div className= {isReportSubmitted?"voted":""}>
            <div className="report"><a href="#something" onClick={handleReportClick}><FontAwesomeIcon icon={faFlag}/><span>Báo vi phạm</span></a>
            {isReportModalVisible && <ReportModal isClicked={isReportModalVisible} updateIsReportSubmitted={updateIsReportSubmitted} updateIsReportModalVisible={updateIsReportModalVisible}/>}
            </div>
            </div>
          </div>
    )
}
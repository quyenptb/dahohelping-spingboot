import React, { useEffect } from 'react';
import { useParams } from 'react-router-dom';

import { Carousel, CreateQuestionButton, CustomCard, DRL, FilterDropdown, Footer, GroupCards, IntroductionContent, NavBar, Notification, NotificationBoard, RankingTable, ReportModal, Sidebar, UserInfo, UserNote, WeatherComponent } from '../../components/index.js';
import { useContext } from 'react';
import { AppContext } from 'src/context/AppContext.js';
    
export default function AnsweringPage() {
    const {
        cards, //danh sách thẻ cho các component cần dùng
    } = useContext(AppContext);


  let { slug } = useParams();

  let   v_card = cards.find((_card) => _card.card_id == slug);

    return (
      <div>
      <NavBar/>
      <div className="main">
            <Sidebar className="sidebar" />

            <div className="little-main">
            <CreateQuestionButton />
            <CustomCard className="CustomCard" card={v_card} />            
            </div>

            <div className="right">
              <UserNote />
              <RankingTable />
            </div>

            </div>
            <Footer />   
            </div>
    )
  }
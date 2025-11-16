import { React, useEffect, useState, useContext, createContext, useParams}
from '../../utils/import.js';

import { Carousel, CreateQuestionButton, CustomCard, DRL, FilterDropdown, Footer, GroupCards, IntroductionContent, NavBar, Notification, NotificationBoard, RankingTable, ReportModal, Sidebar, UserInfo, UserNote, WeatherComponent } from '../../components/index.js';
import { AppContext } from 'src/context/AppContext.js';
export default function DRLPage() {
    return (
      <>
      <NavBar/>
      <div className="main">
            <Sidebar className="sidebar"/>
            <div className="little-main">
            <DRL />
            </div>
            <div className="right">
              <UserNote />
              <RankingTable />
            </div> 
            </div>    
            <Footer />   
      </>
    )
  }
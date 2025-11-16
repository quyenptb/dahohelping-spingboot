import { React, useEffect, useState, useContext, createContext, useParams }
from '../../utils/import';

import { Carousel, CreateQuestionButton, CustomCard, DRL, FilterDropdown, Footer, GroupCards, IntroductionContent, NavBar, Notification, NotificationBoard, RankingTable, ReportModal, Sidebar, UserInfo, UserNote, WeatherComponent } from '../../components/index.js';
import { AppContext } from 'src/context';

export default function NotificationPage() {
    return (
      <div>
      <NavBar/>
      <div className="main">
            <Sidebar className="sidebar" />
            <div className="little-main">
            <CreateQuestionButton />
            <NotificationBoard />
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
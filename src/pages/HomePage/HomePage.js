import { createContext, useState, useEffect } from 'react';
import { React, useContext,Routes, Route, Link, 
  useParams, BrowserRouter as Router, Navbar, Nav, NavDropdown, Form, FormControl, 
  Button, Table, FontAwesomeIcon, faPlusMinus, faCode, faComputer, faBook, faMicrochip, faLanguage}
  from 'src/utils/import.js';

import banner1 from 'src/assets/CarouselImage/banner1.png'
import banner2 from 'src/assets/CarouselImage/banner2.png'
import banner3 from 'src/assets/CarouselImage/banner3.png'

import { Carousel, Pagination, CreateQuestionButton, CustomCard, DRL, FilterDropdown, Footer, GroupCards, IntroductionContent, NavBar, Notification, NotificationBoard, RankingTable, ReportModal, Sidebar, UserInfo, UserNote, WeatherComponent } from '../../components/index.js';

import { AppContext, AppProvider } from 'src/context/AppContext.js';

export default function HomePage() {
  
  const [currentPage, setCurrentPage] = useState(1);
  const [cardsPerPage, setCardsPerPage] = useState(5);
  const {
    cards,
        subjects_uit,
        setSub,
        subjects_target, 
        choosenUni, //trường được chọn
      } = useContext(AppContext);

      const indexOfLastCard = currentPage * cardsPerPage;
      const indexOfFirstCard = indexOfLastCard - cardsPerPage;
      const currentCards = cards.slice(indexOfFirstCard, indexOfLastCard);


  useEffect(() => {
    if (choosenUni === "Trường Đại học") {
      setSub(subjects_uit);
    } else {
      setSub(subjects_target);
    }
  }, [choosenUni]);

  const paginate = pageNumber => setCurrentPage(pageNumber);

  return (
    <div>
      <NavBar />
      
      <Carousel src1={banner1} src2={banner2} src3={banner3}/>
      <div className="main">
        <Sidebar className="sidebar" />
        <div className="little-main">
          <CreateQuestionButton />
          <FilterDropdown
            className="filter-dropdown"
          />
          <GroupCards />
          <Pagination
        cardsPerPage={cardsPerPage}
        totalCards={cards.length}
        paginate={paginate}
      />
        </div>
        <div className="right">
          <UserNote />
          <RankingTable />
        </div>
      </div>
      <Footer />
    </div>
  );
}

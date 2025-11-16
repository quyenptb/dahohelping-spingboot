import { React, useEffect, useState, useContext, createContext, Link, useParams}
from '../../utils/import.js';

import { Carousel, CreateQuestionButton, CustomCard, DRL, FilterDropdown, Footer, GroupCards, IntroductionContent, NavBar, Notification, NotificationBoard, RankingTable, ReportModal, Sidebar, UserInfo, UserNote, WeatherComponent } from '../../components/index.js';
import { AppContext } from 'src/context/AppContext.js';
import { getUserByUsername } from 'src/services/UsersService.js';

export default function MyUserPage() {
  const {
    currentUser, //user hiện tại (principal)

} = useContext(AppContext);
    const [isLoading , setIsLoading] = useState(false);
    const { username } = useParams();
    const [userInfo, setUserInfo] = useState();
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);
    const [isMyPage, setIsMyPage] = useState(false);

    useEffect(() => {
      const fetchUser = async () => {
        setIsLoading(true);
        try {
          if (username == null) {
            userInfo = currentUser; setIsMyPage(true);
          }
          userInfo = await getUserByUsername(username); 
        } catch (error) {
          setError(error.message);
        } finally {
          setIsLoading(false);
        }
      };
      fetchUser();
    }, []);

    return (
      <div>
      <NavBar/>
      <div className="main">
            <Sidebar className="sidebar" />
            <div className="little-main">
            <CreateQuestionButton />
            ((userInfo) ? <UserInfo isMyPage={isMyPage} v_user={userInfo} />  : <p>Không tìm thấy người dùng... Quay lại <Link to="/homepage" className='link'>trang chủ </Link> </p>) :
            
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
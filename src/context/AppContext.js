import { createContext, useState, useCallback, useMemo,  useEffect } from 'react';
import { React, useContext,Routes, Route, Link, 
  useParams, BrowserRouter as Router, Navbar, Nav, NavDropdown, Form, FormControl, 
  Button, Table, FontAwesomeIcon, faPlusMinus, faCode, faComputer, faBook, faMicrochip, faLanguage}
  from 'src/utils/import.js';
import {card_data, daho_data, fal_data, maj_data, sub_data, uni_data, user_data} from 'src/data/index';
import { getAllUni } from 'src/services/UniService';

import DahoHelping from 'src/assets/icons/DahoHelping1.png'
import khtn from 'src/assets/icons/khtn.webp'
import ptnk from 'src/assets/icons/ptnk.png'
import ussh from 'src/assets/icons/ussh.png'
import iu from 'src/assets/icons/iu.png'
import bku from 'src/assets/icons/bku.webp'
import uit from 'src/assets/icons/uit.png'
import agu from 'src/assets/icons/agu.png'
import tthc from 'src/assets/icons/tthc.jpg'
import mttn from 'src/assets/icons/mttn.jpg'
import bentre from 'src/assets/icons/bentre.png'
import thsp from 'src/assets/icons/thsp.png'
import uel from 'src/assets/icons/uel.png'
import khsk from 'src/assets/icons/khsk.png'
import nmlt from 'src/assets/icons/nmlt.png'
import mangxahoi from 'src/assets/icons/mangxahoi.jpg'
import tiengnhat from 'src/assets/icons/japanese.jpg'
import giaitich from 'src/assets/icons/giaitich.png'
import csvhvn from 'src/assets/icons/csvhvn.jpg'
import dlnnh from 'src/assets/icons/dlnnh.jpg'
import gdtc1 from 'src/assets/icons/gdtc1.jpg'
import lsvmtg from 'src/assets/icons/lsvmtg.jpg'
import ppnckh from 'src/assets/icons/ppnckh.jpg'
import tkckhxh from 'src/assets/icons/tkckhxh.png'

import avatar from 'src/assets/icons/minhhieu.webp'
import avatar1 from 'src/assets/icons/dieuhuyen.jpeg'
import { getRanking } from 'src/services/UsersService';
import { getAllMaj } from 'src/services/MajService';
import { getAllFal } from 'src/services/FalService';
import { getAllSub } from 'src/services/SubServices';
import { getAllDaho } from 'src/services/DahoServices';
import { getCards } from 'src/services/CardsService';
import { Audio, Rings } from 'react-loader-spinner';
import { ScriptLoader } from '@tinymce/tinymce-react/lib/cjs/main/ts/ScriptLoader2';

const AppContext = createContext();

/*
const user = [
  {
      user_id: 1,
      username: 'hieuthuhai',
      avatar: avatar,
      fullname: "Trần Minh Hiếu",
      uni_id: bku,
      fal_id: "Khoa học máy tính",
      maj_id: "Khoa học máy tính",
      email: "minhhieutran@gmail.com",
      hometown: "Khánh Hòa",
      score: 300,
      hobby: "Lập trình, đọc sách"
  },
  {
    user_id: 2,
    username: 'dieuhuyen098',
    avatar: avatar1,
    fullname: "Nguyễn Diệu Huyền",
    uni_id: 3, //khóa ngoại trỏ đến để lấy dữ liệu từ bảng Uni
    fal_id: "Khoa Nhật Bản học", 
    maj_id: "Ngành Nhật Bản học",
    email: "shijukukaizen@gmail.com",
    hometown: "Tuyên Quang",
    score: 450,
    hobby: "Nghe nhạc, học tiếng nhật, đọc sách"
}
] */
const user = user_data;


const cards_data = card_data


const ranking_data = [
  {
    id: 1,
    name: "Trần Ngọc Minh Thi",
    uni: ptnk,
    score: "900"
},
{
  id: "2",
  name: "Phạm Hải Anh",
  uni: khtn,
  score: "800"
},

{
  id: "3",
  name: "Hà Ngọc Hân",
  uni: ussh,
  score: "720"
},

{
  id: "4",
  name: "Nguyễn Thành Đạt",
  uni: uit,
  score: "700"
},

{
  id: "5",
  name: "Lưu Bình Minh",
  uni: uel,
  score: "690"
}
] 

const subjects_uit = {
  id: 5,
    subjects: [
    {
        key: 1,
        icon: faPlusMinus,
        label: "Giải tích",
        illust: giaitich,
        desc: "Môn Giải tích là môn học ở giai đoạn kiến thức đại cương, là môn học bắt buộc đối với tất cả sinh viên. Môn học này giúp cho SV có kiến thức cơ bản về phép tính vi phân hàm nhiều biến; phép tính tích phân hàm nhiều biến (tích phân bội); tích phân đường, tích phân mặt; cũng như là kỹ năng khảo sát chuỗi số, chuỗi hàm, tích phân suy rộng,…cùng với việc nhận dạng và giải quyết một số phương trình vi phân cấp một, cấp cao,…để từ đó SV có thể tiếp tục học tập những môn chuyên ngành, hay phục vụ cho quá trình làm khóa luận tốt nghiệp"
      },
      {
        key: 2,
        icon: faCode,
        label: "Nhập môn lập trình",
        illust: nmlt,
        desc: "Môn học sẽ cung cấp các kiến thức nền tảng về máy tính, tư duy và các kỹ năng căn bản lập trình cho tất cả sinh viên các ngành Công nghệ thông tin. Đối với hệ tài năng: sinh viên sẽ được trang bị các kiến thức nâng cao về tư duy và các kỹ năng lập trình thông qua một số bài toán có độ phức tạp cao."
      },
      {
        key: 3,
        icon: faComputer,
        label: "Tổ chức và cấu trúc máy tính",
        illust: nmlt,
        desc: "Môn học sẽ cung cấp các kiến thức nền tảng về máy tính, tư duy và các kỹ năng căn bản lập trình cho tất cả sinh viên các ngành Công nghệ thông tin. Đối với hệ tài năng: sinh viên sẽ được trang bị các kiến thức nâng cao về tư duy và các kỹ năng lập trình thông qua một số bài toán có độ phức tạp cao."
      },
      {
        key: 4,
        icon: faBook,
        label: "Triết học Mac Lenin",
        illust: nmlt,
        desc: "Môn học sẽ cung cấp các kiến thức nền tảng về máy tính, tư duy và các kỹ năng căn bản lập trình cho tất cả sinh viên các ngành Công nghệ thông tin. Đối với hệ tài năng: sinh viên sẽ được trang bị các kiến thức nâng cao về tư duy và các kỹ năng lập trình thông qua một số bài toán có độ phức tạp cao."
      },
      {
        key: 5,
        icon: faMicrochip,
        label: "Nhập môn mạch số",
        illust: nmlt,
        desc: "Môn học sẽ cung cấp các kiến thức nền tảng về máy tính, tư duy và các kỹ năng căn bản lập trình cho tất cả sinh viên các ngành Công nghệ thông tin. Đối với hệ tài năng: sinh viên sẽ được trang bị các kiến thức nâng cao về tư duy và các kỹ năng lập trình thông qua một số bài toán có độ phức tạp cao."
      },
      {
        key: 6,
        icon: faLanguage,
        label: "Tiếng Nhật 1",
        illust: tiengnhat, //illustration
        desc: "Môn học cung cấp cho sinh viên các kiến thức về tiếng Nhật sơ cấp: làm quen với hệ chữ khác hệ chữ La Tinh, ngữ pháp (ngữ pháp tiếng Nhật sơ cấp; các thì, thể của động từ; trợ từ, giới từ; lượng từ vựng tương ứng), phát âm,… các kỹ năng nghe, nói, đọc, viết sơ cấp." //description
      }]
};

const subjects_ussh = {
      id: 3,
        subjects: [
        {
            key: 1,
            icon: faPlusMinus,
            label: "Thống kê cho khoa học xã hội",
            illust: tkckhxh,
            desc: "Môn Giải tích là môn học ở giai đoạn kiến thức đại cương, là môn học bắt buộc đối với tất cả sinh viên. Môn học này giúp cho SV có kiến thức cơ bản về phép tính vi phân hàm nhiều biến; phép tính tích phân hàm nhiều biến (tích phân bội); tích phân đường, tích phân mặt; cũng như là kỹ năng khảo sát chuỗi số, chuỗi hàm, tích phân suy rộng,…cùng với việc nhận dạng và giải quyết một số phương trình vi phân cấp một, cấp cao,…để từ đó SV có thể tiếp tục học tập những môn chuyên ngành, hay phục vụ cho quá trình làm khóa luận tốt nghiệp"
          },
          {
            key: 2,
            icon: faCode,
            label: "Cơ sở văn hoá Việt Nam",
            illust: csvhvn,
            desc: "Môn học sẽ cung cấp các kiến thức nền tảng về máy tính, tư duy và các kỹ năng căn bản lập trình cho tất cả sinh viên các ngành Công nghệ thông tin. Đối với hệ tài năng: sinh viên sẽ được trang bị các kiến thức nâng cao về tư duy và các kỹ năng lập trình thông qua một số bài toán có độ phức tạp cao."
          },
          {
            key: 3,
            icon: faComputer,
            label: "Lịch sử văn minh thế giới",
            illust: lsvmtg,
            desc: "Môn học sẽ cung cấp các kiến thức nền tảng về máy tính, tư duy và các kỹ năng căn bản lập trình cho tất cả sinh viên các ngành Công nghệ thông tin. Đối với hệ tài năng: sinh viên sẽ được trang bị các kiến thức nâng cao về tư duy và các kỹ năng lập trình thông qua một số bài toán có độ phức tạp cao."
          },
          {
            key: 4,
            icon: faBook,
            label: "Dẫn luận ngôn ngữ học",
            illust: dlnnh,
            desc: "Môn học sẽ cung cấp các kiến thức nền tảng về máy tính, tư duy và các kỹ năng căn bản lập trình cho tất cả sinh viên các ngành Công nghệ thông tin. Đối với hệ tài năng: sinh viên sẽ được trang bị các kiến thức nâng cao về tư duy và các kỹ năng lập trình thông qua một số bài toán có độ phức tạp cao."
          },
          {
            key: 5,
            icon: faMicrochip,
            label: "Phương pháp nghiên cứu khoa học",
            illust: ppnckh,
            desc: "Môn học sẽ cung cấp các kiến thức nền tảng về máy tính, tư duy và các kỹ năng căn bản lập trình cho tất cả sinh viên các ngành Công nghệ thông tin. Đối với hệ tài năng: sinh viên sẽ được trang bị các kiến thức nâng cao về tư duy và các kỹ năng lập trình thông qua một số bài toán có độ phức tạp cao."
          },
          {
            key: 6,
            icon: faLanguage,
            label: "Giáo dục thể chất 1",
            illust: gdtc1, //illustration
            desc: "Môn học cung cấp cho sinh viên các kiến thức về tiếng Nhật sơ cấp: làm quen với hệ chữ khác hệ chữ La Tinh, ngữ pháp (ngữ pháp tiếng Nhật sơ cấp; các thì, thể của động từ; trợ từ, giới từ; lượng từ vựng tương ứng), phát âm,… các kỹ năng nghe, nói, đọc, viết sơ cấp." //description
          }]
};

const falcuty = fal_data;
const major = maj_data;
const university = uni_data;
const dahohelping = daho_data;
const subject = sub_data;

const AppProvider = ({ children }) => {
  const [notisetting, setNotiSetting] = useState([]);
  const [showNotification, setShowNotification] = useState(false);
  const [loading, setLoading] = useState(false);
  const [currentUser, setCurrentUser] = useState(null); //principal (giả định là user[0])
  const [cards, setCards] = useState([]);
  const [card, setCard] = useState({});
  const [dahoHelping, setDahoHelping] = useState(dahohelping);
  const [choosenDaho, setChoosenDaho] = useState("DahoHelping");
  const [uni, setUni] = useState(university);
  const [choosenUni, setChoosenUni] = useState("Trường Đại học");
  const [fal, setFal] = useState(falcuty);
  const [choosenFal, setChoosenFal] = useState("Khoa");
  const [maj, setMaj] = useState(major);
  const [choosenMaj, setChoosenMaj] = useState("Ngành");
  const [sub, setSub] = useState(subject);
  const [choosenSub, setChoosenSub] = useState("Môn học");
  const [ranking, setRanking] = useState([]);
  const [subjects_target, setSubjects_target] = useState(subjects_uit.subjects);  
  const [filterState, setFilterState] = useState({
    DahoHelping: choosenDaho,
    Others: [choosenUni, choosenMaj, choosenMaj, choosenSub] //trường, ngành, khoa, môn
  });

  useEffect(() => {
    async function fetchData() {
      setLoading(true); // Set loading state to true when fetching data
      try {
        const cardsData = await getCards();
        const rankingData = await getRanking();
        setCards(cardsData);
        setRanking(rankingData);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false); // Set loading state to false when data is fetched
      }
    }

    fetchData();
  }, []);
  useEffect(() => {
      
        if (choosenUni === "Trường Đại học") {
          setSubjects_target(subjects_uit.subjects);
        } else {
          setSubjects_target(subjects_ussh.subjects);
        }
   }, [choosenUni]); // Note the empty dependency array

  const contextValue = {
    card,
    setCard,
    ranking,
    setRanking,
    subjects_uit,
    subjects_target,
    notisetting,
    setNotiSetting,
    showNotification,
    setShowNotification,
    filterState,
    setFilterState,
    currentUser,
    setCurrentUser,
    cards,
    setCards,
    dahoHelping,
    setDahoHelping,
    choosenUni,
    setChoosenUni,
    uni,
    setUni,
    fal,
    setFal,
    choosenFal,
    setChoosenFal,
    maj,
    setMaj,
    sub,
    setSub,
    choosenSub,
    setChoosenSub,
    choosenMaj,
    setChoosenMaj,
  };

  if (loading) {
    return (
      <div>
       <Rings type="Rings" color="#00BFFF" height={80} width={80} />
      </div>
    );
  }

  return (
    <AppContext.Provider value={{...contextValue}}>
      {children}
    </AppContext.Provider>
  );
};

export { AppProvider, AppContext };
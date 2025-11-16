import { React, useEffect, useState, useContext, createContext, useParams} from '../../utils/import.js';

import { Carousel, CreateQuestionButton, CustomCard, DRL, FilterDropdown, Footer, GroupCards, IntroductionContent, NavBar, Notification, NotificationBoard, RankingTable, ReportModal, Sidebar, UserInfo, UserNote, WeatherComponent } from '../../components/index.js';

import * as img from 'src/utils/images.js'

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

const _university = [
  {
    id: "1",
    name: "Trường Đại học Bách Khoa",
    icon: bku,
    code: "bku",
    src: [img.bku1, img.bku2, img.bku3],
    drl: 1,
  },
  {
    id: "2",
  name: 'Trường Đại học Khoa học tự nhiên',
  icon: khtn,
  code: "khtn",
  src: [img.khtn1, img.khtn2, img.khtn3],
  drl: 2
},
  {
    id: "3",
    name: 'Trường Đại học Khoa học Xã hội và Nhân văn',
    icon: ussh,
    code: "ussh",
    src: [img.ussh1, img.ussh2, img.ussh3],
    drl: 1
  },  
  {
    id: "4",
    name: "Trường Đại học Quốc tế",
    icon: iu,
    code: "iu",
    src: [img.iu1, img.iu2, img.iu3],
    drl: 5
    },
    {
      id: "5",
     name: 'Trường Đại học Công nghệ thông tin',
     icon: uit,
     code: "uit",
     src: [img.uit1, img.uit2, img.uit3],
     drl: 3
    }, 
    {
      id: "6",
      name: 'Trường Đại học Kinh tế - Luật',
      icon: uel,
      code: "uel",
      src: [img.uel1, img.uel2, img.uel3],
      drl: 5
    },
    {
      id: "7",
      name: 'Trường Đại học An Giang',
      icon: agu,
      code: "agu",
      src: [img.agu1, img.agu2, img.agu3],
      drl: 10
    },
    {
    id: "8",
    name: 'Trường Đại học Khoa học sức khỏe',
    icon: khsk,
    code: "khsk",
    src: [img.khsk1, img.khsk2, img.khsk3],
    drl: 1
  },
  {
    id: "9",
    name: 'Viện Môi trường - Tài nguyên',
    icon: mttn,
    code: "mttn",
    src: [img.mttn1, img.mttn2, img.mttn3]
  },
  {
    id: "10",
    name: 'Khoa Chính trị - Hành chính',
    icon: tthc,
    code: "tthc",
    src: [img.tthc1, img.tthc2, img.tthc3]
  },
  {
    id: "11",
    name: 'Phân hiệu ĐHQG tại Bến Tre',
    icon: bentre,
    code: "bentre",
    src: [img.bentre1, img.bentre2, img.bentre3]
  },
  {
    id: "12",
    name: 'Trường Phổ thông Năng khiếu',
    icon: ptnk,
    code: "ptnk",
    src: [img.ptnk1, img.ptnk2, img.ptnk3]
  },
  {
    id: "13",
    name: 'Trường Trung học Thực hành sư phạm An Giang',
    icon: thsp,
    code: "thsp",
    src: [img.thsp1, img.thsp2, img.thsp3]
  }
]
export default function IntroductionPage() {
    return (
      <div>
      <NavBar/>
      <IntroductionContent university={_university} />
      <Footer />   
            </div>
    )
  }
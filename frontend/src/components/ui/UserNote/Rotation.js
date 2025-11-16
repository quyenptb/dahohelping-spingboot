import React, { useContext } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Rotation.css';
import WeatherComponent from '../WeatherComponent/WeatherComponent';
import { AppContext } from 'src/context';

//Dùng context userName của thisUser nha má
export default function Rotation({userName}) {
    const {currentUser} = useContext(AppContext);
    const ban = "bạn";
    return ( 
      <div className="outer-heading" style={{fontFamily: 'sans-serif'}}>
          <WeatherComponent id={1} city={'Ho Chi Minh City'}/>
          { currentUser ? <WeatherComponent id={2} city={currentUser.hometown}/> : <></>  }
              <h1>
                  Chúc { currentUser ? currentUser.username : "bạn" } một ngày <div className="inner-headings">
                      <span className="doRotate">
                      vui vẻ <br/>
                      hạnh phúc <br/>
                      năng suất <br/>
                      </span>
                  </div>
                  {currentUser ? <></> : <p> Đăng nhập ngay! </p>}
              </h1>
          </div>
    )
}
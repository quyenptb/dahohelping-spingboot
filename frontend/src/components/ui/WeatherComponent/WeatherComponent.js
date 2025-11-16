import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { AppContext } from 'src/context';

const WeatherComponent = ({id, city}) => {  
  const [weatherData, setWeatherData] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const apiKey = `10fda11c88aed7f31a1b8ecc2f4f1947`;
        //const city = 'Ho Chi Minh City';
        const url = `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${apiKey}&units=metric`;
        const response = await axios.get(url);
        setWeatherData(response.data);
      } catch (error) {
        console.error('Đã xảy ra lỗi trong quá trình lấy dữ liệu:', error);
      }
    };

    fetchData();
  }, []);
  //const desc = weatherData.weather[0].description

  const getWeatherText = () => {
    if (id === 2 ) return '';
    if (weatherData) {
      const description = weatherData.weather[0].description;

      let weatherText;

      if (description.includes('rain')) {
        weatherText = ' Hôm nay có thể sẽ có mưa! Bạn nhớ mang dù hoặc áo mưa nhé!';
      } else if (description.includes('sun')) {
        weatherText = ' Trời nắng nóng, hãy chú ý cẩn thận khi ra ngoài bạn nhé!';
      } else if (description.includes('clouds')) {
        weatherText = ' Trời nhiều mây, khá âm u nhưng ngày mai sẽ lại nắng thôi!';
      }
      return weatherText;
    }

    return '';
  };

  return ( id===2 && city==="Ho Chi Minh City" ? <></> :
    <>
      {(weatherData != null) && (
        <>
          <>{weatherData.name}, {weatherData.main.temp}°C
          <img src={`http://openweathermap.org/img/w/${weatherData.weather[0].icon}.png`} alt="Weather icon" />
          <p> {getWeatherText()}</p>
          </>
        </>
      )}
    </> 
  );
};

export default WeatherComponent;

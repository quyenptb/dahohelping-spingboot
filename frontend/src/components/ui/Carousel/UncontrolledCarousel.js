import React from 'react';
import CarouselImage from './CarouselImage.js';
import Carousel from 'react-bootstrap/Carousel';
import 'bootstrap/dist/css/bootstrap.min.css';

function UncontrolledCarousel({src1, src2, src3}) {
  
    return (
      <Carousel>
        <Carousel.Item>
          <CarouselImage src={src1} />
        </Carousel.Item>
        <Carousel.Item>
          <CarouselImage src={src2} />
        </Carousel.Item>
        <Carousel.Item>
          <CarouselImage src={src3} />
        </Carousel.Item>
      </Carousel>
    );
  }

  export default UncontrolledCarousel;
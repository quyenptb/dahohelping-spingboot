import React from 'react';

const CarouselImage = ({ src }) => {
  return (
    <img
      className="d-block w-100"
      src={src}
      alt='Slide'
    />
  );
};

export default CarouselImage;
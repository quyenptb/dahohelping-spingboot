import React, { useContext } from 'react';
import UncontrolledCarousel from '../Carousel/UncontrolledCarousel'
import Col from 'react-bootstrap/Col';
import 'bootstrap/dist/css/bootstrap.min.css';
import ListGroup from 'react-bootstrap/ListGroup';
import Row from 'react-bootstrap/Row';
import Tab from 'react-bootstrap/Tab';
import { AppContext } from 'src/context';


function IntroductionContent({university}) {
  const {uni}  = useContext(AppContext);
  return (
    <Tab.Container id="list-group-tabs-example" defaultActiveKey="#bach-khoa-ho-chi-minh">
      <Row>
        <Col sm={4}>
          <ListGroup>
            {uni.map(({name, code}) => (
              <ListGroup.Item action href={`#${code}`}>
              {name}
            </ListGroup.Item>
            ))}
          </ListGroup>
        </Col>
        <Col sm={8}>
          <Tab.Content>
          {uni.map(({code, src}) => (
              <Tab.Pane eventKey={`#${code}`}>
              
              <UncontrolledCarousel src1={src[0]} src2={src[1]} src3={src[2]} /> 
              </Tab.Pane>
            ))}
          </Tab.Content>
        </Col>
      </Row>
    </Tab.Container>
  );
}

export default IntroductionContent;
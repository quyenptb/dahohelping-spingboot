import { React, useState,BrowserRouter as Router, FontAwesomeIcon}
    from '../../../utils/import.js';
import "../../../App.css"
import { faCode } from '@fortawesome/free-solid-svg-icons';
import defaultDesc from 'src/assets/icons/default_desc.webp'

function SidebarItem({ subj }) {
    const [cardPosition, setCardPosition] = useState({ x: 0, y: 0 });
    const [isHovered, setIsHovered] = useState(false);
  
    const handleMouseEnter = (event) => {
      const { clientX, clientY } = event;
      setCardPosition({ x: clientX + 10, y: clientY + 10 }); // Định vị card bên cạnh chuột với độ lệch 10px
      setIsHovered(true);
    };
  
    const handleMouseLeave = () => {
      setIsHovered(false);
    };
  
    return (
      <div onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
        <div>
      <li className="nav-item">
        <a className="nav-link" href="#">
          <FontAwesomeIcon icon={faCode} />
          <span> {subj.label}</span>
        </a>
      </li>
      </div>
      {isHovered && (
          <div
            className="card"
            style={{ zIndex: '1000', width: '50vh', position: 'fixed', top: cardPosition.y, left: cardPosition.x, backgroundColor: 'white', padding: '10px', border: '1px solid gray', display: 'flex' }}
          >
            <img src= {defaultDesc} alt="Subject Description" style={{ width: '200px', height: '100px' }}/>
            <span>{subj.desc}</span>
          </div>
        )}
      </div>
    );
  }
  export default SidebarItem;
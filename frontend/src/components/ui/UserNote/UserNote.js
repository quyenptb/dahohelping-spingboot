import { React } from '../../../utils/import.js';
import Rotation from './Rotation.js';
export default function UserNote() {
    const currentDate = new Date();
    return (
      <div style={{ maxWidth: '18rem', marginBottom: '1rem', backgroundColor: '#FFEF82',  boxShadow: '5px 5px 15px rgba(0.1,0.1,0.1,0.1)', padding: '20px' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', textTransform: 'uppercase', fontSize: '2.5vh' }}>Note
        <h6 style={{ position: 'relative', display: 'inline', fontSize: '2.5vh', marginTop: '4px' }}>
            <time>Date: {currentDate.toLocaleDateString()}</time>
          </h6>
        </div>    
          <div>
          <p>
            <Rotation userName={"Bích Quyên"} />
          </p>
        </div>
      </div>
    );
  }
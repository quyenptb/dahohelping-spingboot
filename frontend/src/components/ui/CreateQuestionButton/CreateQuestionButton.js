import React, { useEffect, useState, useRef, useContext, createContext } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Form, Button, Modal, Dropdown, DropdownButton } from 'react-bootstrap';
import { Editor } from '@tinymce/tinymce-react'; //Thư viện TinyMCE giúp tạo ô text có định dạng
import Notification from 'src/components/ui/Notification/Notification.js';

import {daho_data, fal_data, maj_data, sub_data, uni_data, user_data} from 'src/data/index'
import { AppContext } from 'src/context';

const fal = fal_data;
const maj = maj_data;
const uni = uni_data;
const sub = sub_data;
const daho = daho_data;

const QuesContext = createContext();

function DropDownCustom({ toggle, listItems, onSelect }) {

const [value, setValue] = useState(toggle);

const handleChoice = selectedItem => {
  setValue(selectedItem);
  onSelect(selectedItem);
};

return (
  <DropdownButton title={value} id="dropdown-basic">
      {Array.isArray(listItems) && listItems.map(item => (
<Dropdown.Item key={item.id} onClick={() => handleChoice(item.name)}>
  {item.name}
</Dropdown.Item>
))}
  </DropdownButton>
);
}

function FilterDropdown() {
  const {filterState, setFilterState} = useContext(QuesContext);

  /*
useEffect(() => {
  const fetchData = async () => {
    try {
      const response = await fetch('/api/filter', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(filterState)
      });
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
      // setUni(data); // Update the cards with new data
    } catch (error) {
      console.error('There was an error!', error);
    }
  };

  fetchData();
}, [filterState]);
*/

const handleSelect = (dropdownName, selectedItem) => {
  setFilterState(prevState => ({
    ...prevState,
    [dropdownName]: selectedItem
  }));
};

return (
  <>
    <div className="btn-group">
      <DropDownCustom
        toggle="DahoHelping"
        listItems={daho}
        onSelect={item => handleSelect('DahoHelping', item)}
      />
      {filterState.DahoHelping === 'DahoHelping' && (
          <>
      <DropDownCustom
        toggle="Trường Đại học"
        listItems={uni}
        onSelect={item => handleSelect('Others', [item, null, null, null])}
      />
      <DropDownCustom
        toggle="Khoa"
        listItems={fal}
        onSelect={item => handleSelect('Others', [filterState.Others[0], item, null, null])}
      />
      <DropDownCustom
        toggle="Ngành"
        listItems={maj}
        onSelect={item => handleSelect('Others', [filterState.Others[0], filterState.Others[1], item, null])}
      />
      <DropDownCustom
        toggle="Môn"
        listItems={sub}
        onSelect={item => handleSelect('Others', [filterState.Others[0], filterState.Others[1], filterState.Others[2], item])}
      />
      </>
  )}
  </div>
  </>
  )
}

const CreateQuestionButton = () => {
  const [filterState, setFilterState] = useState({
    DahoHelping: 'DahoHelping',
    Others: [null, null, null, null] //trường, ngành, khoa, môn
  })
  const [notisetting, setNotiSetting] = useState([]);
  const [showNotification, setShowNotification] = useState(false); // Thêm state để kiểm soát hiển thị thông báo
  const QuesProvider = QuesContext.Provider;
  const { currentUser } = useContext(AppContext);
  const editorRef = useRef(null); //khởi tạo textarea
  const [formData, setFormData] = useState({
    title: '',
    score: 0,
    content: '',
    DahoHelping: 'DahoHelping',
    uni: '',
    fal: '',
    maj: '',
    sub: '',
  });
  const [show, setShow] = useState(false);
  const [A, setA] = useState(0); // Initial value for A
  
    useEffect(() => {
      if (currentUser) {
        const userScore = currentUser.score; // Assume this function retrieves user's score
        setA(userScore); // Set A to user's score
        }
      }
      , [currentUser? currentUser.score : null]);

    const handleInputChange = (e) => {
      const { name, value } = e.target;
      setFormData({ ...formData, [name]: value });
    };
    const handleEditorChange = (content, editor) => {
      editorRef.current = editor;
      setFormData({ ...formData, content: content });
    };


const handleSubmit = () => {
  if (!formData.title || !/^(?![0-9]*$)[A-Za-z0-9]+$/.test(formData.title)) {
    setShowNotification(true);
    setNotiSetting({message: 'Tiêu đề không được bỏ trống, không được gồm toàn số hoặc kí tự đặc biệt!', type: "error"});
    setFormData({
      title: '',
      score: 0,
      content: '',
    }
    );
    return;
  }

  // Kiểm tra xem nội dung đã được nhập đúng theo yêu cầu chưa
  if (!formData.content.trim()) {
    setShowNotification(true);
    setNotiSetting({message: 'Nội dung không được bỏ trống!', type: "error"});
    setFormData({
      title: '',
      score: 0,
      content: '',
    }
    );
    return;
  }

  // Kiểm tra xem đã chọn các button chưa
  if (!filterState.DahoHelping || filterState.Others.some(item => item === null)) {
    setShowNotification(true);
    setNotiSetting({message: 'Vui lòng chọn đầy đủ thông tin môn học của câu hỏi!', type: "error"});
    setFormData({
      title: '',
      score: 0,
      content: '',
    }
    );
    return;
  }

  // Kiểm tra điểm số có lớn hơn 0 và nhỏ hơn hoặc bằng giá trị A
  if (formData.score <= 0 || formData.score > A) {
    setShowNotification(true);
    setNotiSetting({message: `Số điểm phải lớn hơn 0 và nhỏ hơn hoặc bằng ${A}`, type: "error"});
    setFormData({
      title: '',
    }
    );
    return;
  }
  const new_form = {
    title: formData.title,
    score: formData.score,
    content: formData.content,
    DahoHelping: (filterState.DahoHelping === null ? '' : filterState.DahoHelping),
    uni: (filterState.Others[0] === null ? 'bing' : filterState.Others[0]),
    fal: (filterState.Others[1] === null ? 'bing' : filterState.Others[1]),
    maj: (filterState.Others[2] === null ? 'bing' : filterState.Others[2]),
    sub: (filterState.Others[3] === null ? 'bing' : filterState.Others[3]),
  
  }
  if (editorRef.current) {
    editorRef.current.setContent('');
  }
            //gửi API về cho backend
            setShowNotification(true);
            setNotiSetting({ message: 'Tạo câu hỏi thành công!', type: 'success' });
            setFormData({
              title: '',
              score: 0,
              content: '',
            }
            );
          }
      
  // Send the API request to the backend
    const handleClose = () => setShow(false);
    const handleShow = () => {
      if (!currentUser) {
        setShowNotification(true);
        setNotiSetting({message: 'Bạn phải đăng nhập trước khi đặt câu hỏi!', type: "error"});
        return;
      }
      else setShow(true);
    }
  
    return (
      <div>
        <Button onClick={handleShow} style={styles.button}>Tạo câu hỏi trong vài giây!</Button>
        <Modal show={show} onHide={handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>Tạo câu hỏi mới</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form.Group>
              <Form.Label htmlFor='title'>Tiêu đề câu hỏi</Form.Label>
              <Form.Control required name='title' id='title' type="text" value={formData.title} onChange={handleInputChange} />
              <Form.Label htmlFor='content'>Nội dung câu hỏi</Form.Label>
            <Editor required id='content' name='content'
            onInit={(evt, editor) => (editorRef.current = editor)}
            apiKey='ubvf47mx487okwyj5ynvjw2ruufjrou0oyb6mq4b8tygjopl'
              initialValue=""
              init={{
                placeholder: `Nhập nội dung câu hỏi của bạn. Hãy sử dụng từ ngữ lịch sự và dành lời cảm ơn cho những người trả lời!`,
                height: 200,
                menubar: false,
                plugins: [
                  'advlist autolink lists link image imagetools charmap print preview anchor',
                  'searchreplace visualblocks code fullscreen',
                  'insertdatetime media table paste code help wordcount'
                ],
                toolbar:
                  'image | code | undo redo | formatselect | bold italic underline | \
                  alignleft aligncenter alignright alignjustify | \
                  bullist numlist outdent indent | removeformat | help'
              }}
              onEditorChange={handleEditorChange}
            />
            <Form.Label htmlFor='score'>Số điểm</Form.Label>
            <Form.Control required name='score' id='score' type="number" value={formData.score} step={10} max={A} onChange={handleInputChange} />
            <QuesProvider value={{filterState, setFilterState}}>
            <FilterDropdown/>
            </QuesProvider>
            </Form.Group>
          </Modal.Body>
          <Modal.Footer>
            <Button type="reset" variant="secondary" onClick={handleClose}>
              Huỷ bỏ
            </Button>
            <Button type="submit" variant="primary" onClick={() => {
              handleSubmit();
              handleClose();
            }}>
              Tạo câu hỏi
            </Button>
          </Modal.Footer>
        </Modal>
        {
  showNotification === true && (
    <Notification
      message={notisetting.message}
      type={notisetting.type}
      onClose={() => setShowNotification(false)} // Đặt setShowNotification(false) khi thông báo được đóng
    />
  )
}
      </div>

    );
  };
   
  const styles = {
    container: {
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      //height: '100vh',
    },
    button: {
      position: 'relative',
      alignSelf: 'center',
      padding: '15px 30px',
      fontSize: '1.5rem',
      fontWeight: 'bold',
      backgroundColor: '#ff4500',
      color: 'white',
      border: 'none',
      borderRadius: '8px',
      boxShadow: '0px 4px 6px rgba(0, 0, 0, 0.1)',
      cursor: 'pointer',
      transition: 'transform 0.3s ease'
    }
  };

  export default CreateQuestionButton;
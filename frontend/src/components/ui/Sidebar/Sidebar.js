import { React, useContext, BrowserRouter as Router}
    from '../../../utils/import.js';
import { AppContext } from 'src/context/AppContext.js';
import SidebarItem from './SidebarItem.js';
import { faCode } from '@fortawesome/free-solid-svg-icons';

const subjects_uit = {
  id: 5,
    subjects: [
    {
        key: 1,
        icon: faCode,
        label: "Giải tích",
        illust: "src/assets/icons/default_desc.webp",
        desc: "Môn Giải tích là môn học ở giai đoạn kiến thức đại cương, là môn học bắt buộc đối với tất cả sinh viên. Môn học này giúp cho SV có kiến thức cơ bản về phép tính vi phân hàm nhiều biến; phép tính tích phân hàm nhiều biến (tích phân bội); tích phân đường, tích phân mặt; cũng như là kỹ năng khảo sát chuỗi số, chuỗi hàm, tích phân suy rộng,…cùng với việc nhận dạng và giải quyết một số phương trình vi phân cấp một, cấp cao,…để từ đó SV có thể tiếp tục học tập những môn chuyên ngành, hay phục vụ cho quá trình làm khóa luận tốt nghiệp"
      },
      {
        key: 2,
        icon: faCode,
        label: "Nhập môn lập trình",
        illust: "src/assets/icons/default_desc.webp",
        desc: "Môn học sẽ cung cấp các kiến thức nền tảng về máy tính, tư duy và các kỹ năng căn bản lập trình cho tất cả sinh viên các ngành Công nghệ thông tin. Đối với hệ tài năng: sinh viên sẽ được trang bị các kiến thức nâng cao về tư duy và các kỹ năng lập trình thông qua một số bài toán có độ phức tạp cao."
      },
      {
        key: 3,
        icon: faCode,
        label: "Tổ chức và cấu trúc máy tính",
        illust: "src/assets/icons/default_desc.webp",
        desc: "Môn học sẽ cung cấp các kiến thức nền tảng về máy tính, tư duy và các kỹ năng căn bản lập trình cho tất cả sinh viên các ngành Công nghệ thông tin. Đối với hệ tài năng: sinh viên sẽ được trang bị các kiến thức nâng cao về tư duy và các kỹ năng lập trình thông qua một số bài toán có độ phức tạp cao."
      },
      {
        key: 4,
        icon: faCode,
        label: "Triết học Mac Lenin",
        illust: "src/assets/icons/default_desc.webp",
        desc: "Môn học sẽ cung cấp các kiến thức nền tảng về máy tính, tư duy và các kỹ năng căn bản lập trình cho tất cả sinh viên các ngành Công nghệ thông tin. Đối với hệ tài năng: sinh viên sẽ được trang bị các kiến thức nâng cao về tư duy và các kỹ năng lập trình thông qua một số bài toán có độ phức tạp cao."
      },
      {
        key: 5,
        icon: faCode,
        label: "Nhập môn mạch số",
        illust: "src/assets/icons/default_desc.webp",
        desc: "Môn học sẽ cung cấp các kiến thức nền tảng về máy tính, tư duy và các kỹ năng căn bản lập trình cho tất cả sinh viên các ngành Công nghệ thông tin. Đối với hệ tài năng: sinh viên sẽ được trang bị các kiến thức nâng cao về tư duy và các kỹ năng lập trình thông qua một số bài toán có độ phức tạp cao."
      },
      {
        key: 6,
        icon: faCode,
        label: "Tiếng Nhật 1",
        illust: "src/assets/icons/default_desc.webp",//illustration
        desc: "Môn học cung cấp cho sinh viên các kiến thức về tiếng Nhật sơ cấp: làm quen với hệ chữ khác hệ chữ La Tinh, ngữ pháp (ngữ pháp tiếng Nhật sơ cấp; các thì, thể của động từ; trợ từ, giới từ; lượng từ vựng tương ứng), phát âm,… các kỹ năng nghe, nói, đọc, viết sơ cấp." //description
      }]
};

function Sidebar() {
    //const {} = useContext(AppContext);
    //const subArray = Array.isArray(sub) ? sub : [];
    const sidebar_sub = subjects_uit.subjects

    return (
      <nav className="col-2 col-md-3 col-lg-2 d-md-block bg-light d-sm-none sidebar">
        <div className="sidebar-sticky">
          <ul className="nav flex-column">
          {sidebar_sub.map((u) => ( <SidebarItem subj={u} />))}
          </ul>
        </div>
      </nav>
    )
} 


  export default Sidebar;
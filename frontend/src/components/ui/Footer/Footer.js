
export default function Footer() {
    return (
      <footer className="text-white text-center text-lg-start footer">
        <div className="container p-4">
          <div className="row">
            <div className="col-lg-6 col-md-12 mb-4 mb-md-0">
              <h5 className="text-uppercase">DAHOHELPING</h5>
              <p>
                DahoHelping là trang web thuộc sở hữu của Đại học Quốc gia Thành phố Hồ Chí Minh. Được ra đời vào năm 2024 bởi ý tưởng của nhóm sinh viên trường Đại học Công nghệ thông tin, DahoHelping đã trở thành
                địa chỉ tin cậy để các bạn học sinh, sinh viên toàn hệ thống ĐHQG hỏi đáp, chia sẻ, giúp đỡ nhau trong học tập và cuộc sống. Với giao diện đơn giản, dễ sử dụng, chức năng phong phú và độ an toàn cao, DahoHelping không ngừng phát triển để hướng tới việc trở thành trang Forum học sinh, sinh viên tốt nhất trên toàn địa bàn thành phố Hồ Chí Minh
              </p>
            </div>
            <div className="col-lg-3 col-md-6 mb-4 mb-md-0">
              <h5 className="text-uppercase">Liên hệ nhóm sinh viên</h5>
              <ul className="list-unstyled mb-0">
                <li>
                  <a href="#" className="text-white">Dương Minh Mẫn</a>
                </li>
                <li>
                  <a href="#!" className="text-white">Nguyễn Thành Tài</a>
                </li>
                <li>
                  <a href="#!" className="text-white">Bùi Quốc Cường</a>
                </li>
                <li>
                  <a href="#!" className="text-white">Phan Thị Bích Quyên</a>
                </li>
              </ul>
            </div>
            <div className="col-lg-3 col-md-6 mb-4 mb-md-0">
              <h5 className="text-uppercase mb-0">Tài liệu</h5>
              <ul className="list-unstyled">
                <li>
                  <a href="#!" className="text-white">Link 1</a>
                </li>
                <li>
                  <a href="#!" className="text-white">Link 2</a>
                </li>
                <li>
                  <a href="#!" className="text-white">Link 3</a>
                </li>
                <li>
                  <a href="#!" className="text-white">Link 4</a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div className="text-center p-3" style={{ backgroundColor: 'rgba(0, 0, 0, 0.2)' }}>
          © 2024 Copyright:
          <a className="text-white" href="https://DahoHelping.com/">DahoHelping.com</a>
        </div>
      </footer>
    );
  }
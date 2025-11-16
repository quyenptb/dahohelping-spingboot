import React, { useEffect, useState, useContext } from 'react';
import { Navbar, Nav, NavDropdown } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import account from '../../../assets/icons/account.png';
import DahoHelping from '../../../assets/icons/DahoHelping1.png';
import { AppContext } from 'src/context/AppContext.js';
import SearchBar from '../SearchComponent/SearchBar.js';
import SearchResultsList from '../SearchComponent/SearchResultsList.js';

export default function NavBar() {
  const { currentUser } = useContext(AppContext);
  const [loading, setLoading] = useState(false);
  const [results, setResults] = useState(null);
  const [isClicked, setIsClicked] = useState(false);

  const handleClickOutside = (event) => {
    setIsClicked(true);
    const searchBar = document.getElementById('search-bar');
    if (searchBar && !searchBar.contains(event.target)) {
      setResults(null);
    }
  };

  return (
    <Navbar expand="lg" className="my-navbar">
      <Navbar.Brand href="#">
        <img src={DahoHelping} alt="DahoHelping" width="150rem" height="100rem" />
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="me-auto mb-3 mb-lg-0">
          <Nav.Link className="active">
            <Link to="/homepage" className="link">
              Trang chủ
            </Link>
          </Nav.Link>
          <Nav.Link>
            <Link to="/introduction" className="link">
              Giới thiệu
            </Link>
          </Nav.Link>
          <Nav.Link>
            <Link to="/behaviour-score" className="link">
              Điểm rèn luyện
            </Link>
          </Nav.Link>
          <NavDropdown title="Thông báo" className="link" id="basic-nav-dropdown">
            <NavDropdown.Item>
              <Link to="/systems/notification" className="link">
                Hệ thống
              </Link>
            </NavDropdown.Item>
            <NavDropdown.Item>
              <Link to="/users/notification" className="link">
                Cá nhân
              </Link>
            </NavDropdown.Item>
          </NavDropdown>
        </Nav>

        <div className="search-bar-container">
  <SearchBar id="search-bar" setLoading={setLoading} setResults={setResults} />
  {(!isClicked && results !== '' && results !== null && results.title !== '') ? 
    <SearchResultsList loading={loading} results={results} /> : null
  }
</div>

        <NavDropdown
          className="account"
          title={<img src={account} alt="account" style={{ width: '50px', height: '50px' }} />}
          id="basic-nav-dropdown"
        >
          {console.log("Day la", currentUser)}
          {currentUser != null ? (
            <>
              <NavDropdown.Item href="/users/me">Thông tin cá nhân</NavDropdown.Item>
              <NavDropdown.Item href="/logout">Đăng xuất</NavDropdown.Item>
            </>
          ) : (
            <>
              <NavDropdown.Item href="/login">Đăng nhập</NavDropdown.Item>
              <NavDropdown.Item href="/signin">Đăng kí</NavDropdown.Item>
            </>
          )}
        </NavDropdown>
      </Navbar.Collapse>
    </Navbar>
  );
}
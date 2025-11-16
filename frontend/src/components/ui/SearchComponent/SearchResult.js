import "./SearchResult.css";
import { useNavigate } from "react-router-dom";
const SearchResult = ({ result, id }) => {
  const navigate = useNavigate();
  const handleClickResult = () => {
    navigate(`/cards/${id}`);
  };
  return (
    <div
      className="search-result"
      onClick={(e) => handleClickResult}
    >
      {result}
    </div>
  );
};

export default SearchResult;
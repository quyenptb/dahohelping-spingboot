import { useEffect, useState } from "react";
import { faSearch } from "@fortawesome/free-solid-svg-icons";

import "./SearchBar.css";
import { fetchTags } from "src/services/SearchService";

const SearchBar = ({ setLoading,setResults }) => {
  const [input, setInput] = useState(null);

  useEffect(() => {
    setLoading(true);
    fetchTags(input)
    .then((data) => {
      setResults(data);
    })
    setLoading(false);
  },[input])

  const handleChange = (value) => {
    setInput(() => value);
  }
  return (
    <div className="input-wrapper">
      <faSearch id="search-icon" />
      <input
        placeholder="Tìm kiếm câu hỏi"
        value={input}
        onChange={(e) => handleChange(e.target.value)}
      />
    </div>
  );
};

export default SearchBar;


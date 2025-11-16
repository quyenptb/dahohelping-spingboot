import "./SearchResultsList.css";
import SearchResult from "./SearchResult";
import { Rings } from "react-loader-spinner";
import { Link } from "react-router-dom";

 const SearchResultsList = ({ results, loading }) => {
  return (
    results.length === 0 ? <> </>:
    <div className="results-list" style={{width: "inherit", position: "absolute", zIndex: "1000"}}>
      {results.map((result, id) => {
        return <Link to={`/cards/${result.id}`} > <SearchResult result={result.title} key={result.id} /> </Link>;
      })}
    </div>
  );
};

export default SearchResultsList;
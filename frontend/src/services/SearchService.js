import axios from 'axios';

export const fetchTags = async (searchTerm) => {
  try {
    const slug = decodeURIComponent(searchTerm);
    const response = await axios.get(`http://localhost:3000/search`); // Sử dụng đúng URL của API
    const data = response.data; // Lấy dữ liệu từ phản hồi
    const lowerSearchTerm = searchTerm.toLowerCase();
    // Lọc dữ liệu để chỉ lấy những phần tử có title chứa searchTerm
    const filteredData = data.filter(item => 
      item.title.toLowerCase().includes(searchTerm.trim().toLowerCase())
    );

    // Lấy 5 phần tử đầu tiên
    const searchSuggestions = filteredData.slice(0, 5);

    return searchSuggestions;
  } catch (error) {
    console.error('Error retrieving tags:', error);
    throw new Error('Server error');
  }
};

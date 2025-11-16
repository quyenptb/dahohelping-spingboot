import axios from "axios";
import convertToSlug from "src/utils/convertToSlug";

export const getAllUni = async () => {
  try {
    const response = await axios.get(`http://localhost:3000/university`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching all universities`, error);
    throw error;
  }
};

export const getUniById = async (uni_id) => {
  try {
    const response = await axios.get(`api/uni/${uni_id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching university with id: ${uni_id}`, error);
    throw error;
  }
};

export const getUniByName = async (name) => {
  try {
    const slug = decodeURIComponent(name);
    const response = await axios.get(`api/university/?name=${slug}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching university with name: ${name}`, error);
    throw error;
  }
};



import axios from "axios";
import convertToSlug from "src/utils/convertToSlug";

export const getAllDaho = async () => {
  try {
    const response = await axios.get(`http://localhost:3000/dahohelping`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching all DahoHelping tag`, error);
    throw error;
  }
};

export const getDahoById = async (daho_id) => {
  try {
    const response = await axios.get(`/api/daho/${daho_id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching falcuty with id: ${daho_id}`, error);
    throw error;
  }
};

export const getDahoByName = async (name) => {
  try {
    const slug = decodeURIComponent(name);
    const response = await axios.get(`/api/daho/?name=${slug}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching falcuty with name: ${name}`, error);
    throw error;
  }
};

import axios from "axios";
import convertToSlug from "src/utils/convertToSlug";

export const getAllFal = async () => {
  try {
    const response = await axios.get(`http://localhost:3000/fal`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching all falcuties`, error);
    throw error;
  }
};

export const getFalByUni = async (uni_id) => {
  try {
    const response = await axios.get(`/api/fal/uni#${uni_id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching falcuty with university: ${uni_id}`, error);
    throw error;
  }
};

export const getFalById = async (fal_id) => {
  try {
    const response = await axios.get(`/api/fal/${fal_id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching falcuty with id: ${fal_id}`, error);
    throw error;
  }
};

export const getFalByName = async (name) => {
  try {
    const slug = decodeURIComponent(name);
    const response = await axios.get(`/api/fal/?name=${slug}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching falcuty with name: ${name}`, error);
    throw error;
  }
};

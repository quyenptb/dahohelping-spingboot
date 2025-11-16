import axios from "axios";

export const getAllSub = async () => {
  try {
    const response = await axios.get(`http://localhost:3000/sub`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching all subjects`, error);
    throw error;
  }
};

export const getSubById = async (sub_id) => {
  try {
    const response = await axios.get(`/api/sub/${sub_id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching subject ${sub_id}`, error);
    throw error;
  }
};

export const getSubByName = async (name) => {
  try {
    const slug = decodeURIComponent(name);
    const response = await axios.get(`/api/sub/?name=${slug}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching subject ${name} `, error);
    throw error;
  }
};

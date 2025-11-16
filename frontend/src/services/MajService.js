import axios from "axios";
import convertToSlug from "src/utils/convertToSlug";
import authHeader from "./auth-header";


export const getAllMaj = async () => { 
  try {
    const response = await axios.get("http://localhost:3000/maj");
    return response.data;
  } catch (error) {
    console.error("Error fetching majors", error);
    throw error;
  }
}

export const getMajByFalId = async (fal_id) => { //for filter dropdown 
  try {
    const response = await axios.get(`/api/fal/${fal_id}/maj`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching majors `, error);
    throw error;
  }
};
export const getMajByUni = async (uni_id) => { //for filter dropdown 
  try {
    const response = await axios.get(`/api/uni/${uni_id}/maj`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching majors with university: ${uni_id}`, error);
    throw error;
  }
};

export const getMajById = async (maj_id) => { //for MajPage ( Majors page - haven't done yet)
  try {
    const response = await axios.get(`/api/maj/${maj_id}`);
    return response.data;
  } catch (error) {
    console.error(`Error fetching majors with id: ${maj_id}`, error);
    throw error;
  }
};

export const getMajByName = async (name) => { //for searchServices 
  try {
    const response = await axios.get(`/api/maj/${convertToSlug(name)}`, name);
    return response.data;
  } catch (error) {
    console.error(`Error fetching majors with name: ${name}`, error);
    throw error;
  }
};

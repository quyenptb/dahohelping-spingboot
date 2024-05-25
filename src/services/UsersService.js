import axios from 'axios';
import authHeader from 'src/services/auth-header'; //forJWT

export const login = async (username, password) => {
  try {
    const response = await axios.post("auth/login", { username, password });
    if (response?.data.token) {
      localStorage.setItem("token", JSON.stringify(response.data.token));
    }
    return response?.data;
  } catch (e) {
    console.log('Error logging in', e);
    throw new Error('Login failed');
  }
};

export const signin = async (user) => {
  try {
    const response = await axios.post("auth/signin", { user });
    if (response?.data) return true;
    return false;
  } catch (e) {
    console.log('Error sign in', e);
    throw new Error('Sign in failed');
  }
};

export const logout = async () => {
  try {
    await axios.post('api/logout');
    localStorage.removeItem('token');
    window.location.href = '/homepage'; 
  } catch (e) {
    console.log('Error logging out', e);
    throw new Error('Logout failed');
  }
};

export const getCurrentToken = () => {
  return JSON.parse(localStorage.getItem("token"));
};

export const getUsers = async () => {
    try {
      const response = await axios.get("api/users", {
        headers: authHeader() ,
      });
      return response?.data;
    } catch (e) {
      console.log('Error fetching users', e);
      
    }
  };

  export const getUserByUsername = async (username) => {
    if (!username) {
      throw new Error('Username is required');
    }
    try {
      const response = await axios.get(`http://localhost:3000/user/?username=${username}`);
      if (!response || !response.data) {
        throw new Error('Invalid response from server');
      }
      return response?.data;
    } catch (e) {
      console.log(`Error fetching user with username ${username}`, e);
      
    }
  };

  export const getUserById = async (id) => {
    try {
      const response = await axios.get(`http://localhost:3000/user/?user_id=${id}`);
      return response?.data;
    } catch (e) {
      console.log(`Error fetching user with id ${id}`, e);
      
    }
  };

export const updateUser = async (currentUser, username, update_info) => {
  if (currentUser.username === username) {
    try {
      await axios.put(`api/updateUser:${username}`, update_info, {
        headers: authHeader()
      });
          } catch (e) {
      console.log('Error updating user', e);
    }
  } 
    console.log("You are not authorized to update this user");
};

export const getRanking = async () => {
  try {
    const response = await axios.get(`http://localhost:3000/ranking`); //fake API
    if (!response || !response.data) {
      throw new Error('Invalid response from server');
    }
    return response.data;
  } catch (e) {
    console.log(`Error fetching ranking list`, e);
    
  }
};

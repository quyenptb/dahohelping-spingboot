import axios from 'axios';

export const getCards = async () => { //get lastest initial cards
    try {
      const response = await axios.get('http://localhost:3000/card');
      return response?.data;
    } catch (error) {
      console.error('Error fetching users', error);
      throw error;
    }
  };

  export const getCardByTitle = async (title) => { //for SeachServices
    if (!title) {
      throw new Error('Title is required');
    }
    try {
      const response = await axios.get(`/api/cards/${title}`);
      if (!response || !response.data) {
        throw new Error('Invalid response from server');
  }
} catch (error) {
    console.error(`Error fetching cards with Title`, error);
      throw error;
}
}

  export const getCardsByUsername = async (username) => { //for UserInfo page
    if (!username) {
      throw new Error('Username is required');
    }
    try {
      const response = await axios.get(`/api/cards/${username}`);
      if (!response || !response.data) {
        throw new Error('Invalid response from server');
      }
      return response.data;
    } catch (error) {
      console.error(`Error fetching user with username ${username}`, error);
      throw error;
    }
  };

  export const filterCards = async (dahohelping, uni_id, fal_id, maj_id, sub_id) => {
    try {
        let response;
        if (dahohelping && dahohelping !== "DahoHelping") {
            response = await axios.get(`/api/cards/dahohelping/${dahohelping}`);
        } else {
            let url = '/api/cards';
            if (uni_id) {
                url += `/${uni_id}`;
                if (fal_id) {
                    url += `/${fal_id}`;
                    if (maj_id) {
                        url += `/${maj_id}`;
                        if (sub_id) {
                            url += `/${sub_id}`;
                        }
                    }
                }
            }
            response = await axios.get(url);
        }
        return response?.data;
    } catch (error) {
        console.error("Error fetching cards", error);
        throw error;
    }
}
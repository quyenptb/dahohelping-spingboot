export default function authHeader() {
    const token = JSON.parse(localStorage.getItem("token"));
    if (token) {
      // return { Authorization: 'Bearer ' + token };
      return { "x-auth-token": token }; //depend on your backend
    } else {
      return {};
    }
  }
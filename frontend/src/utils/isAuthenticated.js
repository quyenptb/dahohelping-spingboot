export const isAuthenticated =  async () => {
    const token = localStorage.getItem('token');
    if (token) {
      return true;
    }
    return false;
  }
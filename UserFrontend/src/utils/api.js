import axios from 'axios';

const API_URL = '/api';

export const _signUp = async (userData) => {
  try {
    const response = await axios.post(`${API_URL}/v1/auth/signup`, userData);
    return response.data;
  } catch (error) {
    throw new Error(error.response.data.message || 'An error occurred during sign up');
  }
};

export const _signIn = async (credentials) => {
  try {
    const response = await axios.post(`${API_URL}/v1/auth/login`, credentials);
    return response.data;
  } catch (error) {
    throw new Error(error.response.data.message || 'An error occurred during sign in');
  }
};

export const _updateProfile = async (userData, accessToken) => {
  try {
    const response = await axios.put(`${API_URL}/v1/user/update`, userData, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data;
  } catch (error) {
    throw new Error(error.response.data.message || 'An error occurred while updating profile');
  }
};


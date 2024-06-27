// axiosConfig.js

import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080", // Replace with your backend API base URL
  timeout: 10000, // 10 seconds timeout
  headers: {
    "Content-Type": "application/json",
    // Add any headers you need for all requests
  },
});

export default apiClient;

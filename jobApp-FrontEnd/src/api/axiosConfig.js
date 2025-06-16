import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
  headers: {
    "Content-Type": "application/json", // ✅ Set default Content-Type
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token"); // or from Redux store
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;

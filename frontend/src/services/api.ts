import axios from "axios";

const API_URL = "/api/ProductoServlet";

export const obtenerProductos = async () => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const crearProducto = async (producto: any) => {
  const response = await axios.post(API_URL, producto);
  return response.data;
};

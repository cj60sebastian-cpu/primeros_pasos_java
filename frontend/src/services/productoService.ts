import axios from "axios";
import type { Producto } from "../Types/Producto";

// Usa la ruta relativa del proxy de Vite
const API_URL = "/api/ProductoServlet";

export const obtenerProductos = async (): Promise<Producto[]> => {
  const response = await axios.get(API_URL);
  return response.data;
};

export const crearProducto = async (producto: Producto): Promise<Producto> => {
  const response = await axios.post(API_URL, producto);
  return response.data;
};

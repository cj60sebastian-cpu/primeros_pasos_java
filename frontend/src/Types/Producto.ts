export interface Producto {
  id?: number; // Optional por el signo de interrogación, ya que puede no estar presente en algunos casos
  nombre: string; // Obligatorio
  precio: number; // Obligatorio
  stock: number; // Obligatorio
  categoria: string; // Obligatorio
}

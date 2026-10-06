package jpa_Iquise_Huamani;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import model.Categoria;
import model.Producto;

public class demo01 {

    public static void main(String[] args) {

        EntityManagerFactory fabrica = null;
        EntityManager manager = null;
        EntityTransaction transaccion = null;

        try {
            // Crear EntityManagerFactory y EntityManager
            fabrica = Persistence.createEntityManagerFactory(
                    "mysqlconex"
            );

            manager = fabrica.createEntityManager();
            transaccion = manager.getTransaction();

            // Iniciar transacción
            transaccion.begin();

            // 1. Recuperar una categoría existente
            Categoria categoria = manager.find(
                    Categoria.class,
                    1
            );

            if (categoria == null) {
                throw new RuntimeException(
                        "No existe la categoría con código 1"
                );
            }

            System.out.println(
                    "Categoría encontrada: "
                    + categoria.getNom_categoria()
            );

            // 2. Crear un producto con datos válidos
            Producto producto = new Producto();

            producto.setNom_producto("Monitor LED");
            producto.setPrecio(new BigDecimal("650.00"));
            producto.setStock(10);
            producto.setCategoria(categoria);

            // Registrar el producto
            manager.persist(producto);

            // Confirmar el registro
            transaccion.commit();

            System.out.println("--------------------------------");
            System.out.println("PRODUCTO REGISTRADO CORRECTAMENTE");
            System.out.println("Código: "
                    + producto.getId_producto());

            System.out.println("Nombre: "
                    + producto.getNom_producto());

            System.out.println("Categoría: "
                    + producto.getCategoria()
                              .getNom_categoria());

            System.out.println("Precio: S/ "
                    + producto.getPrecio());

            System.out.println("Stock: "
                    + producto.getStock());

            System.out.println("--------------------------------");

        } catch (Exception e) {

            if (transaccion != null
                    && transaccion.isActive()) {

                transaccion.rollback();
                System.err.println(
                        "Se realizó rollback."
                );
            }

            System.err.println(
                    "No se pudo registrar el producto."
            );

            System.err.println(
                    "Error: " + e.getMessage()
            );

            e.printStackTrace();

        } finally {

            if (manager != null && manager.isOpen()) {
                manager.close();
                System.out.println(
                        "EntityManager cerrado."
                );
            }

            if (fabrica != null && fabrica.isOpen()) {
                fabrica.close();
                System.out.println(
                        "EntityManagerFactory cerrado."
                );
            }
        }
    }
}
package jpa_Iquise_Huamani;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import model.Categoria;
import model.MovimientoInventario;
import model.Producto;

public class Pregunta02_IquiseHuamani {

    public static void main(String[] args) {

        EntityManagerFactory fabrica = null;
        EntityManager manager = null;
        EntityTransaction transaccion = null;

        try {
            // Crear los objetos de persistencia
            fabrica = Persistence.createEntityManagerFactory(
                    "mysqlconex"
            );

            manager = fabrica.createEntityManager();
            transaccion = manager.getTransaction();

            // Todas las modificaciones empiezan aquí
            transaccion.begin();


            // 1. RECUPERAR UNA CATEGORÍA EXISTENTE
   

            int idCategoria = 1;

            Categoria categoria = manager.find(
                    Categoria.class,
                    idCategoria
            );

            if (categoria == null) {
                throw new RuntimeException(
                        "No existe la categoría con código "
                        + idCategoria
                );
            }

            System.out.println(
                    "Categoría encontrada: "
                    + categoria.getNom_categoria()
            );

          
            // 2. REGISTRAR UN PRODUCTO CON PERSIST()
          

            Producto nuevoProducto = new Producto();

            nuevoProducto.setNom_producto("Monitor LED");
            nuevoProducto.setPrecio(
                    new BigDecimal("800.00")
            );
            nuevoProducto.setStock(10);
            nuevoProducto.setCategoria(categoria);

            manager.persist(nuevoProducto);

            /*
             * Ejecuta el INSERT para obtener el identificador
             * generado por AUTO_INCREMENT.
             */
            manager.flush();

            int idProductoGenerado =
                    nuevoProducto.getId_producto();

            System.out.println();
            System.out.println("PRODUCTO REGISTRADO");
            System.out.println(
                    "Código: " + idProductoGenerado
            );
            System.out.println(
                    "Nombre: "
                    + nuevoProducto.getNom_producto()
            );
            System.out.println(
                    "Precio: S/ "
                    + nuevoProducto.getPrecio()
            );
            System.out.println(
                    "Stock inicial: "
                    + nuevoProducto.getStock()
            );
            System.out.println(
                    "Categoría: "
                    + categoria.getNom_categoria()
            );

        
            // 3. RECUPERAR EL PRODUCTO CON FIND()
     

            /*
             * clear() retira las entidades del contexto actual
             * para que find() vuelva a recuperar el producto.
             */
            manager.clear();

            Producto productoEncontrado = manager.find(
                    Producto.class,
                    idProductoGenerado
            );

            if (productoEncontrado == null) {
                throw new RuntimeException(
                        "No se encontró el producto registrado."
                );
            }

            System.out.println();
            System.out.println(
                    "Producto recuperado con find(): "
                    + productoEncontrado.getNom_producto()
            );

 
            // 4. REGISTRAR MOVIMIENTO DE ENTRADA
    

            int cantidadEntrada = 15;

            MovimientoInventario movimiento =
                    new MovimientoInventario();

            movimiento.setProducto(productoEncontrado);
            movimiento.setTipo_movimiento("ENTRADA");
            movimiento.setCantidad(cantidadEntrada);
            movimiento.setFecha_movimiento(
                    LocalDateTime.now()
            );

            manager.persist(movimiento);

            System.out.println();
            System.out.println("MOVIMIENTO REGISTRADO");
            System.out.println("Tipo: ENTRADA");
            System.out.println(
                    "Cantidad: " + cantidadEntrada
            );

         
            // 5. ACTUALIZAR STOCK CON MERGE()
 

            int stockAnterior =
                    productoEncontrado.getStock();

            int nuevoStock =
                    stockAnterior + cantidadEntrada;

            productoEncontrado.setStock(nuevoStock);

            Producto productoActualizado =
                    manager.merge(productoEncontrado);

            System.out.println();
            System.out.println("STOCK ACTUALIZADO");
            System.out.println(
                    "Stock anterior: " + stockAnterior
            );
            System.out.println(
                    "Cantidad ingresada: " + cantidadEntrada
            );
            System.out.println(
                    "Stock actual: "
                    + productoActualizado.getStock()
            );

            // Confirmar producto, movimiento y actualización
            transaccion.commit();

            System.out.println();
            System.out.println(
                    "Transacción confirmada correctamente."
            );

           
            // 6 Y 7. CONSULTA JPQL PARAMETRIZADA Y ORDENADA
          

            int stockMinimo = 10;

            String jpql =
                    "SELECT p FROM Producto p "
                    + "WHERE p.categoria.id_categoria = :idCategoria "
                    + "AND p.stock >= :stockMinimo "
                    + "ORDER BY p.nom_producto ASC";

            TypedQuery<Producto> consulta =
                    manager.createQuery(
                            jpql,
                            Producto.class
                    );

            consulta.setParameter(
                    "idCategoria",
                    idCategoria
            );

            consulta.setParameter(
                    "stockMinimo",
                    stockMinimo
            );

            List<Producto> productos =
                    consulta.getResultList();

            // 8. MOSTRAR PRODUCTOS EN CONSOLA
       

            System.out.println();
            System.out.println(
                    "========================================"
            );
            System.out.println(
                    "RESULTADO DE LA CONSULTA JPQL"
            );
            System.out.println(
                    "Categoría: " + categoria.getNom_categoria()
            );
            System.out.println(
                    "Stock mínimo: " + stockMinimo
            );
            System.out.println(
                    "========================================"
            );

            if (productos.isEmpty()) {

                System.out.println(
                        "No se encontraron productos."
                );

            } else {

                for (Producto producto : productos) {

                    System.out.println(
                            "Código: "
                            + producto.getId_producto()
                    );

                    System.out.println(
                            "Nombre: "
                            + producto.getNom_producto()
                    );

                    System.out.println(
                            "Categoría: "
                            + producto.getCategoria()
                                      .getNom_categoria()
                    );

                    System.out.println(
                            "Precio: S/ "
                            + producto.getPrecio()
                    );

                    System.out.println(
                            "Stock actual: "
                            + producto.getStock()
                    );

                    System.out.println(
                            "----------------------------------------"
                    );
                }
            }

            System.out.println();
            System.out.println(
                    "PREGUNTA 02 EJECUTADA CORRECTAMENTE"
            );

        } catch (Exception e) {

       
            // ROLLBACK EN CASO DE ERROR
      

            if (transaccion != null
                    && transaccion.isActive()) {

                transaccion.rollback();

                System.err.println(
                        "Se realizó rollback de la transacción."
                );
            }

            System.err.println(
                    "ERROR AL EJECUTAR LA PREGUNTA 02"
            );

            System.err.println(
                    "Detalle: " + e.getMessage()
            );

            e.printStackTrace();

        } finally {

     
            // 9. CERRAR LOS RECURSOS
          
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

            System.out.println("Aplicación finalizada.");
        }
    }
}
package maven_iquisehuamani;

import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Categoria;
import model.Producto;

public class Pregunta03_IquiseHuamani {

    public static void main(String[] args) {

        // Utilizamos la entidad Categoria existente
        Categoria categoria = new Categoria();

        categoria.setId_categoria(1);
        categoria.setNom_categoria("Tecnologia");
        categoria.setEstado(1);

        // Utilizamos la entidad Producto existente
        Producto producto = new Producto();

        producto.setId_producto(7);
        producto.setNom_producto("Auriculares Bluetooth");
        producto.setPrecio(new BigDecimal("120.00"));
        producto.setStock(40);
        producto.setCategoria(categoria);

        // Dependencia Gson
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        // Convertir la entidad Producto a JSON
        String productoJson = gson.toJson(producto);

        System.out.println(
                "========================================"
        );

        System.out.println(
                "PRUEBA FUNCIONAL DE LA DEPENDENCIA GSON"
        );

        System.out.println(
                "========================================"
        );

        System.out.println(
                "Entidad Producto convertida a JSON:"
        );

        System.out.println(productoJson);

        System.out.println(
                "========================================"
        );

        System.out.println(
                "DEPENDENCIA GSON UTILIZADA CORRECTAMENTE"
        );

        System.out.println(
                "========================================"
        );
    }
}
package mainactivity.franzfonseca.com.firebasepeluchitos;

/**
 * Created by FRANZ on 30/05/2016.
 */
public class Peluches {
    private String id, nombre, cantidad, valor;

    public Peluches(String id, String nombre, String cantidad, String valor){
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.valor = valor;

    }

    public String getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }

    public String getCantidad() {
        return cantidad;
    }
    public String getValor() {
        return valor;
    }

}

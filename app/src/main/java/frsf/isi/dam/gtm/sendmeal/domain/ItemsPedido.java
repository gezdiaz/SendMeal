package frsf.isi.dam.gtm.sendmeal.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ITEMS_PEDIDO")
public class ItemsPedido {
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID_ITEMS_PEDIDO")
    private int id;

    private int cantidad;
    private double precio;
    private Pedido pedido;
    private Plato plato;

    public ItemsPedido() {
        precio = 0.0;
        cantidad = 0;
    }

    public ItemsPedido(int id, Pedido pedido, Plato plato, int cantidad, double precio) {
        this.id = id;
        this.pedido = pedido;
        this.plato = plato;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public void calcularPrecio(){
        if(plato != null){
            precio = plato.getPrecioOferta()*cantidad;
        }else {
            precio = 0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        calcularPrecio();
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ItemsPedido{" +
                "id=" + id +
                ", pedido=" + pedido +
                ", plato=" + plato +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemsPedido that = (ItemsPedido) o;

        if (id != that.id) return false;
        if (cantidad != that.cantidad) return false;
        if (Double.compare(that.precio, precio) != 0) return false;
        if (pedido != null ? !pedido.equals(that.pedido) : that.pedido != null) return false;
        return plato != null ? plato.equals(that.plato) : that.plato == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (pedido != null ? pedido.hashCode() : 0);
        result = 31 * result + (plato != null ? plato.hashCode() : 0);
        result = 31 * result + cantidad;
        temp = Double.doubleToLongBits(precio);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

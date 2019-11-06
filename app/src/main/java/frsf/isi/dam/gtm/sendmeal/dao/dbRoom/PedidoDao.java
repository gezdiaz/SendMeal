package frsf.isi.dam.gtm.sendmeal.dao.dbRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import frsf.isi.dam.gtm.sendmeal.domain.Pedido;

@Dao
public interface PedidoDao {

    @Transaction
    @Query("SELECT * FROM PEDIDO")
    List<Pedido.PedidoConItems> getPedidoConItems();

    @Transaction
    @Query("SELECT * FROM PEDIDO WHERE ID_PEDIDO = :idPedido")
    Pedido.PedidoConItems getPedidoConItemsByIdPedido(int idPedido);

    @Query("SELECT * FROM PEDIDO")
    List<Pedido> getAllPedidos();

    @Insert
    void insertPedido(Pedido pedido);

    @Insert
    void insertAllPedidos(List<Pedido> pedidos);

    @Delete
    void deletePedido(Pedido pedido);

    @Update
    void updatePedido(Pedido pedido);

    @Query("SELECT * FROM PEDIDO WHERE ID_PEDIDO=:id")
    Pedido getPedidoById(final int id);
}

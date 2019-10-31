package frsf.isi.dam.gtm.sendmeal.dao.dbRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import frsf.isi.dam.gtm.sendmeal.domain.ItemsPedido;

@Dao
public interface ItemPedidoDao {
    @Query("SELECT * FROM ITEMS_PEDIDO")
    List<ItemsPedido> getAllItemsPedido();

    @Query("SELECT * FROM ITEMS_PEDIDO WHERE ID_ITEMS_PEDIDO=:id")
    ItemsPedido getItemPedidoById(final int id);

    @Insert
    void insertItemPedido(ItemsPedido itemsPedido);

    @Insert
    void insertAllItemsPedido(List<ItemsPedido> itemsPedidos);

    @Delete
    void deleteItemPedido(ItemsPedido itemsPedido);

    @Update
    void updateItemPedido(ItemsPedido itemsPedido);
}

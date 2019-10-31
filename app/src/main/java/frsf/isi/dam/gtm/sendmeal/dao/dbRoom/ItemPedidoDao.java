package frsf.isi.dam.gtm.sendmeal.dao.dbRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import frsf.isi.dam.gtm.sendmeal.domain.ItemPedido;

@Dao
public interface ItemPedidoDao {
    @Query("SELECT * FROM ITEM_PEDIDO")
    List<ItemPedido> getAllItemsPedido();

    @Query("SELECT * FROM ITEM_PEDIDO WHERE ID_ITEM_PEDIDO=:id")
    ItemPedido getItemPedidoById(final int id);

    @Insert
    void insertItemPedido(ItemPedido itemPedido);

    @Insert
    void insertAllItemsPedido(List<ItemPedido> itemsPedidos);

    @Delete
    void deleteItemPedido(ItemPedido itemPedido);

    @Update
    void updateItemPedido(ItemPedido itemPedido);
}

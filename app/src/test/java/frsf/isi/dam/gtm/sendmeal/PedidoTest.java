package frsf.isi.dam.gtm.sendmeal;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import frsf.isi.dam.gtm.sendmeal.domain.ItemPedido;
import frsf.isi.dam.gtm.sendmeal.domain.Pedido;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PedidoTest {

    @Mock
    ItemPedido itemPedidoMock1;
    @Mock
    ItemPedido itemPedidoMock2;
    @Mock
    ItemPedido itemPedidoMock3;
    @Mock
    ItemPedido itemPedidoMock4;

    @Test
    public void test_calcularPrecio(){
        List<ItemPedido> listItemsPedidoMock = new ArrayList<>();
        Pedido p = new Pedido();

        listItemsPedidoMock.add(itemPedidoMock1);
        when(itemPedidoMock1.getPrecioItem()).thenReturn(15.0);
        p.setItemsPedido(listItemsPedidoMock);
        assertEquals(15.0, p.getPrecioTotal(), 0.0);

        listItemsPedidoMock.add(itemPedidoMock2);
        when(itemPedidoMock2.getPrecioItem()).thenReturn(20.0);
        p.setItemsPedido(listItemsPedidoMock);
        assertEquals(35.0, p.getPrecioTotal(), 0.0);

        listItemsPedidoMock.add(itemPedidoMock3);
        when(itemPedidoMock3.getPrecioItem()).thenReturn(11.37);
        p.setItemsPedido(listItemsPedidoMock);
        assertEquals(46.37, p.getPrecioTotal(), 0.0);

        listItemsPedidoMock.add(itemPedidoMock4);
        when(itemPedidoMock4.getPrecioItem()).thenReturn(33.5);
        p.setItemsPedido(listItemsPedidoMock);
        assertEquals(79.87, p.getPrecioTotal(), 0.0);
    }
}

package frsf.isi.dam.gtm.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.DBClient;
import frsf.isi.dam.gtm.sendmeal.domain.ItemPedido;
import frsf.isi.dam.gtm.sendmeal.domain.Pedido;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);

        NotificationReceiver br = new NotificationReceiver();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(NotificationReceiver.OFFERNOTIFICATION);
        getApplication().getApplicationContext().registerReceiver(br,filtro);

//        List<Pedido> pedidos = DBClient.getInstance(this).getRoomDB().pedidoDao().getAllPedidos();
//        System.out.println("Pedidos en SQLite: "+ pedidos.toString() );
//        List<ItemPedido> items = DBClient.getInstance(this).getRoomDB().itemPedidoDao().getAllItemsPedido();
//        System.out.println("Items pedido en SQLite:" + items.toString());
//        List<Pedido.PedidoConItems> pedidosConItems = DBClient.getInstance(this).getRoomDB().pedidoDao().getPedidoConItems();
//        System.out.println("Pedidos con Items en SQLite:" + pedidosConItems.toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.registerOptionItem:
                Intent i1 = new Intent(this, RegisterActivity.class);
                startActivity(i1);
                break;
            case R.id.createItemOptionItem:
                Intent i2 = new Intent(this, CreateActivity.class);
                startActivity(i2);
                break;
            case R.id.itemListOptionItem:
                Intent i3 = new Intent(this, DishViewActivity.class);
                startActivity(i3);
                break;
            case R.id.newOrderOptionItem:
                Intent i4 = new Intent(this, PedidoCreateActivity.class);
                startActivity(i4);
                break;
            case R.id.viewOrders:
                Intent i5 = new Intent(this, MapActivity.class);
                i5.setAction("showOrders");
                startActivity(i5);
                break;
            default:
                Toast.makeText(this, "....", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

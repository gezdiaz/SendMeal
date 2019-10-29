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
            default:
                Toast.makeText(this, "....", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

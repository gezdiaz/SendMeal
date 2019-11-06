package frsf.isi.dam.gtm.sendmeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;
import frsf.isi.dam.gtm.sendmeal.domain.EstadoPedido;
import frsf.isi.dam.gtm.sendmeal.domain.Pedido;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final int GET_LOCATION = 1;
    public static final int SHOW_ORDERS = 2;

    private GoogleMap map;
    private Marker address;
    private int actionReceived;
    private Spinner statesSpinner;
    private ArrayAdapter<EstadoPedido> estadoPedidoArrayAdapter;
    private EstadoPedido[] listaEstados;
    private List<Marker> markerList;
    private List<Pedido> pedidos;
    private ProgressDialog progressDialog;

    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case RetrofitRepository.GETALL_PEDIDOS:{
                    pedidos = (List<Pedido>) msg.obj;
                    mostrarPedidos();
                    if(progressDialog.isShowing()){
                        progressDialog.cancel();
                    }
                    break;
                }
                case RetrofitRepository.ERROR_GETALL_PEDIDOS:{
                    Toast.makeText(MapActivity.this, R.string.databaseGetAllPedidosError, Toast.LENGTH_LONG).show();
                    finish();
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        statesSpinner = findViewById(R.id.stateSelectorSpinner);
        if(getIntent().getAction().equals("getLocation")){
            actionReceived = GET_LOCATION;
            statesSpinner.setVisibility(View.GONE);
        }else{
            if(getIntent().getAction().equals("showOrders")){
                RetrofitRepository.getInstance().getPedidos(handler);
                progressDialog = ProgressDialog.show(this,getString(R.string.pleaseWait),"Cargando pedidos de la base de datos");
                progressDialog.setCancelable(false);
                actionReceived = SHOW_ORDERS;
                listaEstados = EstadoPedido.getSpinnerList();
                estadoPedidoArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaEstados);
                estadoPedidoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                statesSpinner.setAdapter(estadoPedidoArrayAdapter);
                statesSpinner.setVisibility(View.VISIBLE);
                statesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        EstadoPedido estadoSeleccionado = listaEstados[i];

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }
        //Esto se debería hacer antes de pedir los pedidos de la base de datos.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.setLocationMapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        updateMap();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void updateMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},9999);
            return;
        }
        map.setMyLocationEnabled(true);
        if(actionReceived == GET_LOCATION) {
            map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    if (address != null) {
                        address.setPosition(latLng);
                    } else {
                        address = map.addMarker(new MarkerOptions().position(latLng)
                                .draggable(true)
                                .title("Enviar pedido"));
                    }
                }
            });
//            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                @Override
//                public boolean onMarkerClick(Marker marker) {
//                    Intent result = new Intent(MapActivity.this, PedidoCreateActivity.class);
//                    Double lat = marker.getPosition().latitude, lon = marker.getPosition().longitude;
//                    result.putExtra("longitud", lon);
//                    result.putExtra("latitud", lat);
//                    MapActivity.this.setResult(Activity.RESULT_OK, result);
//                    MapActivity.this.finish();
//                    return true;
//                }
//            });
            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent result = new Intent(MapActivity.this, PedidoCreateActivity.class);
                    Double lat = marker.getPosition().latitude, lon = marker.getPosition().longitude;
                    result.putExtra("longitud", lon);
                    result.putExtra("latitud", lat);
                    MapActivity.this.setResult(Activity.RESULT_OK, result);
                    MapActivity.this.finish();
                }
            });
        }else{
            if(actionReceived == SHOW_ORDERS) {
                map.setInfoWindowAdapter(new PedidoInfoWindowAdapter(getLayoutInflater()));
            }
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null){
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
        }else{
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-31.629484, -60.701036), 13));
        }
    }

    private void mostrarPedidos() {
        for(Pedido p: pedidos){
            map.addMarker(new MarkerOptions().position(new LatLng(p.getLatitud(),p.getLongitud()))
                                             .title(getString(R.string.orderMarkerTitle)+" "+p.getId())
                                             .snippet(p.getEstado().name()+"-"+"$ "+p.getPrecioTotal())
                                             .icon(BitmapDescriptorFactory.defaultMarker(getMarkerColor(p.getEstado()))));
        }
    }

    private float getMarkerColor(EstadoPedido estado) {
        float color = 0;
        switch (estado){
            case ENVIADO: color = BitmapDescriptorFactory.HUE_AZURE; break;
            case ACEPTADO: color = BitmapDescriptorFactory.HUE_BLUE; break;
            case RECHAZADO: color = BitmapDescriptorFactory.HUE_RED; break;
            case EN_PREPARACION: color = BitmapDescriptorFactory.HUE_ORANGE; break;
            case EN_ENVIO: color = BitmapDescriptorFactory.HUE_YELLOW; break;
            case ENTREGADO: color = BitmapDescriptorFactory.HUE_GREEN; break;
        }
        return color;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 9999: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateMap();
                }
            }
        }
    }

}

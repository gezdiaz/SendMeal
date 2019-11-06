package frsf.isi.dam.gtm.sendmeal;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.zip.Inflater;

public class PedidoInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater inflater = null;
    private View window = null;

    PedidoInfoWindowAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if(window == null){
            window = inflater.inflate(R.layout.pedido_info_layout, null);
        }
        TextView title, line1, line2;
        title = window.findViewById(R.id.pedidoInfoTitle);
        line1 = window.findViewById(R.id.pedidoInfoLine1);
        line2 = window.findViewById(R.id.pedidoInfoLine2);

        title.setText(marker.getTitle());
        String snippet = marker.getSnippet();
        if(snippet.contains("-")){
            String[] parts = snippet.split("-");
            line1.setText(parts[0]);
            line2.setText(parts[1]);
        }else{
            line1.setText("error");
            line2.setText("error");
        }
        return window;
    }
}

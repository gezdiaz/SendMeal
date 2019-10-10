package frsf.isi.dam.gtm.sendmeal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String OFFERNOTIFICATION="frsf.isi.dam.app04.OFFERNOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast t = new Toast(context);
//        t.setText("Llegó el broadcast");
//        t.setDuration(Toast.LENGTH_LONG);
//        t.show();
        //TODO enviar notificación
        Log.println(Log.ASSERT, "tag", "Se recibió el mensaje");
    }
}

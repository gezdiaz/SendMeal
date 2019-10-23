package frsf.isi.dam.gtm.sendmeal;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.VibrationEffect;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

import frsf.isi.dam.gtm.sendmeal.domain.Plato;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String OFFERNOTIFICATION="frsf.isi.dam.app04.OFFERNOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {
       Log.i("info","Se recibió el mensaje");
            Plato plato = (Plato)intent.getExtras().get("plato");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,context.getResources().getString(R.string.CHANNEL_ID));
            builder.setSmallIcon(R.drawable.ic_fastfood);
            builder.setContentTitle(context.getResources().getString(R.string.notification_title));
            String text = context.getResources().getString(R.string.notification_content);

            Intent destino = new Intent(context, ShowDishActivity.class);
            destino.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            destino.putExtra("plato",plato);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,destino,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            builder.setAutoCancel(true);
            builder.setContentText(text);
            Notification notif = builder.build();
            NotificationManagerCompat.from(context).notify(4,notif);

    }
}

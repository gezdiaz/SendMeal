package frsf.isi.dam.gtm.sendmeal;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import frsf.isi.dam.gtm.sendmeal.dao.RetrofitRepository;
import frsf.isi.dam.gtm.sendmeal.domain.Pedido;

public class PushNotificationService extends FirebaseMessagingService {

    public PushNotificationService() {
    }

    private static FirebaseFunctions mFunctions;


    public static Task<String> sendPushNotification(String idPedido, String estadoAnterior, String nuevoEstado) {
        // Create the arguments to the callable function.
        Map<String, Object> data = new HashMap<>();
        data.put("idPedido", idPedido);
        data.put("estadoAnterior", estadoAnterior);
        data.put("nuevoEstado", nuevoEstado);

        if(mFunctions == null){
            mFunctions = FirebaseFunctions.getInstance();
        }

        return mFunctions
                .getHttpsCallable("sendNotification")
                .call(data)
                .continueWith(new Continuation<HttpsCallableResult, String>() {
                    @Override
                    public String then(@NonNull Task<HttpsCallableResult> task) throws Exception {
                        // This continuation runs on either success or failure, but if the task
                        // has failed then getResult() will throw an Exception which will be
                        // propagated down.
                        String result = (String) task.getResult().getData();
                        return result;
                    }
                });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("token", "Se crea el service");
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("token","Nuevo token: "+s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d("token", "onMessageReceiver()");
        if(remoteMessage.getData().size() >0){
            String idPedido = remoteMessage.getData().get("idPedido");
            String estadoAnterior = remoteMessage.getData().get("estadoAnterior");
            String nuevoEstado = remoteMessage.getData().get("nuevoEstado");
            if(idPedido != null && estadoAnterior != null && nuevoEstado != null){
                int pedidoId = Integer.parseInt(idPedido);
                sendNotification(pedidoId, estadoAnterior, nuevoEstado);
            }
        }
    }

    private void sendNotification(int idPedido, String estadoAnterior, String nuevoEstado) {
        //TODO ir a la actividad ver pedido.
        Intent intent = new Intent(this, ShowNewOrderStateActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("idPedido", idPedido);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, getString(R.string.CHANNEL_ID))
                        .setSmallIcon(R.drawable.ic_fastfood)
                        .setContentTitle("El pedido "+idPedido+" cambió de estado.")
                        .setContentText("Estado anterior: "+estadoAnterior+" - Nuevo estado: "+nuevoEstado)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 , notificationBuilder.build());
    }

}

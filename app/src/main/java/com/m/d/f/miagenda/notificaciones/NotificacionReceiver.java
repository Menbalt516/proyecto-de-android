package com.m.d.f.miagenda.notificaciones;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.presentacion.EventosListActivity;

public class NotificacionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int idEvento = intent.getIntExtra("eventoId", 0);
        String titulo = intent.getStringExtra("titulo");

        String channelId = "eventos_channel";

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Recordatorios de Eventos",
                    NotificationManager.IMPORTANCE_HIGH
            );
            manager.createNotificationChannel(channel);
        }

        Intent abrirAppIntent = new Intent(context, EventosListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                idEvento,
                abrirAppIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Recordatorio")
                .setContentText(titulo)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        manager.notify(idEvento, builder.build());
    }
}

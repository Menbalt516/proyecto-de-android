package com.m.d.f.miagenda.notificaciones;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.presentacion.EventosListActivity;

public class RecordatorioReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "canal_eventos";

    @Override
    public void onReceive(Context context, Intent intent) {

        int eventoId = intent.getIntExtra("eventoId", -1);
        String titulo = intent.getStringExtra("titulo");

        // Crear canal de notificación (Android 8+)
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm != null && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Recordatorios de Eventos",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notificaciones de eventos de la agenda");
            channel.enableLights(true);
            channel.enableVibration(true);
            nm.createNotificationChannel(channel);
        }

        // Sonido por defecto
        Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Intent al tocar la notificación
        Intent i = new Intent(context, EventosListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                eventoId,
                i,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Construir y mostrar notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Recordatorio")
                .setContentText(titulo)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSound(sonido)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (nm != null) {
            nm.notify(eventoId, builder.build());
        }
    }
}

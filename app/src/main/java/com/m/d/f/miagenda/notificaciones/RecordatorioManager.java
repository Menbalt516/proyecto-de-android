package com.m.d.f.miagenda.notificaciones;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class RecordatorioManager {

    /**
     * Programa una notificación para un evento
     * @param context Contexto
     * @param tiempoMillis Fecha y hora en milisegundos del evento
     * @param eventoId ID del evento
     * @param titulo Título del evento
     */
    public static void programarRecordatorio(Context context, long tiempoMillis, int eventoId, String titulo) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, RecordatorioReceiver.class);
        intent.putExtra("eventoId", eventoId);
        intent.putExtra("titulo", titulo);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                eventoId, // requestCode único por evento
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Para que suene 15 min antes
        long tiempoAlarma = tiempoMillis - 15 * 60 * 1000;

        // Programar la alarma exacta
        if (alarmManager != null) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    tiempoAlarma,
                    pendingIntent
            );
        }
    }
}

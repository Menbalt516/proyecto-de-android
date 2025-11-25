package com.m.d.f.miagenda.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import com.m.d.f.miagenda.R;

public class CalendarioActivity extends AppCompatActivity {

    private CalendarView calendarView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_calendario);

            calendarView = findViewById(R.id.calendarView);

            calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;

                Intent i = new Intent(this, EventosListActivity.class);
                i.putExtra("fecha", fecha);
                startActivity(i);
            });
        }
    }

package com.m.d.f.miagenda.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.m.d.f.miagenda.R;

public class MetaSelectorActivity extends AppCompatActivity {
    private Button btnMeta, btnSubMeta;
    private int eventoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_selector);

        btnMeta = findViewById(R.id.btnSeleccionarMeta);
        btnSubMeta = findViewById(R.id.btnSeleccionarSubMeta);

        // Obtener ID del evento a asociar
        eventoId = getIntent().getIntExtra("eventoId", -1);

        // Asociar con Meta principal
        btnMeta.setOnClickListener(v -> {
            Intent i = new Intent(this, MetasListActivity.class);
            i.putExtra("eventoId", eventoId);
            startActivity(i);
        });

        // Asociar con Sub-Meta
        btnSubMeta.setOnClickListener(v -> {
            Intent i = new Intent(this, SubMetasActivity.class);
            i.putExtra("eventoId", eventoId);
            startActivity(i);
        });
    }
}

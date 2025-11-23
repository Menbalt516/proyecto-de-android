package com.m.d.f.miagenda.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.Meta;
import com.m.d.f.miagenda.negocio.MetaNegocio;
import com.m.d.f.miagenda.presentacion.adapters.MetaAdapter;

import java.util.ArrayList;
import java.util.List;

public class MetasListActivity extends AppCompatActivity {

    private Button btnNuevaMeta;
    private Button btnSeleccionarmeta;
    private RecyclerView recyclerMetas;
    private MetaAdapter metaAdapter;
    private List<Meta> metaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_metas);

        btnNuevaMeta = findViewById(R.id.btnNuevaMeta);
        btnSeleccionarmeta = findViewById(R.id.btnSeleccionarMeta);
        recyclerMetas = findViewById(R.id.recyclerMetas);

        metaList = new ArrayList<>();

        recyclerMetas.setLayoutManager(new LinearLayoutManager(this));
        metaAdapter = new MetaAdapter(this, metaList);
        recyclerMetas.setAdapter(metaAdapter);

        btnNuevaMeta.setOnClickListener(v -> {
            startActivity(new Intent(this, MetaCrearActivity.class));
        });

        btnSeleccionarmeta.setOnClickListener(v -> {
            startActivity(new Intent(this, MetaDetalleActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarMetas();
    }

    private void cargarMetas() {
        MetaNegocio metaNegocio = new MetaNegocio(this);
        List<Meta> metas = metaNegocio.obtenerMetas();
        metaAdapter.actualizarLista(metas);
    }
}

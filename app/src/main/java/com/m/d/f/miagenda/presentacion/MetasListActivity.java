package com.m.d.f.miagenda.presentacion;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.MetaDAO;
import com.m.d.f.miagenda.modelos.Meta;
import com.m.d.f.miagenda.presentacion.MetaListItem.MetaListItem;
import com.m.d.f.miagenda.presentacion.adapters.MetasSeccionAdapter;


import java.util.ArrayList;
import java.util.List;

public class MetasListActivity extends AppCompatActivity {

    private RecyclerView recyclerMetas;
    private MetaDAO metaDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas_list);

        recyclerMetas = findViewById(R.id.recyclerMetas);
        recyclerMetas.setLayoutManager(new LinearLayoutManager(this));

        metaDAO = new MetaDAO(this);

        List<Meta> metas = metaDAO.listar();
        List<Meta> subMetas = metaDAO.listar();

        List<MetaListItem> listaFinal = new ArrayList<>();
        if (!metas.isEmpty()) {
            listaFinal.add(new MetaListItem(MetaListItem.Tipo.SECCION, "Metas Principales"));
            for (Meta m : metas) {
                listaFinal.add(new MetaListItem(m.getId(), m.getTitulo(), false));
            }
        }
        if (!subMetas.isEmpty()) {
            listaFinal.add(new MetaListItem(MetaListItem.Tipo.SECCION, "Sub-Metas"));
            for (Meta s : subMetas) {
                listaFinal.add(new MetaListItem(s.getId(), s.getTitulo(), true));
            }
        }

        MetasSeccionAdapter adapter = new MetasSeccionAdapter(listaFinal, item -> {
            Intent data = new Intent();
            if (item.esSubMeta) {
                data.putExtra("subMetaId", item.id);
            } else {
                data.putExtra("metaId", item.id);
            }
            data.putExtra("titulo", item.titulo);
            setResult(RESULT_OK, data);
            finish();
        });

        recyclerMetas.setAdapter(adapter);
    }
}

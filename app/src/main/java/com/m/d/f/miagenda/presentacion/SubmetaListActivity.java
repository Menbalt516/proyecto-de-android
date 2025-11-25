package com.m.d.f.miagenda.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.SubMetaDAO;
import com.m.d.f.miagenda.modelos.SubMeta;
import com.m.d.f.miagenda.presentacion.adapters.SubMetaSelectorAdapter;

import java.util.List;

public class SubmetaListActivity extends AppCompatActivity {

    private RecyclerView rvSubMetas;
    private SubMetaDAO subMetaDAO;
    private int metaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submeta_list);

        rvSubMetas = findViewById(R.id.rvSubMetas);
        subMetaDAO = new SubMetaDAO(this);

        metaId = getIntent().getIntExtra("metaId", -1);
        if (metaId == -1) {
            Toast.makeText(this, "Meta no válida", Toast.LENGTH_SHORT).show();
            finish();
        }

        cargarSubMetas();
    }

    private void cargarSubMetas() {
        List<SubMeta> lista = subMetaDAO.listarPorMeta(metaId);

        if (lista.isEmpty()) {
            Toast.makeText(this, "No hay SubMetas para esta Meta", Toast.LENGTH_SHORT).show();
        }

        SubMetaSelectorAdapter adapter =
                new SubMetaSelectorAdapter(lista, this::devolverSubMeta);

        rvSubMetas.setLayoutManager(new LinearLayoutManager(this));
        rvSubMetas.setAdapter(adapter);
    }

    private void devolverSubMeta(SubMeta subMeta) {
        Intent data = new Intent();
        data.putExtra("subMetaId", subMeta.getId());
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}

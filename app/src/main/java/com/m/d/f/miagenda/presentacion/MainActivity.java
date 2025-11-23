package com.m.d.f.miagenda.presentacion;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.Perfil;
import com.m.d.f.miagenda.negocio.PerfilNegocio;

public class MainActivity extends AppCompatActivity {

    private TextView txtBienvenida;
    private PerfilNegocio perfilNegocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        perfilNegocio = new PerfilNegocio(this);
        txtBienvenida = findViewById(R.id.txtBienvenida);

        cargarNombreUsuario();

        Button btnPerfil = findViewById(R.id.btnPerfil);
        Button btnEventos = findViewById(R.id.btnEventos);
        Button btnMetas = findViewById(R.id.btnMetas);
        Button btnCalendario = findViewById(R.id.btnCalendario);

        //Para que se abra al iniciar
        startActivity(new Intent(this, PerfilActivity.class));

        btnPerfil.setOnClickListener(v ->
                startActivity(new Intent(this, PerfilActivity.class)));

        btnEventos.setOnClickListener(v ->
                startActivity(new Intent(this, EventoListActivity.class)));

        btnMetas.setOnClickListener(v ->
                startActivity(new Intent(this, MetasListActivity.class)));

        btnCalendario.setOnClickListener(v ->
                startActivity(new Intent(this, CalendarEventosActivity.class)));

    }

    private void cargarNombreUsuario() {
        Perfil p = perfilNegocio.getPerfil();
        if (p != null) {
            txtBienvenida.setText("Bienvenido, " + p.getNombre());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarNombreUsuario(); // Refresca cuando vuelve desde Perfil
    }
}

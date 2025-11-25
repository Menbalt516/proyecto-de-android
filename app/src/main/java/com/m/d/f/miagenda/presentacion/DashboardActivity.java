package com.m.d.f.miagenda.presentacion;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.m.d.f.miagenda.R;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNav = findViewById(R.id.bottomNav);

        // Seleccionar Inicio por defecto
        bottomNav.setSelectedItemId(R.id.nav_home);

        configurarMenu();
    }

    private void configurarMenu() {
        bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                return true;
            }

            if (id == R.id.nav_metas) {
                startActivity(new Intent(this, MetasActivity.class));
                return true;
            }

            if (id == R.id.nav_eventos) {
                startActivity(new Intent(this, EventosListActivity.class));
                return true;
            }
            if (id == R.id.nav_calendario) {
                startActivity(new Intent(this, CalendarioActivity.class));
                return true;
            }

            if (id == R.id.nav_profile) {
                startActivity(new Intent(this, PerfilActivity.class));
                return true;
            }

            return false;
        });
    }

}

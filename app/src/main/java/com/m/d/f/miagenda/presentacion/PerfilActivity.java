package com.m.d.f.miagenda.presentacion;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.Perfil;
import com.m.d.f.miagenda.negocio.PerfilNegocio;
public class PerfilActivity extends AppCompatActivity {

    private EditText txtNombre, txtApellidos, txtEdad;
    private ImageView imgPerfil;
    private Uri imagenUri;

    PerfilNegocio perfilNegocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        perfilNegocio = new PerfilNegocio(this);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtEdad = findViewById(R.id.txtEdad);
        imgPerfil = findViewById(R.id.imgPerfil);

        Perfil p = perfilNegocio.getPerfil();

        if (p != null) {
            txtNombre.setText(p.getNombre());
            txtApellidos.setText(p.getApellidos());
            txtEdad.setText(String.valueOf(p.getEdad()));
            if (p.getImagenUri() != null)
                imgPerfil.setImageURI(Uri.parse(p.getImagenUri()));
        }

        findViewById(R.id.btnGuardarPerfil).setOnClickListener(v -> guardarPerfil());
    }

    private void guardarPerfil() {
        Perfil p = new Perfil();
        p.setNombre(txtNombre.getText().toString());
        p.setApellidos(txtApellidos.getText().toString());
        p.setEdad(Integer.parseInt(txtEdad.getText().toString()));
        p.setImagenUri(imagenUri != null ? imagenUri.toString() : null);

        perfilNegocio.guardarPerfil(p);
        Toast.makeText(this, "Perfil guardado", Toast.LENGTH_SHORT).show();
    }
}


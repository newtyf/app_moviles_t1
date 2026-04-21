package com.newtyf.t1_primera_pregunta.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.newtyf.t1_primera_pregunta.R;

public class MenuAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void irCandidatos(View view) {
        Intent x = new Intent(this, ListaCandidatosActivity.class);
        startActivity(x);
    }

    public void irElectores(View view) {
        Intent x = new Intent(this, ListaElectoresActivity.class);
        startActivity(x);
    }

    public void irEleccion(View view) {
        Intent x = new Intent(this, EleccionConfigActivity.class);
        startActivity(x);
    }

    public void irResultados(View view) {
        Intent x = new Intent(this, ResultadosActivity.class);
        startActivity(x);
    }

    public void cerrarSesion(View view) {
        finish();
    }
}

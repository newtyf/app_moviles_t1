package com.newtyf.t1_primera_pregunta.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.newtyf.t1_primera_pregunta.Models.DataStore;
import com.newtyf.t1_primera_pregunta.Models.Departamento;
import com.newtyf.t1_primera_pregunta.Models.Elector;
import com.newtyf.t1_primera_pregunta.Models.Modulo;
import com.newtyf.t1_primera_pregunta.R;

public class MenuElectorActivity extends AppCompatActivity {

    int electorId;
    TextView txtBienvenida, txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_elector);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        electorId = getIntent().getIntExtra("electorId", -1);
        txtBienvenida = findViewById(R.id.txtBienvenida);
        txtInfo = findViewById(R.id.txtInfo);

        Elector elector = DataStore.getInstance().getElectorPorId(electorId);
        if (elector != null) {
            Departamento d = DataStore.getInstance().getDepartamentoPorId(elector.getDepartamentoId());
            Modulo m = DataStore.getInstance().getModuloPorId(elector.getModuloId());
            txtBienvenida.setText("Bienvenido, " + elector.getNombre());
            txtInfo.setText("Modulo: " + (m != null ? m.getNombre() : "-") +
                    " | Depto: " + (d != null ? d.getNombre() : "-") +
                    "\nEstado: " + (elector.haVotado() ? "Ya voto" : "Pendiente de votar"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Elector elector = DataStore.getInstance().getElectorPorId(electorId);
        if (elector != null && txtInfo != null) {
            Departamento d = DataStore.getInstance().getDepartamentoPorId(elector.getDepartamentoId());
            Modulo m = DataStore.getInstance().getModuloPorId(elector.getModuloId());
            txtInfo.setText("Modulo: " + (m != null ? m.getNombre() : "-") +
                    " | Depto: " + (d != null ? d.getNombre() : "-") +
                    "\nEstado: " + (elector.haVotado() ? "Ya voto" : "Pendiente de votar"));
        }
    }

    public void irVotar(View view) {
        Intent x = new Intent(this, VotarActivity.class);
        x.putExtra("electorId", electorId);
        startActivity(x);
    }

    public void cerrarSesion(View view) {
        finish();
    }
}

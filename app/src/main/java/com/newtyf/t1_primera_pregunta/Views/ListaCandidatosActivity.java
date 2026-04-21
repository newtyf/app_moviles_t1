package com.newtyf.t1_primera_pregunta.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.newtyf.t1_primera_pregunta.Models.Candidato;
import com.newtyf.t1_primera_pregunta.Models.DataStore;
import com.newtyf.t1_primera_pregunta.R;
import java.util.List;

public class ListaCandidatosActivity extends AppCompatActivity {

    TableLayout tablaCandidatos;
    TextView txtConteo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_candidatos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tablaCandidatos = findViewById(R.id.tablaCandidatos);
        txtConteo = findViewById(R.id.txtConteo);
        cargarTabla();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarTabla();
    }

    void cargarTabla() {
        List<Candidato> lista = DataStore.getInstance().candidatos;
        txtConteo.setText("Total: " + lista.size() + " / 5");
        tablaCandidatos.removeAllViews();

        for (int i = 0; i < lista.size(); i++) {
            Candidato c = lista.get(i);

            TableRow fila = new TableRow(this);
            fila.setPadding(0, 8, 0, 8);

            TextView txtNombre = new TextView(this);
            txtNombre.setText(c.getNombre());
            txtNombre.setPadding(4, 0, 4, 0);

            Button btnEditar = new Button(this);
            btnEditar.setText("Editar");
            btnEditar.setOnClickListener(v -> {
                Intent x = new Intent(this, FormCandidatoActivity.class);
                x.putExtra("candidatoId", c.getId());
                startActivity(x);
            });

            Button btnEliminar = new Button(this);
            btnEliminar.setText("Eliminar");
            btnEliminar.setOnClickListener(v -> {
                DataStore.getInstance().candidatos.remove(c);
                cargarTabla();
            });

            fila.addView(txtNombre);
            fila.addView(btnEditar);
            fila.addView(btnEliminar);
            tablaCandidatos.addView(fila);
        }
    }

    public void insertarCandidato(View view) {
        if (DataStore.getInstance().candidatos.size() >= 5) {
            txtConteo.setText("Maximo 5 candidatos permitidos");
            return;
        }
        Intent x = new Intent(this, FormCandidatoActivity.class);
        startActivity(x);
    }

    public void volver(View view) {
        finish();
    }
}

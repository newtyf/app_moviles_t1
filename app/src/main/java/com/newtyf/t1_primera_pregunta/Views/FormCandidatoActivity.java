package com.newtyf.t1_primera_pregunta.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.newtyf.t1_primera_pregunta.Models.Candidato;
import com.newtyf.t1_primera_pregunta.Models.DataStore;
import java.util.List;
import com.newtyf.t1_primera_pregunta.R;

public class FormCandidatoActivity extends AppCompatActivity {

    EditText txtNombre, txtPropuesta;
    TextView txtTitulo, txtError;
    Button btnEliminar;
    int candidatoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_candidato);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtNombre = findViewById(R.id.txtNombre);
        txtPropuesta = findViewById(R.id.txtPropuesta);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtError = findViewById(R.id.txtError);
        btnEliminar = findViewById(R.id.btnEliminar);

        candidatoId = getIntent().getIntExtra("candidatoId", -1);

        if (candidatoId != -1) {
            txtTitulo.setText("Editar Candidato");
            btnEliminar.setVisibility(View.VISIBLE);
            Candidato c = DataStore.getInstance().getCandidatoPorId(candidatoId);
            if (c != null) { txtNombre.setText(c.getNombre()); txtPropuesta.setText(c.getPropuesta()); }
        } else {
            txtTitulo.setText("Nuevo Candidato");
            btnEliminar.setVisibility(View.GONE);
        }
    }

    public void guardar(View view) {
        String nombre = txtNombre.getText().toString().trim();
        String propuesta = txtPropuesta.getText().toString().trim();
        if (nombre.isEmpty()) { txtError.setText("Ingrese el nombre"); return; }
        if (propuesta.isEmpty()) { txtError.setText("Ingrese la propuesta"); return; }

        DataStore ds = DataStore.getInstance();
        if (candidatoId == -1) {
            ds.candidatos.add(new Candidato(ds.nextCandidatoId(), nombre, propuesta));
        } else {
            Candidato c = ds.getCandidatoPorId(candidatoId);
            if (c != null) { c.setNombre(nombre); c.setPropuesta(propuesta); }
        }
        finish();
    }

    public void eliminar(View view) {
        List<Candidato> lista = DataStore.getInstance().candidatos;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == candidatoId) {
                lista.remove(i);
                break;
            }
        }
        finish();
    }

    public void volver(View view) {
        finish();
    }
}

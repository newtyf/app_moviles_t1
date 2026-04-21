package com.newtyf.t1_primera_pregunta.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.newtyf.t1_primera_pregunta.Models.DataStore;
import com.newtyf.t1_primera_pregunta.Models.Elector;
import com.newtyf.t1_primera_pregunta.R;
import java.util.List;

public class FormElectorActivity extends AppCompatActivity {

    static final String[] MODULOS = {"Modulo A", "Modulo B", "Modulo C", "Modulo D", "Modulo E"};
    static final String[] DEPARTAMENTOS = {
        "Depto 101", "Depto 102", "Depto 103", "Depto 104", "Depto 105",
        "Depto 106", "Depto 107", "Depto 108", "Depto 109", "Depto 110",
        "Depto 111", "Depto 112", "Depto 113", "Depto 114", "Depto 115",
        "Depto 116", "Depto 117", "Depto 118", "Depto 119", "Depto 120"
    };

    EditText txtNombre, txtCedula, txtPassword;
    Spinner spModulo, spDepartamento;
    TextView txtTitulo, txtError;
    Button btnEliminar;
    int electorId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_elector);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtNombre = findViewById(R.id.txtNombre);
        txtCedula = findViewById(R.id.txtCedula);
        txtPassword = findViewById(R.id.txtPassword);
        spModulo = findViewById(R.id.spModulo);
        spDepartamento = findViewById(R.id.spDepartamento);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtError = findViewById(R.id.txtError);
        btnEliminar = findViewById(R.id.btnEliminar);

        ArrayAdapter<String> adMod = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MODULOS);
        adMod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spModulo.setAdapter(adMod);

        ArrayAdapter<String> adDep = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DEPARTAMENTOS);
        adDep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartamento.setAdapter(adDep);

        electorId = getIntent().getIntExtra("electorId", -1);

        if (electorId != -1) {
            txtTitulo.setText("Editar Elector");
            btnEliminar.setVisibility(View.VISIBLE);
            Elector e = DataStore.getInstance().getElectorPorId(electorId);
            if (e != null) {
                txtNombre.setText(e.getNombre());
                txtCedula.setText(e.getCedula());
                txtPassword.setText(e.getPassword());
                for (int i = 0; i < MODULOS.length; i++) {
                    if (MODULOS[i].equals(e.getModulo())) { spModulo.setSelection(i); break; }
                }
                for (int i = 0; i < DEPARTAMENTOS.length; i++) {
                    if (DEPARTAMENTOS[i].equals(e.getDepartamento())) { spDepartamento.setSelection(i); break; }
                }
            }
        } else {
            txtTitulo.setText("Nuevo Elector");
            btnEliminar.setVisibility(View.GONE);
        }
    }

    public void guardar(View view) {
        String nombre = txtNombre.getText().toString().trim();
        String cedula = txtCedula.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (nombre.isEmpty() || cedula.isEmpty() || password.isEmpty()) {
            txtError.setText("Complete todos los campos");
            return;
        }

        String modulo = spModulo.getSelectedItem().toString();
        String departamento = spDepartamento.getSelectedItem().toString();

        DataStore ds = DataStore.getInstance();
        if (electorId == -1) {
            ds.electores.add(new Elector(ds.nextElectorId(), nombre, cedula, password, departamento, modulo));
        } else {
            Elector e = ds.getElectorPorId(electorId);
            if (e != null) {
                e.setNombre(nombre);
                e.setCedula(cedula);
                e.setPassword(password);
                e.setModulo(modulo);
                e.setDepartamento(departamento);
            }
        }
        finish();
    }

    public void eliminar(View view) {
        List<Elector> lista = DataStore.getInstance().electores;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == electorId) {
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

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
import com.newtyf.t1_primera_pregunta.Models.Departamento;
import com.newtyf.t1_primera_pregunta.Models.Elector;
import com.newtyf.t1_primera_pregunta.Models.Modulo;
import com.newtyf.t1_primera_pregunta.R;
import java.util.List;

public class FormElectorActivity extends AppCompatActivity {

    EditText txtNombre, txtCedula, txtPassword;
    Spinner spModulo, spDepartamento;
    TextView txtTitulo, txtError;
    Button btnEliminar;
    int electorId = -1;
    List<Modulo> modulos;
    List<Departamento> departamentos;

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

        DataStore ds = DataStore.getInstance();
        modulos = ds.modulos;
        departamentos = ds.departamentos;

        ArrayAdapter<Modulo> adMod = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modulos);
        adMod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spModulo.setAdapter(adMod);

        ArrayAdapter<Departamento> adDep = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departamentos);
        adDep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartamento.setAdapter(adDep);

        electorId = getIntent().getIntExtra("electorId", -1);

        if (electorId != -1) {
            txtTitulo.setText("Editar Elector");
            btnEliminar.setVisibility(View.VISIBLE);
            Elector e = ds.getElectorPorId(electorId);
            if (e != null) {
                txtNombre.setText(e.getNombre());
                txtCedula.setText(e.getCedula());
                txtPassword.setText(e.getPassword());
                for (int i = 0; i < modulos.size(); i++) {
                    if (modulos.get(i).getId() == e.getModuloId()) { spModulo.setSelection(i); break; }
                }
                for (int i = 0; i < departamentos.size(); i++) {
                    if (departamentos.get(i).getId() == e.getDepartamentoId()) { spDepartamento.setSelection(i); break; }
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

        Modulo moduloSel = (Modulo) spModulo.getSelectedItem();
        Departamento deptoSel = (Departamento) spDepartamento.getSelectedItem();

        DataStore ds = DataStore.getInstance();
        if (electorId == -1) {
            ds.electores.add(new Elector(ds.nextElectorId(), nombre, cedula, password,
                    deptoSel.getId(), moduloSel.getId()));
        } else {
            Elector e = ds.getElectorPorId(electorId);
            if (e != null) {
                e.setNombre(nombre);
                e.setCedula(cedula);
                e.setPassword(password);
                e.setModuloId(moduloSel.getId());
                e.setDepartamentoId(deptoSel.getId());
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

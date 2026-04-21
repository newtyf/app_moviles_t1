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
import com.newtyf.t1_primera_pregunta.Models.DataStore;
import com.newtyf.t1_primera_pregunta.Models.Elector;
import com.newtyf.t1_primera_pregunta.R;
import java.util.List;

public class ListaElectoresActivity extends AppCompatActivity {

    TableLayout tablaElectores;
    TextView txtConteo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_electores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tablaElectores = findViewById(R.id.tablaElectores);
        txtConteo = findViewById(R.id.txtConteo);
        cargarTabla();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarTabla();
    }

    void cargarTabla() {
        List<Elector> lista = DataStore.getInstance().electores;
        txtConteo.setText("Total electores: " + lista.size());
        tablaElectores.removeAllViews();

        for (int i = 0; i < lista.size(); i++) {
            Elector e = lista.get(i);

            TableRow fila = new TableRow(this);
            fila.setPadding(0, 8, 0, 8);

            TextView txtNombre = new TextView(this);
            txtNombre.setText(e.getNombre());
            txtNombre.setPadding(4, 0, 4, 0);

            Button btnEditar = new Button(this);
            btnEditar.setText("Editar");
            btnEditar.setOnClickListener(v -> {
                Intent x = new Intent(this, FormElectorActivity.class);
                x.putExtra("electorId", e.getId());
                startActivity(x);
            });

            Button btnEliminar = new Button(this);
            btnEliminar.setText("Eliminar");
            btnEliminar.setOnClickListener(v -> {
                DataStore.getInstance().electores.remove(e);
                cargarTabla();
            });

            fila.addView(txtNombre);
            fila.addView(btnEditar);
            fila.addView(btnEliminar);
            tablaElectores.addView(fila);
        }
    }

    public void insertarElector(View view) {
        Intent x = new Intent(this, FormElectorActivity.class);
        startActivity(x);
    }

    public void volver(View view) {
        finish();
    }
}

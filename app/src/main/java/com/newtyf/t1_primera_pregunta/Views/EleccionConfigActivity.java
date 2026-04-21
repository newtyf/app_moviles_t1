package com.newtyf.t1_primera_pregunta.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.newtyf.t1_primera_pregunta.Models.DataStore;
import com.newtyf.t1_primera_pregunta.Models.EleccionConfig;
import com.newtyf.t1_primera_pregunta.R;

public class EleccionConfigActivity extends AppCompatActivity {

    EditText txtFecha, txtHoraInicio, txtHoraFin;
    TextView txtError, txtEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eleccion_config);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtFecha = findViewById(R.id.txtFecha);
        txtHoraInicio = findViewById(R.id.txtHoraInicio);
        txtHoraFin = findViewById(R.id.txtHoraFin);
        txtError = findViewById(R.id.txtError);
        txtEstado = findViewById(R.id.txtEstado);

        EleccionConfig cfg = DataStore.getInstance().eleccionConfig;
        if (cfg != null) {
            txtFecha.setText(cfg.getFecha());
            txtHoraInicio.setText(cfg.getHoraInicio());
            txtHoraFin.setText(cfg.getHoraFin());
            txtEstado.setText("Configuracion actual guardada");
        } else {
            txtEstado.setText("Sin configuracion");
        }
    }

    public void guardar(View view) {
        String fecha = txtFecha.getText().toString().trim();
        String horaInicio = txtHoraInicio.getText().toString().trim();
        String horaFin = txtHoraFin.getText().toString().trim();

        if (fecha.isEmpty() || horaInicio.isEmpty() || horaFin.isEmpty()) {
            txtError.setText("Complete todos los campos");
            return;
        }
        if (!fecha.matches("\\d{2}/\\d{2}/\\d{4}")) {
            txtError.setText("Fecha debe ser dd/MM/yyyy");
            return;
        }
        if (!horaInicio.matches("\\d{2}:\\d{2}") || !horaFin.matches("\\d{2}:\\d{2}")) {
            txtError.setText("Hora debe ser HH:mm");
            return;
        }

        DataStore.getInstance().eleccionConfig = new EleccionConfig(fecha, horaInicio, horaFin);
        txtError.setText("");
        txtEstado.setText("Guardado: " + fecha + " de " + horaInicio + " a " + horaFin);
    }

    public void volver(View view) {
        finish();
    }
}

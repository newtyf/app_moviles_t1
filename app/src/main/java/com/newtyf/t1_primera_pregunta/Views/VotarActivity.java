package com.newtyf.t1_primera_pregunta.Views;

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
import com.newtyf.t1_primera_pregunta.Models.EleccionConfig;
import com.newtyf.t1_primera_pregunta.Models.Elector;
import com.newtyf.t1_primera_pregunta.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class VotarActivity extends AppCompatActivity {

    TableLayout tablaVotar;
    TextView txtInfo, txtError, txtMensaje;
    int electorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_votar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tablaVotar = findViewById(R.id.tablaVotar);
        txtInfo = findViewById(R.id.txtInfo);
        txtError = findViewById(R.id.txtError);
        txtMensaje = findViewById(R.id.txtMensaje);

        electorId = getIntent().getIntExtra("electorId", -1);
        DataStore ds = DataStore.getInstance();
        Elector elector = ds.getElectorPorId(electorId);

        if (elector == null) { txtError.setText("Error: elector no encontrado"); return; }

        if (elector.haVotado()) {
            txtInfo.setText("Ya emitio su voto. No puede votar nuevamente.");
            txtMensaje.setText("");
            tablaVotar.setVisibility(View.GONE);
            return;
        }

        EleccionConfig cfg = ds.eleccionConfig;
        if (cfg == null) {
            txtError.setText("No hay eleccion configurada");
            tablaVotar.setVisibility(View.GONE);
            return;
        }

        if (!estaEnIntervalo(cfg)) {
            txtError.setText("La votacion no esta activa en este momento.\nFecha: " +
                    cfg.getFecha() + " de " + cfg.getHoraInicio() + " a " + cfg.getHoraFin());
            tablaVotar.setVisibility(View.GONE);
            return;
        }

        txtInfo.setText("Seleccione su candidato, " + elector.getNombre() + ":");

        for (int i = 0; i < ds.candidatos.size(); i++) {
            Candidato c = ds.candidatos.get(i);

            TableRow fila = new TableRow(this);
            fila.setPadding(0, 8, 0, 8);

            TextView txtNombre = new TextView(this);
            txtNombre.setText(c.getNombre() + "\n" + c.getPropuesta());
            txtNombre.setPadding(4, 0, 4, 0);

            Button btnVotar = new Button(this);
            btnVotar.setText("Votar");
            btnVotar.setOnClickListener(v -> {
                elector.setVotoCandidatoId(c.getId());
                txtMensaje.setText("Voto registrado por: " + c.getNombre());
                txtInfo.setText("Su voto ha sido emitido exitosamente.");
                tablaVotar.setVisibility(View.GONE);
            });

            fila.addView(txtNombre);
            fila.addView(btnVotar);
            tablaVotar.addView(fila);
        }

        TableRow filaBlanco = new TableRow(this);
        filaBlanco.setPadding(0, 8, 0, 8);

        TextView txtBlanco = new TextView(this);
        txtBlanco.setText("Voto en Blanco");
        txtBlanco.setPadding(4, 0, 4, 0);

        Button btnBlanco = new Button(this);
        btnBlanco.setText("Votar");
        btnBlanco.setOnClickListener(v -> {
            elector.setVotoCandidatoId(-1);
            txtMensaje.setText("Voto en blanco registrado");
            txtInfo.setText("Su voto ha sido emitido exitosamente.");
            tablaVotar.setVisibility(View.GONE);
        });

        filaBlanco.addView(txtBlanco);
        filaBlanco.addView(btnBlanco);
        tablaVotar.addView(filaBlanco);
    }

    boolean estaEnIntervalo(EleccionConfig cfg) {
        try {
            SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            sdfFecha.setTimeZone(TimeZone.getTimeZone("America/Lima"));
            SimpleDateFormat sdfFechaHora = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            sdfFechaHora.setTimeZone(TimeZone.getTimeZone("America/Lima"));
            Date ahora = new Date();
            String fechaHoy = sdfFecha.format(ahora);
            if (!fechaHoy.equals(cfg.getFecha())) return false;
            Date inicio = sdfFechaHora.parse(cfg.getFecha() + " " + cfg.getHoraInicio());
            Date fin = sdfFechaHora.parse(cfg.getFecha() + " " + cfg.getHoraFin());
            return ahora.after(inicio) && ahora.before(fin);
        } catch (Exception e) {
            return false;
        }
    }

    public void volver(View view) {
        finish();
    }
}

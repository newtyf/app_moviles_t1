package com.newtyf.t1_primera_pregunta.Views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class VotarActivity extends AppCompatActivity {

    ListView lstCandidatos;
    TextView txtInfo, txtError, txtMensaje;
    int electorId;
    List<Candidato> candidatosOpciones = new ArrayList<>();
    boolean tieneVotoEnBlanco = false;

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

        lstCandidatos = findViewById(R.id.lstCandidatos);
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
            lstCandidatos.setVisibility(View.GONE);
            return;
        }

        EleccionConfig cfg = ds.eleccionConfig;
        if (cfg == null) {
            txtError.setText("No hay eleccion configurada");
            lstCandidatos.setVisibility(View.GONE);
            return;
        }

        if (!estaEnIntervalo(cfg)) {
            txtError.setText("La votacion no esta activa en este momento.\nFecha: " +
                    cfg.getFecha() + " de " + cfg.getHoraInicio() + " a " + cfg.getHoraFin());
            lstCandidatos.setVisibility(View.GONE);
            return;
        }

        txtInfo.setText("Seleccione su candidato, " + elector.getNombre() + ":");

        List<String> etiquetas = new ArrayList<>();
        for (int i = 0; i < ds.candidatos.size(); i++) {
            Candidato c = ds.candidatos.get(i);
            candidatosOpciones.add(c);
            etiquetas.add(c.getNombre() + " - " + c.getPropuesta());
        }
        etiquetas.add("--- Voto en Blanco ---");
        tieneVotoEnBlanco = true;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, etiquetas);
        lstCandidatos.setAdapter(adapter);

        lstCandidatos.setOnItemClickListener((parent, view, position, id) -> {
            if (position < candidatosOpciones.size()) {
                Candidato sel = candidatosOpciones.get(position);
                elector.setVotoCandidatoId(sel.getId());
                txtMensaje.setText("Voto registrado por: " + sel.getNombre());
            } else {
                elector.setVotoCandidatoId(-1);
                txtMensaje.setText("Voto en blanco registrado");
            }
            lstCandidatos.setVisibility(View.GONE);
            txtInfo.setText("Su voto ha sido emitido exitosamente.");
        });
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

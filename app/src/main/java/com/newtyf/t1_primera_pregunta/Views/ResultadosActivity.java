package com.newtyf.t1_primera_pregunta.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.newtyf.t1_primera_pregunta.Models.Candidato;
import com.newtyf.t1_primera_pregunta.Models.DataStore;
import com.newtyf.t1_primera_pregunta.Models.EleccionConfig;
import com.newtyf.t1_primera_pregunta.R;

public class ResultadosActivity extends AppCompatActivity {

    TextView txtResultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtResultados = findViewById(R.id.txtResultados);
        mostrarResultados();
    }

    void mostrarResultados() {
        DataStore ds = DataStore.getInstance();
        String texto = "";

        EleccionConfig cfg = ds.eleccionConfig;
        if (cfg != null) {
            texto += "Fecha: " + cfg.getFecha() + "  (" + cfg.getHoraInicio() + " - " + cfg.getHoraFin() + ")\n\n";
        } else {
            texto += "Sin configuracion de eleccion\n\n";
        }

        int totalElectores = ds.electores.size();
        int totalVotaron = 0;
        for (int i = 0; i < ds.electores.size(); i++) {
            if (ds.electores.get(i).haVotado()) totalVotaron++;
        }
        int noVotaron = ds.contarNoVotaron();
        int votosEnBlanco = ds.contarVotosEnBlanco();

        texto += "=== RESULTADOS ===\n";
        texto += "Total electores: " + totalElectores + "\n";
        texto += "Total que votaron: " + totalVotaron + "\n";
        texto += "Sin votar (blanco por falta): " + noVotaron + "\n";
        texto += "Votos en blanco emitidos: " + votosEnBlanco + "\n\n";

        texto += "=== VOTOS POR CANDIDATO ===\n";
        for (int i = 0; i < ds.candidatos.size(); i++) {
            Candidato c = ds.candidatos.get(i);
            int votos = ds.contarVotosCandidato(c.getId());
            texto += c.getNombre() + ": " + votos + " voto(s)\n";
        }

        texto += "\n=== GANADOR ===\n";
        Candidato ganador = ds.getCandidatoGanador();
        if (ganador != null && ds.contarVotosCandidato(ganador.getId()) > 0) {
            texto += ganador.getNombre() + " con " + ds.contarVotosCandidato(ganador.getId()) + " voto(s)";
        } else {
            texto += "Aun no hay votos para candidatos";
        }

        txtResultados.setText(texto);
    }

    public void volver(View view) {
        finish();
    }
}

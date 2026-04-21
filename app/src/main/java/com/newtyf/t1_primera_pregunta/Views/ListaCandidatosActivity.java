package com.newtyf.t1_primera_pregunta.Views;

import android.content.Intent;
import android.os.Bundle;
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
import com.newtyf.t1_primera_pregunta.R;
import java.util.List;

public class ListaCandidatosActivity extends AppCompatActivity {

    ListView lstCandidatos;
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
        lstCandidatos = findViewById(R.id.lstCandidatos);
        txtConteo = findViewById(R.id.txtConteo);
        cargarLista();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarLista();
    }

    void cargarLista() {
        List<Candidato> lista = DataStore.getInstance().candidatos;
        txtConteo.setText("Total: " + lista.size() + " / 5");
        ArrayAdapter<Candidato> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        lstCandidatos.setAdapter(adapter);

        lstCandidatos.setOnItemClickListener((parent, view, position, id) -> {
            Candidato c = lista.get(position);
            Intent x = new Intent(this, FormCandidatoActivity.class);
            x.putExtra("candidatoId", c.getId());
            startActivity(x);
        });
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

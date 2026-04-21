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
import com.newtyf.t1_primera_pregunta.Models.DataStore;
import com.newtyf.t1_primera_pregunta.Models.Elector;
import com.newtyf.t1_primera_pregunta.R;
import java.util.List;

public class ListaElectoresActivity extends AppCompatActivity {

    ListView lstElectores;
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
        lstElectores = findViewById(R.id.lstElectores);
        txtConteo = findViewById(R.id.txtConteo);
        cargarLista();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarLista();
    }

    void cargarLista() {
        List<Elector> lista = DataStore.getInstance().electores;
        txtConteo.setText("Total electores: " + lista.size());
        ArrayAdapter<Elector> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        lstElectores.setAdapter(adapter);

        lstElectores.setOnItemClickListener((parent, view, position, id) -> {
            Elector e = lista.get(position);
            Intent x = new Intent(this, FormElectorActivity.class);
            x.putExtra("electorId", e.getId());
            startActivity(x);
        });
    }

    public void insertarElector(View view) {
        Intent x = new Intent(this, FormElectorActivity.class);
        startActivity(x);
    }

    public void volver(View view) {
        finish();
    }
}

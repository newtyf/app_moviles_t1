package com.newtyf.t1_primera_pregunta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.newtyf.t1_primera_pregunta.Models.DataStore;
import com.newtyf.t1_primera_pregunta.Models.Elector;
import com.newtyf.t1_primera_pregunta.Views.MenuAdminActivity;
import com.newtyf.t1_primera_pregunta.Views.MenuElectorActivity;

public class MainActivity extends AppCompatActivity {

    EditText txtCedula, txtPassword;
    TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtCedula = findViewById(R.id.txtCedula);
        txtPassword = findViewById(R.id.txtPassword);
        txtError = findViewById(R.id.txtError);
    }

    public void ingresar(View view) {
        String cedula = txtCedula.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (cedula.isEmpty() || password.isEmpty()) {
            txtError.setText("Complete todos los campos");
            return;
        }


        if (cedula.equals(DataStore.ADMIN_USER) && password.equals(DataStore.ADMIN_PASS)) {
            Intent x = new Intent(this, MenuAdminActivity.class);
            startActivity(x);
            return;
        }
        // Verificar si es elector
        Elector elector = DataStore.getInstance().autenticarElector(cedula, password);
        if (elector != null) {
            Intent x = new Intent(this, MenuElectorActivity.class);
            x.putExtra("electorId", elector.getId());
            startActivity(x);
            return;
        }

        txtError.setText("Cedula o contrasena incorrectos");
    }
}
package mainactivity.franzfonseca.com.firebasepeluchitos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText id, nombre, cantidad, valor, contenido;
    Button bingresar, bactualizar, bconsultar, beliminar, bventa, binventario, bganancias;
    Integer contPeluches=0;
    private static final String FIREBASE_URL="https://agendapeluchitos.firebaseio.com/";
    private Firebase firebasedatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bingresar = (Button) findViewById(R.id.bingresar);
        bactualizar = (Button) findViewById(R.id.bactualizar);
        bconsultar = (Button) findViewById(R.id.bconsultar);
        beliminar = (Button) findViewById(R.id.beliminar);
        bventa = (Button) findViewById(R.id.bventa);
        binventario = (Button) findViewById(R.id.binventario);
        bganancias = (Button) findViewById(R.id.bganancias);

        cantidad = (EditText) findViewById(R.id.ecantidad);
        nombre = (EditText) findViewById(R.id.enombre);
        id = (EditText) findViewById(R.id.eidpeluche);
        valor = (EditText) findViewById(R.id.evalor);
        contenido = (EditText) findViewById(R.id.econtenido);

        Firebase.setAndroidContext(this);
        firebasedatos = new Firebase(FIREBASE_URL);

        bingresar.setOnClickListener(this);
        bactualizar.setOnClickListener(this);
        bconsultar.setOnClickListener(this);
        beliminar.setOnClickListener(this);
        binventario.setOnClickListener(this);
        bventa.setOnClickListener(this);
        bganancias.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bingresar:
                Toast.makeText(MainActivity.this, "Ingresar", Toast.LENGTH_SHORT).show();
                String name= nombre.getText().toString();
                String cantida= cantidad.getText().toString();
                String val= valor.getText().toString();
                Firebase firebd= firebasedatos.child("peluche" + contPeluches);
                Peluches peluches = new Peluches(String.valueOf(contPeluches), name,cantida,val);
                firebd.setValue(peluches);
                id.getText().clear();
                nombre.getText().clear();
                cantidad.getText().clear();
                valor.getText().clear();
                contenido.getText().clear();
                contPeluches++;
                break;

            case R.id.binventario:
                firebasedatos.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        contenido.setText(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                break;

            case R.id.bconsultar:
                String codigo = id.getText().toString();
                final String idS = "peluche "+codigo;
                firebasedatos.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(idS).exists()){
                            contenido.setText(dataSnapshot.child(idS).getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                break;

            case R.id.bactualizar:
                codigo = id.getText().toString();
                name= nombre.getText().toString();
                cantida= cantidad.getText().toString();
                val= valor.getText().toString();
                firebd = firebasedatos.child("peluche "+codigo);
                Map<String, Object> nuevoNombre = new HashMap<>();
                nuevoNombre.put("nombre", name);
                firebd.updateChildren(nuevoNombre);
                Map<String, Object> nuevoCantidad = new HashMap<>();
                nuevoCantidad.put("cantidad", cantida);
                firebd.updateChildren(nuevoCantidad);
                Map<String, Object> nuevoValor = new HashMap<>();
                nuevoValor.put("valor", val);
                firebd.updateChildren(nuevoValor);
                id.getText().clear();
                nombre.getText().clear();
                cantidad.getText().clear();
                valor.getText().clear();
                break;

            case R.id.beliminar:
                codigo = id.getText().toString();
                firebd = firebasedatos.child("peluche "+codigo);
                firebd.removeValue();
                break;

            case  R.id.bventa:
                startActivity(new Intent(MainActivity.this, Ventas.class));
                break;

            case  R.id.bganancias:
                startActivity(new Intent(MainActivity.this, Ganancias.class));




        }

    }
}

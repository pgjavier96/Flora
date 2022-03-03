package org.izv.flora.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.flora.MainActivity;
import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.viewmodel.AddFloraViewModel;

public class AddFloraActivity extends AppCompatActivity {

    private EditText etNombre, etFamilia, etIdentificacion, etAltitiud, etHabitat, etFitosociologia, etBiotopo, etBiologiaReproducctiva, etFloracion, etFructificacion,
                     etExpresionSexual, etPolinizacion, etDispersion, etNumeroCromatico,etReproduccionAsexual,
                     etDistribucion,etBiologia,etDemografia,etMedidasPropuestas,etAmenazas;
    private FloatingActionButton btAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flora);
        initialize();
    }

    private void initialize() {
        etNombre = findViewById(R.id.etNombre);
        etFamilia = findViewById(R.id.etfamilia);
        etIdentificacion = findViewById(R.id.etidentificacion);
        etAltitiud = findViewById(R.id.etaltitud);
        etHabitat = findViewById(R.id.ethabitat);
        etFitosociologia = findViewById(R.id.etFitosociologia);
        etBiotopo = findViewById(R.id.etBiotopo);
        etBiologiaReproducctiva = findViewById(R.id.etBiologiaReproductiva);
        etFloracion = findViewById(R.id.etFloracion);
        etFructificacion = findViewById(R.id.etFructificacion);
        etExpresionSexual = findViewById(R.id.etExpresionSexual);
        etPolinizacion = findViewById(R.id.etPolinizacion);
        etDispersion = findViewById(R.id.etDispersion);
        etNumeroCromatico = findViewById(R.id.etNumeroCromatico);
        etReproduccionAsexual = findViewById(R.id.etReproduccionAsexual);
        etDistribucion = findViewById(R.id.etDistribucion);
        etBiologia = findViewById(R.id.etBiologia);
        etDemografia = findViewById(R.id.etDemografia);
        etMedidasPropuestas = findViewById(R.id.etMedidasPropuesstas);
        etAmenazas = findViewById(R.id.etAmenazas);





        AddFloraViewModel avm = new ViewModelProvider(this).get(AddFloraViewModel.class);
        avm.getAddFloraLiveData().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                if(aLong > 0) {
                    finish();

                } else {
                    Toast.makeText(AddFloraActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        });




        btAdd = findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flora flora = new Flora();
                flora.setNombre(etNombre.getText().toString());
                flora.setFamilia(etFamilia.getText().toString());
                flora.setIdentificacion(etIdentificacion.getText().toString());
                flora.setAltitud(etAltitiud.getText().toString());
                flora.setHabitat(etHabitat.getText().toString());
                flora.setFitosociologia(etFitosociologia.getText().toString());
                flora.setBiologia(etBiologia.getText().toString());
                flora.setBiologia_reproductiva(etBiologiaReproducctiva.getText().toString());
                flora.setFloracion(etFloracion.getText().toString());
                flora.setFructificacion(etFructificacion.getText().toString());
                flora.setExpresion_sexual(etExpresionSexual.getText().toString());
                flora.setPolinizacion(etPolinizacion.getText().toString());
                flora.setDispersion(etDispersion.getText().toString());
                flora.setNumero_cromosomatico(etNumeroCromatico.getText().toString());
                flora.setReproduccion_asexual(etReproduccionAsexual.getText().toString());
                flora.setDistribucion(etDistribucion.getText().toString());
                flora.setBiologia(etBiologia.getText().toString());
                flora.setDemografia(etDemografia.getText().toString());
                flora.setAmenazas(etAmenazas.getText().toString());
                flora.setMedidas_propuestas(etMedidasPropuestas.getText().toString());
                flora.setBiotipo(etBiotopo.getText().toString());

                avm.createFlora(flora);
                MainActivity.dondepasa=1;
                MainActivity.nombre = etNombre.getText().toString();
                startActivity(new Intent(AddFloraActivity.this, AddImagenActivity.class));
            }
        });
    }

}
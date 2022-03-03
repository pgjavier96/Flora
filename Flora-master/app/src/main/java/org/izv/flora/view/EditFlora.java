package org.izv.flora.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.flora.MainActivity;
import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.model.entity.Imagen;
import org.izv.flora.viewmodel.DeleteFloraViewModel;
import org.izv.flora.viewmodel.DeleteImagenViewModel;
import org.izv.flora.viewmodel.EditFloraViewModel;
import org.izv.flora.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

public class EditFlora extends AppCompatActivity {

    private EditText etNombre, etFamilia, etIdentificacion, etAltitiud, etHabitat, etFitosociologia, etBiotopo, etBiologiaReproducctiva, etFloracion, etFructificacion,
            etExpresionSexual, etPolinizacion, etDispersion, etNumeroCromatico,etReproduccionAsexual,
            etDistribucion,etBiologia,etDemografia,etMedidasPropuestas,etAmenazas;
    private FloatingActionButton btEdit, btDelete;
    Flora flora = new Flora();
    private DeleteFloraViewModel dvm;
    private DeleteImagenViewModel dim;
    private MainActivityViewModel mavm;
    private ArrayList<Imagen> imagenList = new ArrayList<>();
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flora);
        initialize();


    }

    private void initialize() {
        flora = getIntent().getExtras().getParcelable("flora");
        mavm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        imagenList = mavm.getImagenLiveData().getValue();

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



        EditFloraViewModel evm = new ViewModelProvider(this).get(EditFloraViewModel.class);
        evm.getEditFloraLiveData().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                Log.v("xyzyx", "respuesta " + aLong);
                if(aLong > 0) {
                    finish();
                }
            }
        });




        etNombre.setText(flora.nombre);
        etFamilia.setText(flora.familia);
        etIdentificacion.setText(flora.identificacion);
        etAltitiud.setText(flora.altitud);
        etHabitat.setText(flora.habitat);
        etFitosociologia.setText(flora.fitosociologia);
        etBiotopo.setText(flora.biotipo);
        etBiologiaReproducctiva.setText(flora.biologia_reproductiva);
        etFloracion.setText(flora.floracion);
        etFructificacion.setText(flora.fructificacion);
        etExpresionSexual.setText(flora.expresion_sexual);
        etPolinizacion.setText(flora.polinizacion);
        etDispersion.setText(flora.dispersion);
        etNumeroCromatico.setText(flora.numero_cromosomatico);
        etReproduccionAsexual.setText(flora.reproduccion_asexual);
        etDistribucion.setText(flora.distribucion);
        etBiologia.setText(flora.biologia);
        etDemografia.setText(flora.demografia);
        etMedidasPropuestas.setText(flora.medidas_propuestas);
        etAmenazas.setText(flora.amenazas);

        btEdit = findViewById(R.id.btAdd);

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlora();
                Log.v("xyzyx", String.valueOf(getFlora()));
                evm.editFlora(flora.getId(),getFlora());
                MainActivity.dondepasa=2;
                MainActivity.nombre = etNombre.getText().toString();
                Intent intencion = new Intent(EditFlora.this, AddImagenActivity.class);
                startActivity(intencion);
            }
        });



        dim = new ViewModelProvider(this).get(DeleteImagenViewModel.class);
        dim.getDeleteImagenLiveData().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                Log.v("xyzyx", "respuesta " + aLong);
                if(aLong >0) {
                    finish();
                }
            }
        });



        dvm = new ViewModelProvider(this).get(DeleteFloraViewModel.class);
        dvm.getDeleteFloraLiveData().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                Log.v("xyzyx", "respuesta " + aLong);
                if(aLong >0) {
                    finish();
                }
            }
        });



        btDelete = findViewById(R.id.btDelete);
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder  = new AlertDialog.Builder(v.getContext());
                builder.setTitle("titulo")
                        .setMessage("Quieres borrar una flora")
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //dim.deleteImagen(selectPosition());
                                dvm.deleteFlora(flora.getId());

                            }
                        });
                builder.create().show();
            }
        });




    }

    private Flora getFlora(){
        Flora flora = new Flora();
        flora.setNombre(etNombre.getText().toString());
        flora.setFamilia(etFamilia.getText().toString());
        flora.setFamilia(etBiotopo.getText().toString());
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
        return flora;
    }

    public long selectPosition(){
        for (int i = 0; i < imagenList.size(); ++i) {
            if (imagenList.get(i).idflora == flora.getId()){
                pos = i;

            }
        }
        return pos;


    }




}
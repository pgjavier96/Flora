package org.izv.flora.view;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.flora.MainActivity;
import org.izv.flora.R;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.model.entity.Imagen;
import org.izv.flora.viewmodel.AddFloraViewModel;
import org.izv.flora.viewmodel.AddImagenViewModel;
import org.izv.flora.viewmodel.MainActivityViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddImagenActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> launcher;
    private Intent resultadoImagen = null;
    private EditText etNombre, etDescripcion;
    private TextView tvIdFlora;
    private Button btMas, btMenos;
    private int pos;

    private long idflora;

    private ImageView btSelectImage;
    private AddImagenViewModel aivm;
    private Bitmap btp_img = null;
    private InputStream in_stream;
    private FloatingActionButton btAddImagen;
    private MainActivityViewModel mavm;
    private  String recibe;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_imagen);
        initialize();


    }

    private void initialize() {
        id = -1;
        tvIdFlora = findViewById(R.id.tvIdFlora);
        pos = 0;
        mavm = new ViewModelProvider(this).get(MainActivityViewModel.class); //obtengo viewmodel de la actividad
        MutableLiveData<ArrayList<Flora>> floraList = mavm.getFloraLiveData();
        mavm.getFlora(); //llama al servicio REST la encola y devuelve la lista de la base de datos

        floraList.observe(this,  floras -> { //cuando se llene la lista veremos los cambios
            if(MainActivity.dondepasa==1){
                tvIdFlora.setText(floras.get(mavm.getFloraLiveData().getValue().size()-1).getNombre()+"");
                id= floras.get(mavm.getFloraLiveData().getValue().size()-1).getId();
            }else if(MainActivity.dondepasa==2){
                tvIdFlora.setText(MainActivity.nombre);
                id = MainActivity.id;
            }

        });







        launcher = getLauncher();
        etDescripcion = findViewById(R.id.etDescripcion);
        etNombre = findViewById(R.id.etNombreImagen);



        btSelectImage = findViewById(R.id.btSelectImage);
        btSelectImage.setOnClickListener(v -> {
            selectImage();
        });
        btAddImagen = findViewById(R.id.btAddImagen);
        btAddImagen.setOnClickListener(v -> {
            uploadDataImage();
            startActivity(new Intent(AddImagenActivity.this, MainActivity.class));

        });
        aivm = new ViewModelProvider(this).get(AddImagenViewModel.class);
    }

    private void uploadDataImage() {
        String nombre = etNombre.getText().toString();
        String descripcion = etDescripcion.getText().toString();

        if(!(nombre.trim().isEmpty() ||
                id<0||
                resultadoImagen == null)) {
            Imagen imagen = new Imagen();
            imagen.name = nombre;
            imagen.descr = descripcion;
            imagen.idflora = id;
            aivm.saveImagen(resultadoImagen, imagen);
        }
    }

    ActivityResultLauncher<Intent> getLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    //respuesta al resultado de haber seleccionado una imagen
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        //copyData(result.getData());
                        resultadoImagen = result.getData();
                    }
                }
        );


    }

    Intent getContentIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        return intent;
    }

    void selectImage() {
        Intent intent = getContentIntent();
        launcher.launch(intent);
    }
}
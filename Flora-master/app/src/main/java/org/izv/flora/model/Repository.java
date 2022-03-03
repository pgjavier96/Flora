package org.izv.flora.model;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.izv.flora.model.api.FloraClient;
import org.izv.flora.model.entity.CreateResponse;
import org.izv.flora.model.entity.Flora;
import org.izv.flora.model.entity.Imagen;
import org.izv.flora.model.entity.RowsResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private Context context;
    private static FloraClient floraClient;


    private MutableLiveData<ArrayList<Flora>> floraLiveData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Imagen>> imagenLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> addFloraLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> addImagenLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> editFloraLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> deleteFloraLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> deleteImagenLiveData = new MutableLiveData<>();

    static {
        floraClient = getFloraClient();
    }

    public Repository(Context context) {
        this.context = context;
    }

    private static FloraClient getFloraClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://informatica.ieszaidinvergeles.org:10014/ad/felixRDLF/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(FloraClient.class);
    }

    public MutableLiveData<ArrayList<Flora>> getFloraLiveData() {
        return floraLiveData;
    }

    public MutableLiveData<ArrayList<Imagen>> getImagenLiveData() {
        return imagenLiveData;
    }

    public MutableLiveData<Long> getAddFloraLiveData() {
        return addFloraLiveData;
    }

    public MutableLiveData<Long> getEditFloraLiveData() {
        return editFloraLiveData;
    }

    public MutableLiveData<Long> getAddImagenLiveData() {
        return addImagenLiveData;
    }

    public MutableLiveData<Long> getDeleteFloraLiveData() {
        return addImagenLiveData;
    }

    public MutableLiveData<Long> getDeleteImagenLiveData() {
        return deleteImagenLiveData;
    }



    public void getFlora(long id) {

    }

    public void getFlora() {
        Call<ArrayList<Flora>> call = floraClient.getFlora();
        call.enqueue(new Callback<ArrayList<Flora>>() {
            @Override
            public void onResponse(Call<ArrayList<Flora>> call, Response<ArrayList<Flora>> response) {
                floraLiveData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<Flora>> call, Throwable t) {

            }
        });
    }



    public void getImagen() {
        Call<ArrayList<Imagen>> call = floraClient.getImagen();
        call.enqueue(new Callback<ArrayList<Imagen>>() {
            @Override
            public void onResponse(Call<ArrayList<Imagen>> call, Response<ArrayList<Imagen>> response) {
                imagenLiveData.setValue(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<Imagen>> call, Throwable t) {

            }
        });
    }




    public void createFlora(Flora flora) {
        Call<CreateResponse> call = floraClient.createFlora(flora);
        call.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                addFloraLiveData.setValue(response.body().id);
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {

            }
        });
    }

    public void editFlora(long id, Flora flora) {
        Call<RowsResponse> call = floraClient.editFlora(id,flora);
        call.enqueue(new Callback<RowsResponse>() {
            @Override
            public void onResponse(Call<RowsResponse> call, Response<RowsResponse> response) {
                editFloraLiveData.setValue(response.body().rows);
            }

            @Override
            public void onFailure(Call<RowsResponse> call, Throwable t) {
                Log.v("xyzyx", t.getLocalizedMessage());
                editFloraLiveData.setValue(0l);
            }
    });
    }

    public void deleteFlora(long id) {
        Call<RowsResponse> call = floraClient.deleteFlora(id);
        call.enqueue(new Callback<RowsResponse>() {
            @Override
            public void onResponse(Call<RowsResponse> call, Response<RowsResponse> response) {
                deleteFloraLiveData.setValue(response.body().rows);
            }

            @Override
            public void onFailure(Call<RowsResponse> call, Throwable t) {
                Log.v("xyzyx", t.getLocalizedMessage());
                deleteFloraLiveData.setValue(0l);
            }
        });
    }

    public void deleteImagen(long id) {
        Call<RowsResponse> call = floraClient.deleteImagen(id);
        call.enqueue(new Callback<RowsResponse>() {
            @Override
            public void onResponse(Call<RowsResponse> call, Response<RowsResponse> response) {
                deleteImagenLiveData.setValue(response.body().rows);
            }

            @Override
            public void onFailure(Call<RowsResponse> call, Throwable t) {
                Log.v("xyzyx", t.getLocalizedMessage());
                deleteImagenLiveData.setValue(0l);
            }
        });
    }



    public void saveImagen(Intent intent, Imagen imagen) {
        copyData(intent, imagen.name);
        File file = new File(context.getExternalFilesDir(null), imagen.name);
        Log.v("xyzyx", file.getAbsolutePath());
        subirImagen(file, imagen);
    }

    private void subirImagen(File file, Imagen imagen) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
        Call<Long> call = floraClient.subirImagen(body, imagen.idflora, imagen.descr);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                addImagenLiveData.setValue(response.body());
                Log.v("xyzyx", "ok");
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.v("xyzyx", "error");
            }
        });
    }

    private boolean copyData(Intent data, String name) {
        Log.v("xyzyx", "copyData");
        boolean result = true;
        Uri uri = data.getData();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = context.getContentResolver().openInputStream(uri);
            out = new FileOutputStream(new File(context.getExternalFilesDir(null), name));
            byte[] buffer = new byte[1024];
            int len;
            int cont = 0;
            while ((len = in.read(buffer)) != -1) {
                cont++;
                Log.v("xyzyx", "copyData" + cont);
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            result = false;
            Log.v("xyzyx", e.toString());
        }
        return result;
    }
}
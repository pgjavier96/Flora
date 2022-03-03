package org.izv.flora.viewmodel;

import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.izv.flora.model.Repository;
import org.izv.flora.model.entity.Imagen;

public class AddImagenViewModel extends AndroidViewModel {

    private Repository repository;

    public AddImagenViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public MutableLiveData<Long> getAddImagenLiveData() {
        return repository.getAddImagenLiveData();
    }

    public void saveImagen(Intent intent, Imagen imagen) {
        repository.saveImagen(intent, imagen);
    }
}

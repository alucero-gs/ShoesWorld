package com.example.shoesworld.ui.productos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProductosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ProductosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hola soy stock");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
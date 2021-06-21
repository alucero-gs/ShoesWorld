package com.example.shoesworld.ui.inactivos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InactivosViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public InactivosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hola soy stock");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
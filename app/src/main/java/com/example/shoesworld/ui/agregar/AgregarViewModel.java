package com.example.shoesworld.ui.agregar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AgregarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AgregarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is agregar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
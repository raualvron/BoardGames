package com.example.boardgames.Classes;
import android.widget.EditText;

import java.util.HashMap;
import android.util.Patterns;


public class ValidatorHelper {

    public boolean isNullOrEmpty(String string){
        return string.length() == 0;
    }

    public boolean isRangeInvalid(String text, Integer min, Integer max) {
        return text.length() > 0 && text.length() < min || text.length() > max;
    }

    public boolean isEmailInvalid(String text) {
        return text.length() > 0 && !Patterns.EMAIL_ADDRESS.matcher(text).matches();
    }

    public Boolean isFormValid(String... strings) {
        for(String str : strings){
            if(str == null || str.isEmpty())
                return true;
        }
        return false;
    }


    public String showError(EditText editable, String type, String field, HashMap<String, String> params) {
        if (type == "string") {
            editable.setText("Please enter a " + field + " valid");
        }
        if (type == "range") {
            if (params.size() != 0) {
                editable.setText("The " + field + " must be between " + params.get("min") +" and "+ params.get("max") + " characters");
            }
        }

        return field + " no validated";
    }

}
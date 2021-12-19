package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.model.User;

import org.json.JSONException;

public class ErrorHandling extends AppCompatActivity
implements View.OnClickListener {

    EditText editText;
    Button btSendError;
    boolean isEnglish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_handling);

        User currentUser= getIntent().getParcelableExtra("currentUser");
        if(currentUser.getLanguage().toLowerCase().startsWith("e")){
            isEnglish = true;
        }

        editText = findViewById(R.id.error_message);
        btSendError = findViewById(R.id.bt_send_error);

        editText.setText("");
        btSendError.setOnClickListener(this);
    }

    private void sendError(){
        String urlSendErrorMessage = MainPage.ipAdd+"/app/reporterror/?error="+editText.getText().toString().trim();

        JsonObjectRequest jsonObjectSaveData = new JsonObjectRequest
                (Request.Method.GET, urlSendErrorMessage, null, response -> {
                    Boolean isSuccessful = null;
                    try {
                        isSuccessful = response.getBoolean("successful");
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(isSuccessful!=null && isSuccessful){
                        editText.setText("");
                        if(isEnglish){
                            Toast.makeText(ErrorHandling.this, "Error submitted successfully.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(ErrorHandling.this, "Greska je uspjesno poslana.", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        if(isEnglish){
                            Toast.makeText(ErrorHandling.this, "Error was't submitted successfully.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(ErrorHandling.this, "Greska nije poslana. Pokusajte ponovo.", Toast.LENGTH_LONG).show();
                        }
                    }
                }, error -> {
                    if(isEnglish){
                        Toast.makeText(ErrorHandling.this, "Error has occurred.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(ErrorHandling.this, "Doslo je do greske", Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(ErrorHandling.this).add(jsonObjectSaveData);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_send_error){
            sendError();
        }
    }
}
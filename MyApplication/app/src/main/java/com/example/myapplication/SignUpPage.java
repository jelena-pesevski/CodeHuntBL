package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.model.User;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class SignUpPage extends AppCompatActivity {

    private EditText username;
    private Button registerButton;

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale=locale;

        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My language",lang);
        editor.apply();
    }

    public String loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = preferences.getString("My language","sr");
        setLocale(language);
        return language;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String lang = loadLocale();
        setContentView(R.layout.singup_page);

        username = findViewById(R.id.username);
        // Listen to EditText key event to change button state and text accordingly.
        username.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                // Get key action, up or down.
                int action = keyEvent.getAction();

                // Only process key up action, otherwise this listener will be triggered twice because of key down action.
                if(action == KeyEvent.ACTION_UP)
                {
                    registerButton.setEnabled(username.getText().toString() != null);
                }
                return false;
            }
        });

        EditText info = findViewById(R.id.infoText);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPage.this, Info.class);
                startActivity(intent);
            }
        });

        TextView back = findViewById(R.id.backTextView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpPage.this, MainPage.class);
                startActivity(intent);
            }
        });


        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.registerButton:
                        Intent i = getIntent();
                        String email = i.getStringExtra("currentEmail");
                        System.out.println(email + " " + username.getText().toString() + " " );
                        String sentLang = "";
                        if(lang.startsWith("e")) {
                            sentLang = "en";
                        } else {
                            sentLang = "srp";
                        }
                        String url2 = MainPage.ipAdd + "/app/addnewuser/?gmail=" + email + "&username=" + username.getText().toString() + "&startpatquestionid=1&language=" + sentLang;
                        System.out.println("URL ==== " + url2);
                        JsonObjectRequest jsonObjectRequestAddNewUser = new JsonObjectRequest
                                (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {

                                        Log.d("Odgovor MOJJJJ->", response.toString());
                                        Boolean isRegistered = null;
                                        int newUserId = -1;
                                        User currentUser = null;
                                        try {
                                            isRegistered = response.getBoolean("addedNewUser");
                                            newUserId = response.getInt("userId");
                                            currentUser = new User(email, username.getText().toString() , 0, newUserId, false);
                                            currentUser.setLanguage(lang);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        if(isRegistered != null) {
                                            if(lang.toLowerCase().startsWith("e")) {
                                                Toast.makeText(SignUpPage.this, "Registracija uspjesna.", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(SignUpPage.this, "Successful registration.", Toast.LENGTH_LONG).show();
                                            }
                                            username.setText("");
                                            Intent intent = new Intent(SignUpPage.this, FirstPage.class);
                                            intent.putExtra("currentUser", currentUser);
                                            startActivity(intent);
                                        }

                                        Log.d("Pokupio sa servera->", "Novi korisnik ima id = " + String.valueOf(newUserId));
                                        //isRegisteredCheck je sada true ili false i sad dalje sta god ..

                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // TODO: Handle error
                                        Toast.makeText(SignUpPage.this, "Neuspjesno.", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(SignUpPage.this, MainPage.class);
                                        startActivity(intent);
                                    }
                                });

                        Volley.newRequestQueue(SignUpPage.this).add(jsonObjectRequestAddNewUser);
                        break;
                }
            }
        });

    }
}

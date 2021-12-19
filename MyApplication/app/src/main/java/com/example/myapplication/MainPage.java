package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MainPage extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    Button loginButton;
    EditText signUpButton, moreInfo;
    int RC_SIGN_IN = 0;
    private static final String ERR = "Error";
    private static boolean isSignUp;
    public static String ipAdd = "http://dce46b908fa1.ngrok.io";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.main_page);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.loginButton:
                        isSignUp = false;
                        login();
                        break;
                }
            }
        });

        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.signUpButton:
                        isSignUp = true;
                        signUp();
                        break;
                }
            }
        });

        moreInfo = findViewById(R.id.moreInfoTextView);
        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Info.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLocale();
    }

    private void signUp() {
        Intent loginIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(loginIntent, RC_SIGN_IN);
    }

    private void login() {
        Intent loginIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(loginIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // If GoogleSignIn.getLastSignedInAccount returns a GoogleSignInAccount object (rather than null),
        // the user has already signed in to your app with Google. Update your UI accordingly—that is,
        // hide the sign-in button, launch your main activity, or whatever is appropriate for your app.

        // If GoogleSignIn.getLastSignedInAccount returns null, the user has not yet signed in to your app with Google.
        // Update your UI to display the Google Sign-in button.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (isSignUp) {
                final String currentEmail = account.getEmail();
                String url = ipAdd + "/app/checkifuserisregistered/?gmail=" + currentEmail;
                System.out.println("isAlreadyRegistered email = " + currentEmail);
                System.out.println("URL -> " + url);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Boolean isRegisteredCheck = null;
                                try {
                                    isRegisteredCheck = response.getBoolean("isRegistered");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (isRegisteredCheck != null) {
                                    // isAlreadyRegistered = isRegisteredCheck;
                                    Log.d("Pokupio sa SERVERA ->", "is registered = " + isRegisteredCheck.toString());
                                    Log.d("RESPONSE ->", response.toString());

                                    showToast(isRegisteredCheck, currentEmail);
                                }
                                //isRegisteredCheck je sada true ili false i sad dalje sta god ..
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                System.out.println(" ERROR " + error.toString());
                                //ovdje neki toast stavi kao neka greska je u pitanju, provjerite internet konekicju ili slicno
                                //mada moze se i provjeriti da li je internet konekcija ok, ali nije sad neophodno ...
                            }
                        });

                Volley.newRequestQueue(MainPage.this).add(jsonObjectRequest);
            } else {
                String currentEmail = null;
                if (account != null) {
                    currentEmail = account.getEmail();
                }
                String url = ipAdd + "/app/checkifuserisregistered/?gmail=" + currentEmail;
                System.out.println("isRegistered email = " + currentEmail);
                System.out.println("URL -> " + url);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, response -> {
                            Boolean isRegisteredCheck = null;
                            int userId, currentPathId, currentQuestionId, points;
                            boolean isRangListAllowed;
                            String userName, email, language="";
                            User currentUser = null;
                            try {
                                isRegisteredCheck = response.getBoolean("isRegistered");
                                userId = response.getInt("userId");
                                currentPathId = response.getInt("pathId");
                                currentQuestionId = response.getInt("questionId");
                                points = response.getInt("points");
                                isRangListAllowed = response.getBoolean("isRangListAllowed");
                                userName = response.getString("username");
                                language = response.getString("language");
                                email = response.getString("gmail");
                                currentUser = new User(email, userName, points, userId, isRangListAllowed, currentPathId, currentQuestionId);
                                currentUser.setLanguage(language);
                                System.out.println(response);

                                if(language.startsWith("s")){
                                    setLocale("srp");
                                }else{
                                    setLocale("en");
                                }
                                loadLocale();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (isRegisteredCheck != null) {
                                Log.d("Pokupio sa SERVERA ->", "is registered = " + isRegisteredCheck.toString());
                                Log.d("RESPONSE ->", response.toString());

                                if (isRegisteredCheck) {
                                    // Signed in successfully, show authenticated UI.
                                    signOut();
                                    if(language.startsWith("s") || language.startsWith("S")) {
                                        Toast.makeText(MainPage.this, "Prijava je uspjesna.", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MainPage.this, "Successful login.", Toast.LENGTH_LONG).show();
                                    }
                                    Intent intent = new Intent(MainPage.this, FirstPage.class);
                                    if (currentUser != null) {
                                        intent.putExtra("currentUser", currentUser);
                                    }
                                    startActivity(intent);
                                } else {
                                    if(language.startsWith("s") || language.startsWith("S")) {
                                        Toast.makeText(MainPage.this, "Prijava neuspjesna. Potrebno je da se registrujete.", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MainPage.this, "Unsuccessful login. You need to be registered.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            //isRegisteredCheck je sada true ili false i sad dalje sta god ..
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                System.out.println(" ERROR " + "****************************************************" + error.toString());
                                //ovdje neki toast stavi kao neka greska je u pitanju, provjerite internet konekicju ili slicno
                                //mada moze se i provjeriti da li je internet konekcija ok, ali nije sad neophodno ...
                            }
                        });

                Volley.newRequestQueue(MainPage.this).add(jsonObjectRequest);
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(ERR, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void showToast(Boolean isRegisteredCheck, String currentEmail) {
        if (isRegisteredCheck) {
            Toast.makeText(MainPage.this, currentEmail + " je vec registrovan.", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(MainPage.this, "Registracija je uspjesna.", Toast.LENGTH_LONG).show();
            signOut();
            Intent signupIntent = new Intent(this, SignUpPage.class);
            signupIntent.putExtra("currentEmail", currentEmail);
            startActivity(signupIntent);
        }
    }

    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(MainPage.this, "Signed out successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
    }

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

    public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = preferences.getString("My language","sr");
        setLocale(language);
    }

    @Override
    public void onBackPressed() {
        System.out.println("USLOOOOO U BACK");
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
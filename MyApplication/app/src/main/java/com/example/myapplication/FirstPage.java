package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.User;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class FirstPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    ImageButton playBtn;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.first_page);

        System.out.println("Usao uu ");
        //ovo je trenutni korisnik
        //System.out.println(getIntent().getParcelableExtra("currentUser").toString());
        User currentUser= getIntent().<User>getParcelableExtra("currentUser");
        System.out.println(currentUser);

        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView=(NavigationView) findViewById(R.id.nav_view);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        navigationView.bringToFront(); // <-
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_open );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this); // <-

        navigationView.setCheckedItem(R.id.nav_home); // <-
        playBtn=(ImageButton)findViewById(R.id.playBtn);

        if(currentUser.getLanguage().toLowerCase().startsWith("e")){
            playBtn.setImageResource(R.drawable.igraj_eng);
        }else{
            playBtn.setImageResource(R.drawable.igraj_srp);
        }

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ovdje poslati zahtjev za pitanje i dohvatiti sledece pitanje za trenutno prijavljenog korisnika
                //kreirati novo pitanje
              //  Question nextQuestion=null;
                Log.d("onclick->", "usao");
                String url= MainPage.ipAdd+"/app/geteuserlastquestion/?userid="+currentUser.getUserId();
                System.out.println(url);
                JsonObjectRequest jsonObjectGetQuestionID = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Odgovor ZA ID PITANJA->", response.toString());
                                Boolean isSuccessful = null;
                                int pathId = -1;
                                int questionId=-1;
                                try {
                                    isSuccessful = response.getBoolean("successful");
                                    pathId = response.getInt("pathid");
                                    questionId = response.getInt("questionid");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if(isSuccessful !=null && isSuccessful.booleanValue()==true) {
                                    //zahtjev za pitanjem sa datim id-jem/

                                    currentUser.setCurrentPathId(pathId);
                                    currentUser.setCurrentQuestionId(questionId);
                                    String urlQuest=MainPage.ipAdd+"/app/getnextquestion/?pathid="+pathId+"&previousquestionid="+questionId+"&userid="+currentUser.getUserId();
                                    JsonObjectRequest jsonObjectGetQuestion = new JsonObjectRequest
                                            (Request.Method.GET, urlQuest, null, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    Log.d("Dobijeno pitanje->", response.toString());
                                                    Boolean isSuccessful2 = null;
                                                    int id=-1;
                                                    String text=null;
                                                    String answer=null;
                                                    Integer points=null;
                                                    String questionHelpFirst=null;
                                                    String questionHelpSecond=null;
                                                    String qrCode=null;
                                                    String locationHint=null;
                                                    String locationName=null;
                                                    String locationLon=null;
                                                    String locationLat=null;
                                                    String city=null;
                                                    boolean usedFirstHelp=false, usedScndHelp=false;
                                                    try {
                                                        isSuccessful2 = response.getBoolean("successful");
                                                    }catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    if(isSuccessful2 !=null && isSuccessful2.booleanValue()==true) {
                                                        try{
                                                            id=response.getInt("questionId");
                                                            text=response.getString("questionText");
                                                            answer=response.getString("questionAnswer");
                                                            points=response.getInt("points");
                                                            questionHelpFirst=response.getString("questionHelpFirst");
                                                            questionHelpSecond=response.getString("questionHelpSecond");
                                                            qrCode=response.getString("qrCode");
                                                            locationHint=response.getString("locationHint");
                                                            locationName=response.getString("locationName");
                                                            locationLon=response.getString("locationLon");
                                                            locationLat=response.getString("locationLat");
                                                            city=response.getString("city");
                                                            usedFirstHelp=response.getBoolean("questionHelpFirstUsed");
                                                            usedScndHelp=response.getBoolean("questionHelpSecondUsed");
                                                        }catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                        //kreiramo pitanje od parametara
                                                        Question currentQuestion=new Question(id, text, answer,points, questionHelpFirst, questionHelpSecond, qrCode,
                                                                locationHint, locationName, locationLon, locationLat, city, usedFirstHelp, usedScndHelp);

                                                        //prebaciti na stranicu sa hintom i prethodno upisati lokaciju u textView na toj stranici
                                                        Log.d("location page->", "dohvaceno pitanje");
                                                        Intent intent=new Intent(FirstPage.this, LocationHintPage.class);
                                                        intent.putExtra("currentUser", currentUser);
                                                        intent.putExtra("currentQuestion", currentQuestion);
                                                        startActivity(intent);

                                                    }else{
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(FirstPage.this,R.style.AlertDialogCustom));
                                                        if(currentUser.getLanguage().toLowerCase().startsWith("e")){
                                                            builder.setMessage("You have successfully answered all questions! New questions are coming soon...");
                                                        }else{
                                                            builder.setMessage("Uspjesno ste odgovorili na sva pitanja! Nova pitanja stizu uskoro...");
                                                        }
                                                        builder.setCancelable(false)
                                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                        dialog.cancel();
                                                                    }
                                                                });
                                                        AlertDialog alert = builder.create();
                                                        alert.show();
                                                    }
                                                }
                                            },new Response.ErrorListener() {

                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // TODO: Handle error
                                                    System.out.println("Error response 140");
                                                    //ovdje neki toast stavi kao neka greska je u pitanju, provjerite internet konekicju ili slicno
                                                    //mada moze se i provjeriti da li je internet konekcija ok, ali nije sad neophodno ...
                                                }
                                            });
                                    Volley.newRequestQueue(FirstPage.this).add(jsonObjectGetQuestion);

                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {

                                System.out.println("Error response 151");
                                // TODO: Handle error
                                //ovdje neki toast stavi kao neka greska je u pitanju, provjerite internet konekicju ili slicno
                                //mada moze se i provjeriti da li je internet konekcija ok, ali nije sad neophodno ...
                            }
                        });

                Volley.newRequestQueue(FirstPage.this).add(jsonObjectGetQuestionID);

            }
        });

    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        User currentUser= getIntent().<User>getParcelableExtra("currentUser");
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_ranking:
                Intent rankIntent = new Intent(FirstPage.this, RankList.class);
                rankIntent.putExtra("currentUser", currentUser);
                startActivity(rankIntent);
                break;
            case R.id.nav_map:
                Intent mapIntent = new Intent(FirstPage.this, Map.class);
                mapIntent.putExtra("currentUser", currentUser);
                startActivity(mapIntent);
                break;
            case R.id.nav_solved:
                Intent solvedIntent = new Intent(FirstPage.this, SolvedQuestions.class);
                solvedIntent.putExtra("currentUser", currentUser);
                startActivity(solvedIntent);
                break;
            case R.id.nav_error:
                Intent errorIntent = new Intent(FirstPage.this, ErrorHandling.class);
                errorIntent.putExtra("currentUser", currentUser);
                startActivity(errorIntent);
                break;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(FirstPage.this, Settings.class);
                settingsIntent.putExtra("currentUser", currentUser);
                startActivity(settingsIntent);
                break;
            case R.id.nav_info:
                Intent infoIntent = new Intent(FirstPage.this, Info.class);
                startActivity(infoIntent);
                break;
            case R.id.nav_instructions:
                Intent instructionsIntent=new Intent(FirstPage.this, Instructions.class);
                startActivity(instructionsIntent);
                break;
            case R.id.nav_logout:
                Intent intent=new Intent(FirstPage.this, MainPage.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}

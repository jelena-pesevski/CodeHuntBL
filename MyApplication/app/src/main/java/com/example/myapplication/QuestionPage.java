package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class QuestionPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ImageButton hintBtn1;
    ImageButton hintBtn2;
    Button checkAnswerBtn;
    TextView questionTxt;
    EditText answer;
    User currentUser;
    Question currentQuestion;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_page);

        Log.d("question page->", "kreiran");
        //ovo je trenutno pitanje
        System.out.println(getIntent().getParcelableExtra("currentQuestion").toString());
        System.out.println(getIntent().getParcelableExtra("currentUser").toString());
        currentQuestion=getIntent().getParcelableExtra("currentQuestion");
        currentUser= getIntent().getParcelableExtra("currentUser");

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

        questionTxt=(TextView)findViewById(R.id.questionTxt);
        questionTxt.setText(currentQuestion.getText());

        hintBtn1=(ImageButton)findViewById(R.id.firstHintBtn);
        hintBtn2=(ImageButton)findViewById(R.id.secondHintBtn);
        checkAnswerBtn=(Button) findViewById(R.id.checkAnswerBtn);
        answer=(EditText)findViewById(R.id.answer);

        hintBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentQuestion.isUsedFirstHelp()){
                   //ako nije koristeno sad smanji bodove
                    updatePointsHelpUsed((int)(currentQuestion.getPoints()*0.1));
                    //obavijesti server
                    reportHintUsage(1);
                }
                showHint(1);
                currentQuestion.setUsedFirstHelp(true);
            }
        });

        hintBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentQuestion.isUsedScndHelp()){
                    updatePointsHelpUsed((int)(currentQuestion.getPoints()*0.2));
                    reportHintUsage(2);
                }
                showHint(2);
                currentQuestion.setUsedScndHelp(true);
            }
        });

        checkAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input=answer.getText().toString();
                if(currentQuestion.getAnswer().equalsIgnoreCase(input)){
                    Integer pointsGained=currentQuestion.getPoints();

                    if(currentUser.getLanguage().toLowerCase().startsWith("e")){
                        Toast.makeText(QuestionPage.this, "Correct answer. + " + pointsGained +" points", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(QuestionPage.this, "Tacan odgovor. + " + pointsGained +" poena", Toast.LENGTH_SHORT).show();
                    }
                    //salji serveru za bodove
                    String urlUpdatePoints = MainPage.ipAdd + "/app/updateuserpoint/?userid="+currentUser.getUserId()+"&points="+(currentUser.getPoints()+pointsGained);
                    JsonObjectRequest jsonObjectUpdatePoints = new JsonObjectRequest
                            (Request.Method.GET, urlUpdatePoints, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    Log.d("Odgovor BODOVI->", response.toString());
                                    Boolean isUpdated = null;

                                    try {
                                        isUpdated = response.getBoolean("updated");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if(isUpdated!=null && isUpdated.booleanValue()==true){
                                        Log.i("info->", "bodovi uspjesno azurirani");
                                        currentUser.setPoints(currentUser.getPoints()+pointsGained);
                                    }else if(isUpdated.booleanValue()==true){
                                        Log.i("info->", "nije uspio update na serveru za tacan odgovor");
                                        if(currentUser.getLanguage().toLowerCase().startsWith("e")){
                                            Toast.makeText(QuestionPage.this, "Points are not updated successfully.", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(QuestionPage.this, "Bodovi nisu uspjesno azurirani na serveru.", Toast.LENGTH_LONG).show();
                                        }

                                    }

                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    //ovdje neki toast stavi kao neka greska je u pitanju, provjerite internet konekicju ili slicno
                                    //mada moze se i provjeriti da li je internet konekcija ok, ali nije sad neophodno ...
                                }
                            });

                    Volley.newRequestQueue(QuestionPage.this).add(jsonObjectUpdatePoints);
                    //reset koristenje pomoci
                    resetHelpUsage();
                    // sada zahtjev za novo pitanje
                    saveUserData();
                    getNextQuestion();

                }else{
                    answer.getText().clear();
                    if(currentUser.getLanguage().toLowerCase().startsWith("e")){
                        Toast.makeText(QuestionPage.this, "Incorrect answer.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(QuestionPage.this, "Netacan odgovor.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    private void saveUserData(){
        //cuvamo zadnje pitanje na koje je tacno odgovorio
        String urlSaveData=MainPage.ipAdd+"/app/saveuserdata/?userid="+currentUser.getUserId()+"&patthid="+currentUser.getCurrentPathId()
                +"&questionid="+currentQuestion.getId();
        System.out.println(urlSaveData);
        JsonObjectRequest jsonObjectSaveData = new JsonObjectRequest
                (Request.Method.GET, urlSaveData, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Update state->", response.toString());
                        Boolean isSuccessful = null;

                        try {
                            isSuccessful = response.getBoolean("successful");
                        }catch (JSONException e) {
                            e.printStackTrace();
                            //nije updejtovano stanje
                        }
                        if(isSuccessful!=null && isSuccessful.booleanValue()==true){
                            currentUser.setCurrentQuestionId(currentQuestion.getId());
                            Log.i("data saved->", "podaci o trenutnom pitanju sacuvani");
                        }else{
                            Log.i("data saved->", "podaci o trenutnom pitanju nisu sacuvani");
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
        Volley.newRequestQueue(QuestionPage.this).add(jsonObjectSaveData);
    }

    private void getNextQuestion(){
        String urlQuest=MainPage.ipAdd+"/app/getnextquestion/?pathid="+currentUser.getCurrentPathId()+"&previousquestionid="+currentQuestion.getId()+"&userid="+currentUser.getUserId();
        System.out.println(urlQuest);
            JsonObjectRequest jsonObjectGetQuestion = new JsonObjectRequest
                    (Request.Method.GET, urlQuest, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("Dobijeno pitanje->", response.toString());
                            Boolean isSuccessful = null;
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
                                isSuccessful = response.getBoolean("successful");
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(isSuccessful !=null && isSuccessful.booleanValue()==true) {
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
                                Question nextQuestion=new Question(id, text, answer,points, questionHelpFirst, questionHelpSecond, qrCode,
                                        locationHint, locationName, locationLon, locationLat, city, usedFirstHelp, usedScndHelp);

                                //prebaciti na stranicu sa hintom i prethodno upisati lokaciju u textView na toj stranici
                                Log.i("location page->", "dohvaceno pitanje");
                                Intent intent=new Intent(QuestionPage.this, LocationHintPage.class);
                                intent.putExtra("currentUser", currentUser);
                                intent.putExtra("currentQuestion", nextQuestion);
                                startActivity(intent);

                            }else{

                                String urlChangePath=MainPage.ipAdd+"/app/saveuserendpath/?userid="+currentUser.getUserId()+"&pathid="+currentUser.getCurrentPathId();
                                System.out.println(urlChangePath);

                                JsonObjectRequest jsonChangePath = new JsonObjectRequest
                                        (Request.Method.GET, urlChangePath, null, new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                Log.d("Change path->", response.toString());
                                                Boolean isSuccess = null;

                                                try {
                                                    isSuccess = response.getBoolean("successful");
                                                }catch (JSONException e) {
                                                    e.printStackTrace();
                                                    //nije updejtovano stanje
                                                }
                                                if(isSuccess!=null && isSuccess.booleanValue()==true){


                                                    //ima novih putanja
                                                    Log.i("info->", "ima novih putanja");
                                                    if(currentUser.getLanguage().toLowerCase().startsWith("e")){
                                                        showDialog("Congratulations! You have successfully answered all questions on path number "+currentUser.getCurrentPathId()+". Game continues with next path!");
                                                    }else{
                                                        showDialog("Čestitamo! Uspjesno ste odgovorili na sva pitanja sa putanje broj "+currentUser.getCurrentPathId() +". Nastavljate sa sledećom putanjom!");
                                                    }

                                                    currentUser.setCurrentPathId(currentUser.getCurrentPathId()+1);
                                                    currentUser.setCurrentQuestionId(-1);

                                                    //sada novi zahtjev za novo pitanj
                                                    String urlQuest=MainPage.ipAdd+"/app/getnextquestion/?pathid="+currentUser.getCurrentPathId()+"&previousquestionid="+
                                                            currentUser.getCurrentQuestionId()+"&userid="+currentUser.getUserId();
                                                    System.out.println(urlQuest);
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
                                                                        Intent intent=new Intent(QuestionPage.this, LocationHintPage.class);
                                                                        System.out.println("user prije prelaska na novu utanju:"+currentQuestion);
                                                                        System.out.println("pitanje prije prelaska "+currentQuestion);
                                                                        intent.putExtra("currentUser", currentUser);
                                                                        intent.putExtra("currentQuestion", currentQuestion);
                                                                        startActivity(intent);
                                                                    }else{
                                                                        if(currentUser.getLanguage().toLowerCase().startsWith("e")){
                                                                            showDialog("Congratulations! You have successfully answered all questions");
                                                                        }else{
                                                                            showDialog( "Cestitamo! Uspjesno ste odgovorili na sva pitanja!");
                                                                        }

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
                                                    Volley.newRequestQueue(QuestionPage.this).add(jsonObjectGetQuestion);

                                                }else{
                                                    Log.i("info->", "nema nova putanja");

                                                    if(currentUser.getLanguage().toLowerCase().startsWith("e")){
                                                        showDialog("Congratulations! You have successfully answered all questions on path number "+currentUser.getCurrentPathId());
                                                    }else{
                                                        showDialog("Čestitamo! Uspjesno ste odgovorili na sva pitanja sa putanje broj "+currentUser.getCurrentPathId());
                                                    }
                                                    //prebaci na pocetnu
                                                    Intent intent=new Intent(QuestionPage.this, FirstPage.class);
                                                    intent.putExtra("currentUser", currentUser);
                                                    startActivity(intent);
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
                                Volley.newRequestQueue(QuestionPage.this).add(jsonChangePath);
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
            Volley.newRequestQueue(QuestionPage.this).add(jsonObjectGetQuestion);

    }


    private void resetHelpUsage(){
        String url=MainPage.ipAdd+"/app/resetuserusedhelp/?userid="+currentUser.getUserId();
        System.out.println(url);
        JsonObjectRequest jsonObject = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Help reset->", response.toString());
                        Boolean isCorrect = null;

                        try {
                            isCorrect = response.getBoolean("isCorrect");
                        }catch (JSONException e) {
                            e.printStackTrace();
                            //nije updejtovano stanje
                        }
                        if(isCorrect!=null && isCorrect.booleanValue()==true){
                            Log.i("info->", "uspjesno resetovana pomoc");
                        }else{
                            Log.i("info->", "neuspjesno resetovana pomoc");
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
        Volley.newRequestQueue(QuestionPage.this).add(jsonObject);
    }

    private void showHint(int hintNum){
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.AlertDialogCustom));
        builder.setMessage(currentQuestion.getHelp(hintNum))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void reportHintUsage(int hintNum){
        //obavijesti server o koristenju pomoci i prikazi pomoc

        String url=MainPage.ipAdd+"/app/saveuserusehelp/?userid="+currentUser.getUserId()+"&helpid="+hintNum;
        System.out.println(url);
        JsonObjectRequest jsonObject = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("H usage saved->", response.toString());
                        Boolean isCorrect = null;

                        try {
                            isCorrect = response.getBoolean("isCorrect");
                        }catch (JSONException e) {
                            e.printStackTrace();
                            //nije updejtovano stanje
                        }
                        if(isCorrect!=null && isCorrect.booleanValue()==true){
                            Log.i("info->", "uspjesno javljeno serveru da je koristena pomoc");
                        }else{
                            Log.i("info->", "neuspjesno javljeno serveru da je koristena pomoc");
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
        Volley.newRequestQueue(QuestionPage.this).add(jsonObject);
    }

    private void updatePointsHelpUsed(int decrement){
        //salji serveru za bodove
        String urlUpdatePoints = MainPage.ipAdd + "/app/updateuserpoint/?userid="+currentUser.getUserId()+"&points="+(currentUser.getPoints()-decrement);
        JsonObjectRequest jsonObjectUpdatePoints = new JsonObjectRequest
                (Request.Method.GET, urlUpdatePoints, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Odgovor BODOVI->", response.toString());
                        Boolean isUpdated = null;

                        try {
                            isUpdated = response.getBoolean("updated");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(isUpdated!=null && isUpdated.booleanValue()==true){
                            Log.i("info->", "uspjesno umanjeni bodovi na serveru");

                            currentUser.setPoints(currentUser.getPoints()-decrement);
                            Toast.makeText(QuestionPage.this, "-"+decrement+ " bodova", Toast.LENGTH_LONG);

                        }else if(isUpdated.booleanValue()==true){
                            Log.d("updatePoints bad", response.toString());
                            Toast.makeText(QuestionPage.this, "Bodovi nisu uspjesno azurirani na serveru.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //ovdje neki toast stavi kao neka greska je u pitanju, provjerite internet konekicju ili slicno
                        //mada moze se i provjeriti da li je internet konekcija ok, ali nije sad neophodno ...
                    }
                });

        Volley.newRequestQueue(QuestionPage.this).add(jsonObjectUpdatePoints);
    }

    @Override
    public void onBackPressed()
    {
        //prebaci na pocetnu
        Intent intent=new Intent(QuestionPage.this, FirstPage.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        User currentUser= getIntent().<User>getParcelableExtra("currentUser");
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_ranking:
                Intent rankIntent = new Intent(QuestionPage.this, RankList.class);
                rankIntent.putExtra("currentUser", currentUser);
                startActivity(rankIntent);
                break;
            case R.id.nav_map:
                Intent mapIntent = new Intent(QuestionPage.this, Map.class);
                mapIntent.putExtra("currentUser", currentUser);
                startActivity(mapIntent);
                break;
            case R.id.nav_solved:
                Intent solvedIntent = new Intent(QuestionPage.this, SolvedQuestions.class);
                solvedIntent.putExtra("currentUser", currentUser);
                startActivity(solvedIntent);
                break;
            case R.id.nav_error:
                Intent errorIntent = new Intent(QuestionPage.this, ErrorHandling.class);
                errorIntent.putExtra("currentUser", currentUser);
                startActivity(errorIntent);
                break;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(QuestionPage.this, Settings.class);
                settingsIntent.putExtra("currentUser", currentUser);
                startActivity(settingsIntent);
                break;
            case R.id.nav_info:
                Intent infoIntent = new Intent(QuestionPage.this, Info.class);
                infoIntent.putExtra("currentUser", currentUser);
                startActivity(infoIntent);
                break;
            case R.id.nav_instructions:
                Intent instructionsIntent=new Intent(QuestionPage.this, Instructions.class);
                instructionsIntent.putExtra("currentUser", currentUser);
                startActivity(instructionsIntent);
                break;
            case R.id.nav_logout:
                Intent intent=new Intent(QuestionPage.this, MainPage.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialog(String msg){
        //ovaj Handler mora postojati da bi korisnik imao vremena da procita poruku u Dialog prozoru, jer je dialog asinhron
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message mesg) {
                throw new RuntimeException();
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.AlertDialogCustom));
        builder.setMessage(msg);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        handler.sendMessage(handler.obtainMessage());
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        try{ Looper.loop(); }
        catch(RuntimeException e){}
    }


}

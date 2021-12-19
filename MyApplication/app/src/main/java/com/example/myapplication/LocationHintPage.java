package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import com.example.myapplication.model.Question;
import com.example.myapplication.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class LocationHintPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ImageButton scanBtn;
    TextView locationHint;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_scan_page);

       //ovo je trenutno pitanje
        System.out.println(getIntent().getParcelableExtra("currentQuestion").toString());
         Question currentQuestion=getIntent().getParcelableExtra("currentQuestion");

        locationHint=(TextView)findViewById(R.id.locationHint);
        locationHint.setText(currentQuestion.getLocationHint());

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

        scanBtn=(ImageButton)findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("skeniranje->", "otvoren skener");
                IntentIntegrator intent=new IntentIntegrator(LocationHintPage.this);

                intent.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intent.setOrientationLocked(true);
                intent.setBeepEnabled(false);
                intent.setPrompt("Koristite dugme za pojacavanje kao blic");
                intent.setCaptureActivity(Capture.class);
                intent.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode, resultCode,data );
        if(intentResult.getContents()!=null){
            Question currentQuestion=getIntent().getParcelableExtra("currentQuestion");
            User currentUser= getIntent().<User>getParcelableExtra("currentUser");
            String currentQR=currentQuestion.getQrCode();

            //ako je uspjesan prebaci na stranicu Question Page
            String result=intentResult.getContents();
            if(currentQR.equals(result)){
                Log.d("dobar kod->", "prelazak na question page");
                Intent intent=new Intent(LocationHintPage.this, QuestionPage.class);
                intent.putExtra("currentUser", currentUser);
                intent.putExtra("currentQuestion", currentQuestion);
                startActivity(intent);
            }else{
                AlertDialog.Builder builder=new AlertDialog.Builder(
                        new ContextThemeWrapper(this,R.style.AlertDialogCustom)
                );
                if(currentUser.getLanguage().toLowerCase().startsWith("e")){
                    builder.setTitle("Scanning result:");
                    builder.setMessage("QR code is scanned, but not the one you need.");
                }else{
                    builder.setTitle("Rezultat skeniranja:");
                    builder.setMessage("Kod je skeniran, ali nije odgovarajuci.");
                }

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        }else{
            if(getIntent().<User>getParcelableExtra("currentUser").getLanguage().toLowerCase().startsWith("e")){
                Toast.makeText(getApplicationContext(), " QRCode is not successfully scanned. " , Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), " Kod nije uspjesno skeniran" , Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed()
    {
        //prebaci na pocetnu
        Intent intent=new Intent(LocationHintPage.this, FirstPage.class);
        User user=getIntent().getParcelableExtra("currentUser");
        System.out.println("Iz location hint povratak za usera:"+ user);
        intent.putExtra("currentUser", user);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        User currentUser= getIntent().<User>getParcelableExtra("currentUser");
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_ranking:
                Intent rankIntent = new Intent(LocationHintPage.this, RankList.class);
                rankIntent.putExtra("currentUser", currentUser);
                startActivity(rankIntent);
                break;
            case R.id.nav_map:
                Intent mapIntent = new Intent(LocationHintPage.this, Map.class);
                mapIntent.putExtra("currentUser", currentUser);
                startActivity(mapIntent);
                break;
            case R.id.nav_solved:
                Intent solvedIntent = new Intent(LocationHintPage.this, SolvedQuestions.class);
                solvedIntent.putExtra("currentUser", currentUser);
                startActivity(solvedIntent);
                break;
            case R.id.nav_error:
                Intent errorIntent = new Intent(LocationHintPage.this, ErrorHandling.class);
                errorIntent.putExtra("currentUser", currentUser);
                startActivity(errorIntent);
                break;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(LocationHintPage.this, Settings.class);
                settingsIntent.putExtra("currentUser", currentUser);
                startActivity(settingsIntent);
                break;
            case R.id.nav_info:
                Intent infoIntent = new Intent(LocationHintPage.this, Info.class);
                infoIntent.putExtra("currentUser", currentUser);
                startActivity(infoIntent);
                break;
            case R.id.nav_instructions:
                Intent instructionsIntent=new Intent(LocationHintPage.this, Instructions.class);
                instructionsIntent.putExtra("currentUser", currentUser);
                startActivity(instructionsIntent);
                break;
            case R.id.nav_logout:
                Intent intent=new Intent(LocationHintPage.this, MainPage.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}

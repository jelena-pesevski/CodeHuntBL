package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.interfaces.ServerCallback;
import com.example.myapplication.maps.MapFragment;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Map extends AppCompatActivity {

    ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        User currentUser = getIntent().getParcelableExtra("currentUser");

        questions = new ArrayList<>();
        getQuestions(new ServerCallback() {

            @Override
            public void onSuccess() {

                // Initialize fragment
                Fragment fragment = new MapFragment(questions, currentUser);

                // Open fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .commit();

            }
        }, currentUser.getCurrentPathId(),currentUser.getCurrentQuestionId(), currentUser.getUserId());


    }

    private void getQuestions(final ServerCallback callBack, int pathId, int currentQuestionId, int userid){


        //dodati path id trenutnog korisnika, dobijen kao intent
        String url = MainPage.ipAdd +"/app/getallansweredquestionsfromselectedpath/?pathid="+pathId+"&lastansweresquestionid="+currentQuestionId+"&userid="+userid;
        JsonObjectRequest jsonObjectRequestGetRankingList = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Odgovor SOLVED->", response.toString());
                        try {
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

                            JSONArray jsonArray = response.getJSONArray("questions");
                            for (int i = 0, size = jsonArray.length(); i < size; i++)
                            {
                                JSONObject objectInArray = jsonArray.getJSONObject(i);
                                id=objectInArray.getInt("questionId");
                                text=objectInArray.getString("questionText");
                                answer=objectInArray.getString("questionAnswer");
                                points=objectInArray.getInt("points");
                                questionHelpFirst=objectInArray.getString("questionHelpFirst");
                                questionHelpSecond=objectInArray.getString("questionHelpSecond");
                                qrCode=objectInArray.getString("qrCode");
                                locationHint=objectInArray.getString("locationHint");
                                locationName=objectInArray.getString("locationName");
                                locationLon=objectInArray.getString("locationLon");
                                locationLat=objectInArray.getString("locationLat");
                                city=objectInArray.getString("city");

                                Question currentQuestion=new Question(id, text, answer,points, questionHelpFirst, questionHelpSecond, qrCode,
                                        locationHint, locationName, locationLon, locationLat, city);
                                //trenutno ubacuje sva pitanja
                                questions.add(currentQuestion);
                            }
                            for(Question q: questions){
                                System.out.println(q);
                            }

                            callBack.onSuccess();
                        } catch (JSONException e) {
                            e.printStackTrace();
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

        Volley.newRequestQueue(Map.this).add(jsonObjectRequestGetRankingList);
    }

}
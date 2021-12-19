package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.adapters.QuestionAdapter;
import com.example.myapplication.interfaces.ServerCallback;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SolvedQuestions extends AppCompatActivity {

    ArrayList<Question> questions;
    ImageView questionsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solved_questions);

        RecyclerView rvQuestions = (RecyclerView) findViewById(R.id.rvQuestions);
        questionsImage = (ImageView) findViewById(R.id.questionsImage);
        User currentUser= getIntent().<User>getParcelableExtra("currentUser");
        System.out.println(currentUser);

        if(currentUser.getLanguage().toLowerCase().startsWith("e")){
            questionsImage.setImageResource(R.drawable.solved);
        }else{
            questionsImage.setImageResource(R.drawable.rijesena);
        }

        // treba povuci pitanja i staviti u questions
        questions = new ArrayList<>();
        getQuestions(new ServerCallback() {

            @Override
            public void onSuccess() {

                //ovdje mora biti kod za adaptaviju jer se ovo izvrsava kada se dobiju pitanja od seervera
                QuestionAdapter adapter = new QuestionAdapter(questions);
                rvQuestions.setAdapter(adapter);
                rvQuestions.setLayoutManager(new LinearLayoutManager(SolvedQuestions.this));

            }
        }, currentUser.getCurrentPathId(),currentUser.getCurrentQuestionId(), currentUser.getUserId());


    }

    private void getQuestions(final ServerCallback callBack, int pathId, int currentQuestionId, int userid){
        //dodati path id trenutnog korisnika, dobijen kao intent
        String url = MainPage.ipAdd +"/app/getallansweredquestionsfromselectedpath/?pathid="+pathId+"&lastansweresquestionid="+currentQuestionId+"&userid="+userid;
        System.out.println(url);

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

        Volley.newRequestQueue(SolvedQuestions.this).add(jsonObjectRequestGetRankingList);
    }
}
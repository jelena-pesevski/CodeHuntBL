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
import com.example.myapplication.adapters.UsersAdapter;
import com.example.myapplication.interfaces.ServerCallback;
import com.example.myapplication.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RankList extends AppCompatActivity {

    ArrayList<User> users;
    ImageView rankings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);

        RecyclerView rvUsers = (RecyclerView) findViewById(R.id.rvUsers);
        rankings=(ImageView)findViewById(R.id.rankings);
        User currentUser= getIntent().<User>getParcelableExtra("currentUser");

        if(currentUser.getLanguage().toLowerCase().startsWith("e")){
            rankings.setImageResource(R.drawable.rankings);
        }else{
            rankings.setImageResource(R.drawable.rang_lista);
        }

        // samo User-e staviti u "users" i trebalo bi da radi

        users=new ArrayList<>();
        getUsers(new ServerCallback() {

            @Override
            public void onSuccess() {

                //ovdje mora biti kod za adaptaviju jer se ovo izvrsava kada se dobije rang lista od servera
                UsersAdapter adapter = new UsersAdapter(users);
                rvUsers.setAdapter(adapter);
                rvUsers.setLayoutManager(new LinearLayoutManager(RankList.this));

            }
            });
    }

    private void getUsers(final ServerCallback callBack){
        String url = MainPage.ipAdd +"/app/getrankinglist/";
        JsonObjectRequest jsonObjectRequestGetRankingList = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Odgovor RANKINGLIST->", response.toString());
                        try {
                            JSONArray jsonArray = response.getJSONArray("rankingList");
                            for (int i = 0, size = jsonArray.length(); i < size; i++)
                            {
                                JSONObject objectInArray = jsonArray.getJSONObject(i);
                                String userName=objectInArray.getString("username");
                                Integer numOfPoints=objectInArray.getInt("points");
                                User u=new User(userName, numOfPoints);
                                users.add(u);
                            }

                            for(User u : users){
                                System.out.println(u.getUserName()+ " "+u.getPoints());
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

        Volley.newRequestQueue(RankList.this).add(jsonObjectRequestGetRankingList);
    }
}
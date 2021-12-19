package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Settings extends PreferenceActivity {
    User currUser;

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
        addPreferencesFromResource(R.xml.settings);
        getListView().setBackground(getResources().getDrawable(R.drawable.settings_style));

        currUser = getIntent().<User>getParcelableExtra("currentUser");
        SwitchPreference rankingPref = (SwitchPreference) findPreference("ranking");
        EditTextPreference username = (EditTextPreference) findPreference("username");
        Preference email = (Preference) findPreference("email");
        ListPreference language = (ListPreference) findPreference("language");
        final boolean[] userOption = new boolean[1];
        final String[] currentUsername = new String[1];

        // Podesavanje opcije za rangiranje
        String url1 = MainPage.ipAdd + "/app/getuserdataadditional/?userid=" + currUser.getUserId();
        JsonObjectRequest jsonObjectRequestAddNewUser = new JsonObjectRequest
                (Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Odgovor MOJJJJ->", response.toString());
                        try {
                            userOption[0] = response.getBoolean("isRangListAllowed");
                            rankingPref.setChecked(userOption[0]);
                            username.setSummary(response.getString("username"));
                            currentUsername[0] = response.getString("username");
                            username.setText(currentUsername[0]);
                            email.setSummary(response.getString("gmail"));
                            String lang = response.getString("language");
                            if ("en".equals(lang)) {
                                System.out.println("JEZIK = " + lang);
                                language.setSummary("English");
                                CharSequence[] entries = {"Serbian", "English"};
                                language.setEntries(entries);
                                currUser.setLanguage("eng");
                            } else {
                                System.out.println("JEZIK = " + lang);
                                language.setSummary("Srpski");
                                CharSequence[] entries = {"Srpski", "Engleski"};
                                language.setEntries(entries);
                                currUser.setLanguage("srp");
                            }
                            System.out.println("UNUTRA userOption = " + userOption[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(Settings.this, "Neuspjesna izmjena.", Toast.LENGTH_LONG).show();
                    }
                });
        Volley.newRequestQueue(Settings.this).add(jsonObjectRequestAddNewUser);

        rankingPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                System.out.println("USLOOOOO");
                boolean option = !rankingPref.isChecked();
                System.out.println("Opcija za rangiranje --> " + option);
                currUser.setRangListAllowed(option);
                String url = MainPage.ipAdd + "/app/chengerankinglistoption/?userid=" + currUser.getUserId() + "&setonrankinglist=" + option;
                System.out.println("URLLLLLL -> " + url);

                JsonObjectRequest jsonObjectRequestAddNewUser = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Odgovor MOJJJJ->", response.toString());

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                Toast.makeText(Settings.this, "Neuspjesna izmjena.", Toast.LENGTH_LONG).show();
                            }
                        });

                Volley.newRequestQueue(Settings.this).add(jsonObjectRequestAddNewUser);
                System.out.println("Userrrr UNUTRA -> " + currUser.isRangListAllowed());
                return true;
            }
        });

        // Podesavanje korisnickog imena
        username.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                System.out.println("USERNAME " + currentUsername[0]);
                username.setText(currentUsername[0]);
                Editable input = username.getEditText().getText();
                String url = MainPage.ipAdd + "/app/chengeusername/?userid=" + currUser.getUserId() + "&newusername=" + input.toString();
                System.out.println("URLLLLLL -> " + url);

                JsonObjectRequest jsonObjectRequestAddNewUser = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Odgovor MOJJJJ->", response.toString());

                                try {
                                    if (!response.getBoolean("successful")) {
                                        Toast.makeText(Settings.this, "" + response.getString("exceptionMessage"), Toast.LENGTH_LONG).show();
                                        username.setText(currentUsername[0]);
                                        username.setText(input.toString());
                                    } else {
                                        username.setSummary(input.toString());
                                        username.setText(input.toString());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                Toast.makeText(Settings.this, "" + error, Toast.LENGTH_LONG).show();
                            }
                        });

                Volley.newRequestQueue(Settings.this).add(jsonObjectRequestAddNewUser);
                return true;
            }
        });

        // Podesavanje jezika
        language.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                System.out.println("IZABRANI JEZIK = " + newValue);
                currUser.setLanguage(newValue.toString());
                String option = "";
                if ("srpski".equals(newValue)) {
                    option = "srp";
                    language.setSummary("Srpski");
                    setLocale("sr"); // postavljanje jezika
                    recreate();
                    CharSequence[] entries = {"Srpski", "Engleski"};
                    language.setEntries(entries);
                } else {
                    option = "en";
                    language.setSummary("English");
                    setLocale("en");
                    recreate();
                    CharSequence[] entries = {"Serbian", "English"};
                    language.setEntries(entries);
                }
                String url = MainPage.ipAdd + "/app/chengeuserlanguage/?userid=" + currUser.getUserId() + "&newlanguage=" + option;
                System.out.println("URLLLLLL -> " + url);

                JsonObjectRequest jsonObjectRequestAddNewUser = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Odgovor MOJJJJ->", response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                Toast.makeText(Settings.this, "Neuspjesna izmjena.", Toast.LENGTH_LONG).show();
                                System.out.println(error.toString());
                            }
                        });

                Volley.newRequestQueue(Settings.this).add(jsonObjectRequestAddNewUser);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(Settings.this, FirstPage.class);
        intent.putExtra("currentUser", currUser);
        System.out.println("TRENUTNOOOOOO  " + currUser);
        startActivity(intent);
    }
}
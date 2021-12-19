package com.example.myapplication.maps;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.model.Question;
import com.example.myapplication.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapFragment extends Fragment {

    ArrayList<Question> questions;
    User currentUser;
    LatLng startingLatLng;

    public MapFragment(ArrayList<Question> q, User cUser){
        super();
        questions = q;
        currentUser = cUser;
        if(q.isEmpty()){
            startingLatLng = new LatLng(44.77936911471649, 17.198087179935452); // Trg :)
        }
        else{
            questions.forEach((n)->{
                if(n.getId() == currentUser.getCurrentQuestionId()) startingLatLng = new LatLng(Double.parseDouble(n.getLocationLat()), Double.parseDouble(n.getLocationLon()));
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingLatLng, 16.0f));
                // When map is loaded
                questions.forEach((n) -> {
                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = new LatLng(Double.parseDouble(n.getLocationLat()), Double.parseDouble(n.getLocationLon()));
                    setMarkerOptions(latLng, n.getLocationName(), markerOptions);
                    googleMap.addMarker(markerOptions);
                });
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        // Animation when zooming on the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    }
                });
            }
        });
        return view;
    }

    private void setMarkerOptions(LatLng latLng, String markerTitle, MarkerOptions markerOptions){
        markerOptions.position(latLng);
        markerOptions.title(markerTitle);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
    }

}
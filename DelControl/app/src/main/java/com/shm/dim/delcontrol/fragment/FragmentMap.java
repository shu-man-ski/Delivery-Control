package com.shm.dim.delcontrol.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shm.dim.delcontrol.R;
import com.shm.dim.delcontrol.receiver.NetworkChangeReceiver;

import java.io.IOException;

public class FragmentMap
        extends Fragment
        implements OnMapReadyCallback {

    private Geocoder mGeocoder;

    private MapView mMapView;

    private GoogleMap mMap;

    private final float DEFAULT_CAMERA_ZOOM = 11.5f;

    private final float USER_CAMERA_ZOOM = 14f;

    private FloatingActionButton mFindUserLocation;

    private FloatingActionButton mUpdateMarkers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initComponents(view, getArguments());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.getMapAsync(this);
        mMapView.onResume();
    }

    private void initComponents(View view, Bundle savedInstanceState) {
        initGeocoder(view);
        initMapView(view, savedInstanceState);
        initFloatingActionButtons(view);
    }

    private void initGeocoder(View view) {
        mGeocoder = new Geocoder(view.getContext());
    }

    private void initMapView(View view, Bundle savedInstanceState) {
        mMapView = view.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);
    }

    private void initFloatingActionButtons(View view) {
        mFindUserLocation = view.findViewById(R.id.find_location);
        mUpdateMarkers = view.findViewById(R.id.update_markers);
        setButtonOnClickListeners();
    }

    private void setButtonOnClickListeners() {
        mFindUserLocation.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Location userLocation = mMap.getMyLocation();
                LatLng userPosition = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userPosition, USER_CAMERA_ZOOM));
            }
        });
        mUpdateMarkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapView.getMapAsync(FragmentMap.this);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        configureMap();
        setMarkersOnMap();
        setDefaultCameraPosition();
    }

    private void configureMap() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private void setMarkersOnMap() {
        if(isInternetAvailable()) {
            LatLng location = getLatLngByAddress("Минск");
            if (location != null) {
                mMap.addMarker(new MarkerOptions().position(location).title("Минск"));
            }
        }
    }

    private void setDefaultCameraPosition() {
        LatLng defaultPosition = new LatLng(53.904539799999995,27.5615244);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(defaultPosition, DEFAULT_CAMERA_ZOOM));
    }

    private boolean isInternetAvailable() {
        return NetworkChangeReceiver.isInternetAvailable(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    public LatLng getLatLngByAddress(String locationName) {
        Address address = getAddress(locationName);
        if(address != null)
            return new LatLng(address.getLatitude(), address.getLongitude());
        else
            return null;
    }

    private Address getAddress(String locationName) {
        try {
            return mGeocoder.getFromLocationName(locationName, 1).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
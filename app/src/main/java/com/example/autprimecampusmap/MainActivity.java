package com.example.autprimecampusmap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.location.Location;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.net.URI;
import java.net.URL;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnInfoWindowClickListener
{
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private GoogleMap mMap;

    private static final LatLng AUTSouthCampus = new LatLng(-36.984360, 174.879210);
    private Marker SouthCampus;

    private static final LatLng AUTCityCampus = new LatLng(-36.852631, 174.766785);
    private Marker CityCampus;

    private static final LatLng AUTNorthCampus = new LatLng(-36.792930, 174.747960);
    private Marker NorthCampus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }

    private void fetchLastLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>()

        {
            @Override
            public void onSuccess(Location location)
            {
                if (location != null)
                {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(MainActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        //Adds campus markers to map
        addMarkersToMap();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        mMap.setMyLocationEnabled(true);

        //Setting listeners for events
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        //Adding a marker on current location and camera zooms in for current location
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("My current location");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        googleMap.addMarker(markerOptions);
    }

    private void addMarkersToMap()
    {
        // Add a marker in 3 different AUT campuses
        SouthCampus = mMap.addMarker(new MarkerOptions()
                .position(AUTSouthCampus)
                .title("AUT south campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(AUTSouthCampus));

        CityCampus = mMap.addMarker(new MarkerOptions()
                .position(AUTCityCampus)
                .title("AUT city campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(AUTCityCampus));

        NorthCampus = mMap.addMarker(new MarkerOptions()
                .position(AUTNorthCampus)
                .title("AUT north campus"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(AUTNorthCampus));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    fetchLastLocation();
                }
                break;
        }
    }

    @Override
    public boolean onMyLocationButtonClick()
    {
        Toast.makeText(this, "Current location button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location)
    {
        Toast.makeText(this, "Current location", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInfoWindowClick(Marker marker)
    {
        if(marker.equals(SouthCampus))
        {
            Toast.makeText(this, "south campus clicked", Toast.LENGTH_SHORT).show();
            //TODO: open url or image when infowindow clicked
            //https://www.aut.ac.nz/__data/assets/pdf_file/0008/118925/AUT-campus-map-south.pdf
        }

        if(marker.equals(CityCampus))
        {
            Toast.makeText(this, "city campus clicked", Toast.LENGTH_SHORT).show();
            //TODO: open url or image when infowindow clicked
            //https://www.aut.ac.nz/__data/assets/pdf_file/0011/118919/AUT-campus-map-city.pdf
        }

        if(marker.equals(NorthCampus))
        {
            Toast.makeText(this, "north campus clicked", Toast.LENGTH_SHORT).show();
            // TODO: open url or image when infowindow clicked
            //https://www.aut.ac.nz/__data/assets/pdf_file/0006/118905/AUT-campus-map-north.pdf
        }

        else
        {
            Toast.makeText(this, "Info window clicked", Toast.LENGTH_SHORT).show();
        }

    }

}
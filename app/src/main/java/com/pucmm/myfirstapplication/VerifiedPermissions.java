package com.pucmm.myfirstapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class VerifiedPermissions extends AppCompatActivity {

    Button buttonStorage;
    Button buttonLocation;
    Button buttonCamara;
    Button buttonPhone;
    Button buttonContacts;
    FusedLocationProviderClient fusedLocationProviderClient;
    String latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified_permissions);

        buttonStorage = (Button) findViewById(R.id.button_storage);
        buttonLocation = (Button) findViewById(R.id.button_location);
        buttonCamara = (Button) findViewById(R.id.button_camara);
        buttonPhone = (Button) findViewById(R.id.button_phone);
        buttonContacts = (Button) findViewById(R.id.button_contacts);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buttonStorage.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();

            } else {
                Snackbar snackbar = Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction("Open", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Intent storage = new Intent();
                        //getIntent().setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        //startActivity(storage);
                        // Intent storage = new Intent(Intent.ACTION_GET_CONTENT);
                        //storage.setType("*/*");
                        Intent storage = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        storage.addCategory(Intent.CATEGORY_OPENABLE);
                        storage.setType("*/*");
                        startActivity(storage);


                    }
                });

            }
        });
        buttonLocation.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                Snackbar.make(view, "Please request permission.", Snackbar.LENGTH_LONG).show();

            } else {
                Snackbar snackbar = Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction("Open", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ActivityCompat.checkSelfPermission(VerifiedPermissions.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(VerifiedPermissions.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            fusedLocationProviderClient.getLastLocation()
                                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location!=null){
                                        Geocoder geocoder = new Geocoder(VerifiedPermissions.this, Locale.getDefault());
                                        longitude = String.valueOf(location.getLongitude());
                                        latitude = String.valueOf(location.getLatitude());
                                    }

                                }
                            });
                        }

                        Uri gmmIntentUri = Uri.parse("geo:=" + longitude  + ", " + latitude);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);

                    }
                });
            }
        });
        buttonCamara.setOnClickListener(view ->{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED) {
                Snackbar.make(view,"Please request permission.",Snackbar.LENGTH_LONG).show();

            }
            else{
                Snackbar snackbar = Snackbar.make(view,"Permission already granted.",Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction("Open", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Intent camara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                       // startActivityForResult(camara,6);
                        Intent camara = new Intent();
                        camara.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivity(camara);



                    }
                });

            }

        });
        buttonPhone.setOnClickListener(view ->{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_DENIED) {
                Snackbar.make(view,"Please request permission.",Snackbar.LENGTH_LONG).show();

            }
            else {
                Snackbar snackbar = Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction("Open", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:8298892617"));//change the number
                        startActivity(callIntent);

                    }
                });
            }
        });
        buttonContacts.setOnClickListener(view ->{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_DENIED) {
                Snackbar.make(view,"Please request permission.",Snackbar.LENGTH_LONG).show();


            }
            else {
                Snackbar snackbar = Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG);
                snackbar.show();
                snackbar.setAction("Open", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent contacts = new Intent(Intent.ACTION_VIEW);
                        contacts.setData(ContactsContract.Contacts.CONTENT_URI);
                        startActivity(contacts);

                    }
                });
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


            if (locationManager.isProviderEnabled(locationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)){
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location!=null){

                        }
                        else {
                            LocationRequest locationRequest = new LocationRequest()
                                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                    .setInterval(10000)
                                    .setFastestInterval(1000)
                                    .setNumUpdates(1);
                            LocationCallback locationCallback = new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    Location location1 = locationResult.getLastLocation();
                                    super.onLocationResult(locationResult);
                                }
                            };
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                    locationCallback, Looper.myLooper());
                        }
                    }
                });
            }

        }
}
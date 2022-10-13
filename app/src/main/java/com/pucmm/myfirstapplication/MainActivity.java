package com.pucmm.myfirstapplication;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity{

    Button continueB;
    Button cancelB;
    private boolean PermissionGranted = false;
    ActivityResultLauncher<String[]> mPermissionResult;
    private int PERMISSION_CODE = 200;
    String [] permissionNames = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.CAMERA", "android.permission.CALL_PHONE", "android.permission.READ_CONTACTS"};
    ArrayList<SwitchCompat> switchGroup = new ArrayList<>() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        continueB = (Button) findViewById(R.id.continueB);
        cancelB = (Button) findViewById(R.id.cancel);
        switchGroup.add(findViewById(R.id.switch_storage));
        switchGroup.add(findViewById(R.id.switch_location));
        switchGroup.add(findViewById(R.id.switch_camara));
        switchGroup.add(findViewById(R.id.switch_phone));
        switchGroup.add(findViewById(R.id.switch_contacts));
        for (int m = 0; m < permissionNames.length; m++){
            if(ContextCompat.checkSelfPermission(this,permissionNames[m])== PackageManager.PERMISSION_GRANTED){
                switchGroup.get(m).setChecked(true);
                //switchGroup.get(m).setEnabled(false);
            }
            else
            {
                switchGroup.get(m).setChecked(false);
                //switchGroup.get(m).setEnabled(true);
            }
        }

        switchGroup.get(0).setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, permissionNames[0]) == PackageManager.PERMISSION_GRANTED) {
                switchGroup.get(0).setChecked(true);
                Snackbar.make(view,"Permission Granted!!!",Snackbar.LENGTH_LONG).show();
            }
        });
        switchGroup.get(1).setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, permissionNames[1]) == PackageManager.PERMISSION_GRANTED) {
                switchGroup.get(1).setChecked(true);
                Snackbar.make(view,"Permission Granted!!!",Snackbar.LENGTH_LONG).show();
            }
        });
        switchGroup.get(2).setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, permissionNames[2]) == PackageManager.PERMISSION_GRANTED) {
                switchGroup.get(2).setChecked(true);
                Snackbar.make(view,"Permission Granted!!!",Snackbar.LENGTH_LONG).show();
            }
        });
        switchGroup.get(3).setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, permissionNames[3]) == PackageManager.PERMISSION_GRANTED) {
                switchGroup.get(3).setChecked(true);
                Snackbar.make(view,"Permission Granted!!!",Snackbar.LENGTH_LONG).show();
            }
        });
        switchGroup.get(4).setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, permissionNames[4]) == PackageManager.PERMISSION_GRANTED) {
                switchGroup.get(4).setChecked(true);
                Snackbar.make(view,"Permission Granted!!!",Snackbar.LENGTH_LONG).show();
            }
        });

        mPermissionResult = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
            }
        });
        continueB.setOnClickListener(view -> {
            List<String> permissionRequest = new ArrayList<>();
            boolean control = false ;
            for (int i = 0; i < switchGroup.size(); i++){
                if (switchGroup.get(i).isChecked()){
                    PermissionGranted = ContextCompat.checkSelfPermission(this,permissionNames[i]) == PackageManager.PERMISSION_GRANTED;
                    if(!PermissionGranted) {
                        control = true;
                        permissionRequest.add(permissionNames[i]);
                    }
                }
            }
            if (!control)
            {
                Intent verifiedPermissions = new Intent(MainActivity.this, VerifiedPermissions.class);
                startActivity(verifiedPermissions);
            }
            else if (!permissionRequest.isEmpty())
                mPermissionResult.launch(permissionRequest.toArray(new String[0]));
        });
        cancelB.setOnClickListener(view -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            //finish();
            //System.exit(0);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int m = 0; m < permissionNames.length; m++) {
            if (ContextCompat.checkSelfPermission(this, permissionNames[m]) == PackageManager.PERMISSION_GRANTED) {
                switchGroup.get(m).setChecked(true);
                //switchGroup.get(m).setClickable(false);

            } else {
               // switchGroup.get(m).setChecked(false);
                //switchGroup.get(m).setClickable(true);
            }
            Intent verifiedPermissions = new Intent(MainActivity.this, VerifiedPermissions.class);
            startActivity(verifiedPermissions);
        }


    }
    @Override
    protected void onPause(){
        super.onPause();
        for (int m = 0; m < permissionNames.length; m++){
            if(ContextCompat.checkSelfPermission(this,permissionNames[m])== PackageManager.PERMISSION_GRANTED){
                switchGroup.get(m).setChecked(true);
            }
            /*else
            {
                    //switchGroup.get(m).setChecked(false);
            }*/
        }
    }

}
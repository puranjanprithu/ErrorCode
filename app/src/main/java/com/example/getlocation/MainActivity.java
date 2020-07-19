package com.example.getlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Checking for Permission

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
           //REQUEST PERMission SINCE PERMISSION IS NOT GRANTED
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else{
            startService();
        }
    }
//Creating a method to start the service
    void startService() {
        LocationBroadcastReciver reciver = new LocationBroadcastReciver();
        IntentFilter filter = new IntentFilter("act_location");
        registerReceiver(reciver, filter);
        Intent intent = new Intent(MainActivity.this, LocationService.class);
        startService(intent);
    }

    // TO check if user has pressed yes or no

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startService();
                }
                else{
                    Toast.makeText(this, ":Permission Needed", Toast.LENGTH_LONG).show();

                }
        }
    }


    public class LocationBroadcastReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("act_location")){
                double lat = intent.getDoubleExtra("Latitude", 0f);
                double longitude = intent.getDoubleExtra("Longitude", 0f);
                //Toast.makeText(MainActivity.this, "Lat :" + lat + "Long :" + longitude, Toast.LENGTH_LONG).show();

                String strlat = Double.toString(lat);
                String strlongitude = Double.toString(longitude);

                String link = "www.google.com/maps/search/?api=1&query="+strlat+","+strlongitude;

                Toast.makeText(MainActivity.this, link, Toast.LENGTH_LONG).show();

            }
        }
    }
}
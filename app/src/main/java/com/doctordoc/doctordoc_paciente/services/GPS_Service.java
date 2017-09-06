package com.doctordoc.doctordoc_paciente.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by filipp on 6/16/2016.
 */
public class GPS_Service extends Service {

    private LocationListener listener;
    private LocationManager locationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate( ) {

            listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
               /*     Intent i = new Intent("location_update");
                    i.putExtra("longitud",location.getLongitude());
                    i.putExtra("latitud",location.getLatitude());
                    sendBroadcast(i);*/

                    Intent i = new Intent("location_update");
                    if(location.getLatitude()!=0||location.getLongitude()!=0){
                        i.putExtra("ubicacion_existente",true);
                        i.putExtra("longitud",location.getLongitude());
                        i.putExtra("latitud",location.getLatitude());
                    }
                    else
                    {
                        i.putExtra("ubicacion_existente",false);
                    }
                    sendBroadcast(i);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {
                    /*Intent i = new Intent("location_service");
                    i.putExtra("active",true);
                    sendBroadcast(i);*/
                }

                @Override
                public void onProviderDisabled(String s) {
                  /*  Intent i = new Intent("location_service");
                    i.putExtra("active",false);
                    sendBroadcast(i);*/
                /*    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);*/
                }
            };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,listener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }
}
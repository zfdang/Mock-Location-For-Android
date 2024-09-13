package com.zfdang.mocklocation;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

public class MockLocationProvider {

    private final Context context;
    private final LocationManager locationManager;

    public MockLocationProvider(Context context) {
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void updateLocation(double longitude, double latitude) {
        if (locationManager == null) {
            Toast.makeText(context, "Location Manager not available", Toast.LENGTH_SHORT).show();
            return;
        }

        Location mockLocation = new Location(LocationManager.GPS_PROVIDER);
        mockLocation.setLongitude(longitude);
        mockLocation.setLatitude(latitude);
        mockLocation.setAccuracy(1);
        mockLocation.setTime(System.currentTimeMillis());

        try {
            locationManager.setTestProviderLocation(LocationManager.GPS_PROVIDER, mockLocation);
            Toast.makeText(context, "Location updated", Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            Toast.makeText(context, "Permission denied to set mock location", Toast.LENGTH_SHORT).show();
        }
    }
}
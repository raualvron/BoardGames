// https://developer.android.com/reference/android/location/LocationManager

package com.example.boardgames.Classes;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationService implements LocationListener {

    Location location;
    double latitude;
    double longitude;
    protected LocationManager locationManager;

    Context context;
    Activity activity;

    public LocationService(Activity activity) {
        this.context = activity.getBaseContext();
        this.activity = activity;
        this.initGetLocation();
    }


    public Location initGetLocation() {
        try {

            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if (this.isNetworkProviderEnable()) {
                this.requestLocationUpdates();
                if (locationManager != null) {
                    location = this.getLastKnownLocation();
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    // Returns the current enabled/disabled status of the given provider.
    private Boolean isNetworkProviderEnable() {
        return locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    // Register for location updates from the given provider with the given arguments
    private void requestLocationUpdates() {
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                ConstantsHelper.MIN_TIME_MS,
                ConstantsHelper.MIN_DISTANCE_MIN, this);
    }

    // Gets the last known location from the given provider, or null if there is no last known location
    @SuppressLint("MissingPermission")
    private Location getLastKnownLocation() {
            return locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }


    public double getLatitudeLocation() {
        return location.getLatitude();
    }

    public double getLongitudeLocation() {
        return location.getLongitude();
    }


    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


}

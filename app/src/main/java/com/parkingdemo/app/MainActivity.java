package com.parkingdemo.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    //constants
    /**
     * these coordinates are used as dummy parking spots.
     */
    private final LatLng PARKING_SPOT_1 = new LatLng(-31.952854, 115.857342);
    private final LatLng PARKING_SPOT_2 = new LatLng(-33.87365, 151.20689);

    //vars
    private GoogleMap mMap;
    private Marker markerParkingSpotClaimed;
    private Marker markerParkingSpotUnClaimed;

    /**
     * this is for demonstration only, in your real app it should come from your available data source
     */
    private boolean claimed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "Could not load map!", Toast.LENGTH_SHORT).show();
        }

        // i set spot to be claimed, you can play with value
        claimed = true;

    }//oc

    /**
     * Called when the map is ready.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // i am adding both demo markers on map as soon as the map is ready
        markerParkingSpotClaimed = mMap.addMarker(
                new MarkerOptions()
                        .position(PARKING_SPOT_1)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        .title("CLAIMED PARKING SPOT")
                        .snippet(
                                "Parking owner: John doe\n" +
                                        "Parking number: 12345\n" +
                                        "Phone number: 123 45678 90\n" +
                                        "Available until: 29/05/2021"
                        ));
        markerParkingSpotUnClaimed = mMap.addMarker(
                new MarkerOptions()
                        .position(PARKING_SPOT_2)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                        .title("UN-CLAIMED PARKING SPOT")
                        .snippet(
                                "Parking owner: Mr X\n" +
                                        "Parking number: 12345\n" +
                                        "Phone number: 123 45678 90\n" +
                                        "Available until: 29/05/2021"
                        ));

        if (claimed) {
            //i will pass 1 if the spot is claimed
            //plz note that I have 2 separate layouts for each info window
            //parkingSpotType tells the adapter to inflate different layout in case of claimed or un-claimed( 0 or 1)
            mMap.setInfoWindowAdapter(new MapInfoWindowAdapter(MainActivity.this, 1));
        }//if claimed

        else {
            //not claimed
            //i will pass 1 if the spot is un-claimed
            mMap.setInfoWindowAdapter(new MapInfoWindowAdapter(MainActivity.this, 0));
        }//else-//not claimed

        //UI
        zoomAndAnimateCamera();

    }//onMapReady

    private void zoomAndAnimateCamera() {
        //this function is for making map presentation better, don't worry for this stuff
        LatLngBounds latLngBounds = new LatLngBounds
                (
                        PARKING_SPOT_2,
                        PARKING_SPOT_1
                );
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLngBounds.northeast, 2);
        mMap.animateCamera(cameraUpdate);

        //optional
        mMap.setOnInfoWindowClickListener(marker -> Toast.makeText(MainActivity.this,
                marker.getTitle(), Toast.LENGTH_SHORT).show());
    }

}//end class
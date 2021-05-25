package com.parkingdemo.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    //constants
    private static final int CLAIMED = 1;

    private final View mWindow;
    private final Context mContext;
    private final int mParkingSpotType;

    public MapInfoWindowAdapter(Context context, int parkingSpotType) {
        mContext = context;
        mParkingSpotType = parkingSpotType;

        /*parkingSpotType differentiates b/w claimed & un-claimed spots,
         * hence we will inflate the info window layout accordingly
         */
        if (parkingSpotType == CLAIMED) {
            mWindow = LayoutInflater.from(context).inflate(R.layout.claimed_map_info_window, null);
        } else {
            mWindow = LayoutInflater.from(context).inflate(R.layout.unclaimed_map_info_window, null);
        }
    }//constructor

    private void renderWindowText(Marker marker, View view) {

        String title = marker.getTitle();
        String snippet = marker.getSnippet();

        TextView tvTitle = view.findViewById(R.id.marker_title);
        TextView tvSnippet = view.findViewById(R.id.marker_snippet);

        if (title != null && !title.isEmpty()) {
            tvTitle.setText(title);
        }

        if (snippet != null && !snippet.isEmpty()) {
            tvSnippet.setText(snippet);
        }

    }//renderWindowText

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        renderWindowText(marker, mWindow);
        return mWindow;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        return mWindow;
    }
}//end class

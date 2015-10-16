package com.utilsframework.android.gmaps;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLngBounds;
import com.utils.framework.suggestions.SuggestionsProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by stykhonenko on 13.10.15.
 */
public class GoogleMapsSuggestionsProvider implements SuggestionsProvider<Address> {
    private GoogleMap googleMap;

    private final Context context;
    private int count;

    public GoogleMapsSuggestionsProvider(Context context, GoogleMap googleMap, int count) {
        this.context = context;
        this.count = count;
        this.googleMap = googleMap;
        googleMap.getProjection().getVisibleRegion();
    }

    public GoogleMapsSuggestionsProvider(Context context, GoogleMap googleMap) {
        this(context, googleMap, 15);
    }

    @Override
    public List<Address> getSuggestions(String query) {
        List<Address> searchResults = new ArrayList<>();

        Geocoder geocoder = new Geocoder(context, context.getResources().getConfiguration().locale);
        List<Address> addresses = null;

        try {
            LatLngBounds bounds = googleMap.getProjection().getVisibleRegion().latLngBounds;
            addresses = geocoder.getFromLocationName(query, count,
                    bounds.northeast.latitude,
                    bounds.northeast.longitude,
                    bounds.southwest.latitude,
                    bounds.southwest.longitude);

            for (Address address : addresses) {
                if (address.getMaxAddressLineIndex() > 0) {
                    searchResults.add(address);
                }
            }
        } catch (IOException e) {
            return Collections.emptyList();
        }

        return searchResults;
    }
}

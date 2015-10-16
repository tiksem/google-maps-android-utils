package com.utilsframework.android.gmaps;

import android.content.Context;
import android.location.Address;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.utils.framework.suggestions.SuggestionsProvider;
import com.utilsframework.android.adapters.SuggestionsAdapter;

/**
 * Created by stykhonenko on 16.10.15.
 */
public class GoogleMapsSuggestionsView extends FrameLayout {
    private AutoCompleteTextView editText;

    public GoogleMapsSuggestionsView(Context context) {
        super(context);
        init(context);
    }

    public GoogleMapsSuggestionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GoogleMapsSuggestionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(final Context context) {
        if (getChildCount() <= 0) {
            editText = (AutoCompleteTextView) View.inflate(getContext(), getEditTextLayoutId(), null);
            addView(editText);
        } else {
            editText = (AutoCompleteTextView) getChildAt(0);
        }

        final MapView mapView = new MapView(context);
        //mapView.setVisibility(INVISIBLE);
        addView(mapView, new ViewGroup.LayoutParams(100, 100));
        requestLayout();

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                onMapIsReady(googleMap);
            }
        });
    }

    private void onMapIsReady(GoogleMap googleMap) {
        SuggestionsAdapter<Address, ?> adapter = createSuggestionsAdapter(getContext());
        SuggestionsProvider<Address> suggestionsProvider = createSuggestionsProvider(getContext(), googleMap);
        adapter.setSuggestionsProvider(suggestionsProvider);
        editText.setAdapter(adapter);
    }

    protected SuggestionsProvider<Address> createSuggestionsProvider(Context context, GoogleMap googleMap) {
        return new GoogleMapsSuggestionsProvider(context, googleMap);
    }

    protected SuggestionsAdapter<Address, ?> createSuggestionsAdapter(Context context) {
        return new GoogleMapsSuggestionsAdapter(context);
    }

    protected int getEditTextLayoutId() {
        return R.layout.suggestions_edit_text;
    }

    public Editable getText() {
        return editText.getText();
    }

    public void setText(CharSequence text) {
        editText.setText(text);
    }

    public AutoCompleteTextView getEditText() {
        return editText;
    }
}

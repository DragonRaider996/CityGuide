package com.example.p.cityguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by P on 29-12-2016.
 */
public class ViewImage extends android.support.v4.app.Fragment {


    ImageView image;

    public ViewImage getInstance(int n) {
        ViewImage temp = new ViewImage();
        Bundle bundle = new Bundle();
        bundle.putInt("url", n);
        temp.setArguments(bundle);
        return temp;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_image, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            image = (ImageView) view.findViewById(R.id.imageBusiness);
            image.setImageResource(bundle.getInt("url"));
        }
        return view;
    }
}

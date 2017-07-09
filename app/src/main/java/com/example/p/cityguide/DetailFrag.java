package com.example.p.cityguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by P on 30-12-2016.
 */
public class DetailFrag extends Fragment {

    View view;
    RatingBar ratingBar;
    TextView text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_frag, container, false);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);
        text = (TextView) view.findViewById(R.id.textView12);
        ratingBar.setNumStars(5);
        ratingBar.setRating(Float.parseFloat("3.5"));
        return view;
    }

}

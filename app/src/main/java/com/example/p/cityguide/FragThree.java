package com.example.p.cityguide;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

/**
 * Created by P on 08-01-2017.
 */
public class FragThree extends android.support.v4.app.Fragment implements View.OnClickListener {


    Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragthree, container, false);
        btn = (Button) view.findViewById(R.id.rateNow);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.rating, null);
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity()).title("Rate Now").customView(v, true).positiveText("Submit").negativeText("Dismiss").theme(Theme.LIGHT).positiveColor(getResources().getColor(R.color.colorPrimary)).negativeColor(getResources().getColor(R.color.colorPrimary)).contentColor(getResources().getColor(R.color.black)).show();
        final RatingBar ratingBar = (RatingBar) dialog.getCustomView().findViewById(R.id.rating);
        MaterialDialog.Builder builder = dialog.getBuilder();
        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                Toast.makeText(getActivity(), "Your Rating" + String.valueOf(ratingBar.getRating()), Toast.LENGTH_SHORT).show();
            }
        });

    }
}

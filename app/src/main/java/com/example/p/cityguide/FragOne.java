package com.example.p.cityguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by P on 06-01-2017.
 */
public class FragOne extends Fragment implements View.OnClickListener{

    View view;
    TextView name,address,services;
    ImageButton map;
    String lat,lng;
    String label;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragone, container, false);
        //name = (TextView) view.findViewById(R.id.nameBusiness);
        address = (TextView) view.findViewById(R.id.address);
        map = (ImageButton) view.findViewById(R.id.map);
        services = (TextView) view.findViewById(R.id.services);
        map.setOnClickListener(this);
        if(getArguments() != null) {
            //name.setText(getArguments().getString("name"));
            label = getArguments().getString("name");
            address.setText(getArguments().getString("add"));
            services.setText(getArguments().getString("abs"));
            lat = getArguments().getString("lat");
            lng = getArguments().getString("lng");
        }
        else
        {
            //name.setText("empty");
            //label = savedInstanceState.getString("name");
            address.setText("empty");
            //lat = savedInstanceState.getString("lat");
            //lng = savedInstanceState.getString("lng");

        }

        return view;
    }



    @Override
    public void onClick(View v) {
        String format = "geo:0,0?q=" + lat + "," + lng + "(" + label + ")";
        Uri uri = Uri.parse(format);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().startActivity(intent);
    }
}

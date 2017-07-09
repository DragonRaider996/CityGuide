package com.example.p.cityguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by P on 08-01-2017.
 */
public class FragTwo extends Fragment {

    TextView contact, website, monFri, satSun;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragtwo, container, false);
        contact = (TextView) view.findViewById(R.id.contact);
        website = (TextView) view.findViewById(R.id.website);
        monFri = (TextView) view.findViewById(R.id.monfri);
        satSun = (TextView) view.findViewById(R.id.satsun);
        if (getArguments() != null) {
            contact.setText(getArguments().getString("contact"));
            website.setText(getArguments().getString("website"));
            monFri.setText(getArguments().getString("monFri"));
            satSun.setText(getArguments().getString("satSun"));
        }
        return view;
    }
}

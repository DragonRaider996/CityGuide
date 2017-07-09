package com.example.p.cityguide;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

/**
 * Created by P on 29-12-2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    BusinessData data;
    Context context;

    public PagerAdapter(FragmentManager fm, BusinessData temp, Context con) {
        super(fm);
        data = temp;
        context = con;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Detail";
        } else if (position == 1) {
            return "Contact";
        } else {
            return "Reviews";
        }

    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            String name = data.getName();
            String address = data.getAddress();
            String lat = data.getLat();
            String lng = data.getLng();
            String abs = data.getAbs();
            Bundle bun = new Bundle();
            bun.putString("name", name);
            bun.putString("add", address);
            bun.putString("lat", lat);
            bun.putString("lng", lng);
            bun.putString("abs", abs);
            Fragment temp = new FragOne();
            temp.setArguments(bun);
            return temp;
        } else if (position == 1) {
            String contact = data.getMobile();
            String website = data.getWebsite();
            String monFri = data.getMon();
            String satSun = data.getSat();
            Bundle bun = new Bundle();
            bun.putString("contact", contact);
            bun.putString("website", website);
            bun.putString("monFri", monFri);
            bun.putString("satSun", satSun);
            Fragment temp = new FragTwo();
            temp.setArguments(bun);
            return temp;
        } else {
            Fragment temp = new FragThree();
            return temp;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Pranav on 9/1/2015.
 */
public class DesignDemoPagerAdapter extends FragmentStatePagerAdapter {

    public DesignDemoPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        if(position==0) {
            return DesignDemoFragment.newInstance(position);
        }
        else
        {
           return MoviesDemoFragment.newInstance(position);
        }
    }

    @Override
    public int getCount()
    {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String s=null;
       switch (position)
       {
           case 0:
               s="IMAGES";
               break;
           case 1:
               s="MOVIES";
               break;

       }
        return s;
    }
}

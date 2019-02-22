package com.amityprojectvivekrai.amityproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by MR VIVEK RAI on 31-05-2018.
 */
class SectionsPagerAdapter extends FragmentPagerAdapter {


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MajorQuestions major = new MajorQuestions();
                return major;


            case 1:
                MinorQuestions minor = new MinorQuestions();
                return minor;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Major";

            case 1:
                return "Minor";



            default:
                return null;
        }

    }

}
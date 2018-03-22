package sg.edu.iss.trailblazelearnft04.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sg.edu.iss.trailblazelearnft04.Fragment.StationDiscussionFragment;
import sg.edu.iss.trailblazelearnft04.Fragment.StationInfoFragment;
import sg.edu.iss.trailblazelearnft04.Fragment.StationUpdateFragment;


/**
 * Created by mia on 04/03/18.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;
    String trailKey;
    String stationId;

    public TabPagerAdapter(FragmentManager fm, int numOfTabs, String trailKey, String stationId) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.trailKey=trailKey;
        this.stationId=stationId;
    }

    // Launch different fragment by tap different tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                StationInfoFragment stationInfoFragment = new StationInfoFragment();
                Bundle bundle=new Bundle();
                bundle.putString("trailKey",trailKey);
                bundle.putString("stationId",stationId);
                stationInfoFragment.setArguments(bundle);
                return stationInfoFragment;
            case 1:
                StationDiscussionFragment stationDiscussionFragment = new StationDiscussionFragment();
                bundle = new Bundle();
                bundle.putString("stationId",stationId);
                stationDiscussionFragment.setArguments(bundle);
                return stationDiscussionFragment;
            default:
                StationUpdateFragment stationUpdateFragment = new StationUpdateFragment();
                bundle=new Bundle();
                bundle.putString("stationId",stationId);
                stationUpdateFragment.setArguments(bundle);
                return stationUpdateFragment;
        }
    }


    @Override
    public int getCount() {
        return numOfTabs;
    }
}

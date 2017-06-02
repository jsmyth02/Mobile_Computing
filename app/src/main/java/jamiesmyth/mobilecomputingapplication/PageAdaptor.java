package jamiesmyth.mobilecomputingapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import values.NextFixturesFragment;
import values.PastResultsFragment;


public class PageAdaptor extends FragmentPagerAdapter {

    int teamAPIID = 0;

    public PageAdaptor(FragmentManager fm, int intvalue) {
        super(fm);

        teamAPIID = intvalue;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = NextFixturesFragment.newInstance(0, teamAPIID);

        // Displays which fragment will be on which tab in the viewpager
        switch (position) {
            case 0:
                return NextFixturesFragment.newInstance(0,teamAPIID);
            case 1:
                return PastResultsFragment.newInstance(1, teamAPIID);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        // Number of tabs
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Sets title of each tab
        switch (position)
        {
            case 0:
                return "Fixtures";
            case 1:
                return "Results";
        }

        return "Fixtures " + (position + 1);
    }
}
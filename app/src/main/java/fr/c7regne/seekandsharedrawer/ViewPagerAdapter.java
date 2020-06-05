/** Adapter for ViewPager: let us slide between Fragments **/

package fr.c7regne.seekandsharedrawer;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;



public class ViewPagerAdapter extends FragmentPagerAdapter {
    //we first create a list of our fragments
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }
    //different fonctions to..
    //..see the fragments linked with a special position
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    //..know the number of fragments
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    //..add a fragment in the list
    void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }

}

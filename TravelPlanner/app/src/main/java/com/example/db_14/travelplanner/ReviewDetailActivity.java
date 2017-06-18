package com.example.db_14.travelplanner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.db_14.travelplanner.FirstFragment;
import com.example.db_14.travelplanner.SecondFragment;

public class ReviewDetailActivity extends AppCompatActivity implements ActionBar.TabListener{

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected ( int position){
                actionBar.setSelectedNavigationItem(position);
            }
        });
        for(int i=0; i<mAppSectionsPagerAdapter.getCount(); i++){
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override //액션바의 탭 선택시 호출됨
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        //액션바에서 선택된 탭에 대응되는 페이지를 뷰페이지에서 현재 보여지는 페이지로 변경한다.
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        private FragmentManager fm;

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int pos) {
            //태그로 프래그먼트를 찾는다.
            Fragment fragment = fm.findFragmentByTag("android:switcher:" + mViewPager.getId() + ":" + getItemId(pos));

            //프래그먼트가 이미 생성되어 있는 경우에는 리턴
            if (fragment != null) {
                return fragment;
            }

            //프래그먼트의 인스턴스를 생성한다.
            switch(pos) {
                case 0: return FirstFragment.newInstance("");
                case 1: return SecondFragment.newInstance("");
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        //탭제목
        @Override
        public CharSequence getPageTitle(int position) {
            return "review" + (position + 1);
        }
    }

}
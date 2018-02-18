package com.codingtest.codingtest.activities;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codingtest.codingtest.R;
import com.codingtest.codingtest.fragments.FragmentFour;
import com.codingtest.codingtest.fragments.FragmentOne;
import com.codingtest.codingtest.fragments.FragmentThree;
import com.codingtest.codingtest.fragments.FragmentTwo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RelativeLayout mMainLayout;
    private TextView mTabSelectedTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabDotsLayout = (TabLayout)findViewById(R.id.tabDots);
        tabDotsLayout.setupWithViewPager(viewPager, true);
        setupViewPager(viewPager);

        mMainLayout = findViewById(R.id.lyt_main);
        mTabSelectedTextView = findViewById(R.id.tv_tab_selected);

        findViewById(R.id.white_bg_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainLayout.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        findViewById(R.id.grey_bg_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainLayout.setBackgroundColor(getResources().getColor(R.color.grey));
            }
        });
        findViewById(R.id.blue_bg_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        });
        findViewById(R.id.nextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NavigationActivity.class));
            }
        });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabSelectedTextView.setText(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FragmentOne(), "1");
        adapter.addFrag(new FragmentTwo(), "2");
        adapter.addFrag(new FragmentThree(), "3");
        adapter.addFrag(new FragmentFour(), "4");

        viewPager.setAdapter(adapter);
    }

   class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}





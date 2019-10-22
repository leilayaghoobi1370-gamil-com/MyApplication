package com.example.practice_9_task;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.practice_9_task.Repository.Repositroy;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ListTaskActivity extends AppCompatActivity {
    public static final String KEYNAME = "com.example.practice_9_task_key";
    public static final String TABNAME = "com.example.practice_9_task_tabname";
    public static final String KEYPASSWORD = "com.example.practice_9_task_PASSWORD";
    public static final String PASSWORD = "com.example.practice_9_task_PASSWORD";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MenuItem sreach, account, delete;
    private TabFragment.RecycleAdapter mRecycleAdapter;
    private TapPagerAdapter tapPagerAdapter;
    String tabname = "";


    public static Intent newIntet(Context context, String name, String password, String tabname) {
        Intent intent = new Intent(context, ListTaskActivity.class);
        intent.putExtra(KEYNAME, name);
        intent.putExtra(PASSWORD, password);
        intent.putExtra(TABNAME, tabname);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_task);
        mViewPager = findViewById(R.id.pager);
        mTabLayout = findViewById(R.id.tabMode);
        tapPagerAdapter = new TapPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(tapPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
       mRecycleAdapter =new TabFragment().new RecycleAdapter((Repositroy.newInstance(getApplicationContext())
                .getArrayList(getIntent().getStringExtra(KEYNAME), getIntent().getStringExtra(PASSWORD), getIntent().getStringExtra(TABNAME))),
                this,
                getIntent().getStringExtra(KEYNAME));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("TAG", "onPageScroll0: ");
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAG", "onPageScroll1: ");
                mRecycleAdapter = ((TabFragment) ((TapPagerAdapter) mViewPager.getAdapter()).getItem(position)).recycleAdapter;
                tapPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("TAG", "onPageScroll2: ");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.itemfragmentmenu, menu);
        account = menu.findItem(R.id.id_account);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_account:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    class TapPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments = new ArrayList<>();

        public TapPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(TabFragment.newInstance(tabname = "TODO", getIntent().getStringExtra(KEYNAME), getIntent().getStringExtra(KEYPASSWORD)));
            fragments.add(TabFragment.newInstance(tabname = "DONE", getIntent().getStringExtra(KEYNAME), getIntent().getStringExtra(KEYPASSWORD)));
            fragments.add(TabFragment.newInstance(tabname = "DOING", getIntent().getStringExtra(KEYNAME), getIntent().getStringExtra(KEYPASSWORD)));
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);

          /*  switch (position) {
                case 0:
                    return TabFragment.newInstance(tabname = "TODO", getIntent().getStringExtra(KEYNAME), getIntent().getStringExtra(KEYPASSWORD));
                case 1:
                    return TabFragment.newInstance(tabname = "DONE", getIntent().getStringExtra(KEYNAME), getIntent().getStringExtra(KEYPASSWORD));
                default:
                    return TabFragment.newInstance(tabname = "DOING", getIntent().getStringExtra(KEYNAME), getIntent().getStringExtra(KEYPASSWORD));
            }*/

        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "TODO";
                case 1:
                    return "DONE";
                default:
                    return "DOING";
            }
        }

    }


}

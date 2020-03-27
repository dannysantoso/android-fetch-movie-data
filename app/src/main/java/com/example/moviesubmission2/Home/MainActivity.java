package com.example.moviesubmission2.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.moviesubmission2.Favorite.FavoriteActivity;
import com.example.moviesubmission2.R;
import com.example.moviesubmission2.Search.SearchActivity;
import com.example.moviesubmission2.SettingActivity;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
//            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.favorite){
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



            SectionPagerAdapter sectionsPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager(), this);
            ViewPager viewPager = findViewById(R.id.view_pager);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setElevation(0);
            }
    }
}


package com.example.moviesubmission2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviesubmission2.Favorite.FavoriteActivity;
import com.example.moviesubmission2.Home.MainActivity;
import com.example.moviesubmission2.Search.SearchActivity;

import butterknife.BindView;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.frame_container)
    FrameLayout tvSettingLanguage;


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
        setContentView(R.layout.activity_setting);

//        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SettingFragment()).commit();
    }
}

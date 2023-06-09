package com.if4b.goplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.if4b.goplanner.databinding.ActivityAboutAppBinding;

public class ActivityAboutApp extends AppCompatActivity {
    private ActivityAboutAppBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutAppBinding.inflate(getLayoutInflater());
        getSupportActionBar().setTitle("ABOUT APP");
        setContentView(binding.getRoot());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }
}
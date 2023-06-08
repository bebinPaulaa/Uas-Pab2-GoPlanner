package com.if4b.goplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.if4b.goplanner.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<Study> data;
    private StudyViewAdapter studyViewAdapter;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("My Study Plan");



        studyViewAdapter = new StudyViewAdapter();
        binding.rvStudy.setLayoutManager(new LinearLayoutManager(this));
        binding.rvStudy.setAdapter(studyViewAdapter);

        binding.fabInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddStudyActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getAllPost(){
        binding.progressBar.setVisibility(View.VISIBLE);
        APIService api = Utility.getRetrofit().create(APIService.class);
        Call<ValueData<List<Study>>> call = api.getStudy();

        call.enqueue(new Callback<ValueData<List<Study>>>() {
            @Override
            public void onResponse(Call<ValueData<List<Study>>> call, Response<ValueData<List<Study>>> response) {
                binding.progressBar.setVisibility(View.GONE);

                if(response.code()==200){
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();
                    if (success==1){
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        data = response.body().getData();
                        studyViewAdapter.setData(data);
                    }else{
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "Response " +response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueData<List<Study>>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                System.out.println("Retrofit Error : "+t.getMessage());
                Toast.makeText(MainActivity.this, "Retrofit Error : "+ t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAllPost();
    }

}
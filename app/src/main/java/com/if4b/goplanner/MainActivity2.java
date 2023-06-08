package com.if4b.goplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.if4b.goplanner.databinding.ActivityMain2Binding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {
    private WorkViewAdapter workViewAdapter;
    private ActivityMain2Binding binding;
    private List<Work> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("My Work Plan");

        workViewAdapter = new WorkViewAdapter();
        binding.rvWork.setLayoutManager(new LinearLayoutManager(this));
        binding.rvWork.setAdapter(workViewAdapter);

        binding.fabInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, AddWorkActivity.class);
                startActivity(intent);
            }
        });

    }
   private void getAllWork(){
        binding.progressBar.setVisibility(View.VISIBLE);
        APIService api = Utility.getRetrofit().create(APIService.class);
        Call<ValueData<List<Work>>> call = api.getWork();

        call.enqueue(new Callback<ValueData<List<Work>>>() {
            @Override
            public void onResponse(Call<ValueData<List<Work>>> call, Response<ValueData<List<Work>>> response) {
                binding.progressBar.setVisibility(View.GONE);
                if(response.code()==200){
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();
                    if (success==1){
                        Toast.makeText(MainActivity2.this, message, Toast.LENGTH_SHORT).show();
                        data = response.body().getData();
                        workViewAdapter.setData(data);
                    }else{
                        Toast.makeText(MainActivity2.this, message, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity2.this, "Response " +response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueData<List<Work>>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                System.out.println("Retrofit Error : "+t.getMessage());
                Toast.makeText(MainActivity2.this, "Retrofit Error : "+ t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
   }

    @Override
    protected void onResume() {
        super.onResume();
        getAllWork();
    }
}
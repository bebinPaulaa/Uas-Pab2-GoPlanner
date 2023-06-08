package com.if4b.goplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.if4b.goplanner.databinding.ActivityMain2Binding;
import com.if4b.goplanner.databinding.ActivityMain3Binding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity3 extends AppCompatActivity {
    private NoteViewAdapter noteViewAdapter;
    private ActivityMain3Binding binding;
    private List<Note> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("My Notes");

        noteViewAdapter = new NoteViewAdapter();
        binding.rvNote.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNote.setAdapter(noteViewAdapter);

        binding.fabInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this,AddNotesActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getAllNote(){
        binding.progressBar.setVisibility(View.VISIBLE);
        APIService api = Utility.getRetrofit().create(APIService.class);
        Call<ValueData<List<Note>>> call = api.getNote();

        call.enqueue(new Callback<ValueData<List<Note>>>() {
            @Override
            public void onResponse(Call<ValueData<List<Note>>> call, Response<ValueData<List<Note>>> response) {
                binding.progressBar.setVisibility(View.GONE);
                if(response.code()==200){
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();
                    if (success==1){
                        Toast.makeText(MainActivity3.this, message, Toast.LENGTH_SHORT).show();
                        data = response.body().getData();
                        noteViewAdapter.setData(data);
                    }else{
                        Toast.makeText(MainActivity3.this, message, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity3.this, "Response " +response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueData<List<Note>>> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                System.out.println("Retrofit Error : "+t.getMessage());
                Toast.makeText(MainActivity3.this, "Retrofit Error : "+ t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllNote();
    }
}
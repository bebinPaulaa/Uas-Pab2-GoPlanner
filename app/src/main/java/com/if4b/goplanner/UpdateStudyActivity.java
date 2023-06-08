package com.if4b.goplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.if4b.goplanner.databinding.ActivityUpdateStudyBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateStudyActivity extends AppCompatActivity {
    private ActivityUpdateStudyBinding binding;
    private Study study;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateStudyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Update Study Plan");

        study = getIntent().getParcelableExtra("EXTRA_DATA");
        String id = study.getId();
        String content = study.getContent();
        //getExtra
        binding.etContent.setText(content);

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.etContent.getText().toString();

                boolean bolehUpdate =true;

                if (TextUtils.isEmpty(content)){
                    bolehUpdate = false;
                    binding.etContent.setError("Tidak Boleh Kosong!");
                }
                if(bolehUpdate){
                    updateStudy(id,content);
                    Intent intent = new Intent(UpdateStudyActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void updateStudy(String id, String content) {
        binding.progressBar.setVisibility(View.VISIBLE);
        APIService api = Utility.getRetrofit().create(APIService.class);
        Call<ValueNoData> call = api.updateStudy(id,content);
        call.enqueue(new Callback<ValueNoData>() {
            @Override
            public void onResponse(Call<ValueNoData> call, Response<ValueNoData> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.code()==200){
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if (success==1){
                        Toast.makeText(UpdateStudyActivity.this, message, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UpdateStudyActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(UpdateStudyActivity.this, "Response : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                System.out.println("Retrofit Error : "+ t.getMessage());
                Toast.makeText(UpdateStudyActivity.this, "Retrofit Error : "+ t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
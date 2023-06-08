package com.if4b.goplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.if4b.goplanner.databinding.ActivityAddNotesBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNotesActivity extends AppCompatActivity {
    private ActivityAddNotesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Add Notes");

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.etContent.getText().toString();

                boolean bolehNotes = true;
                if(TextUtils.isEmpty(content)){
                    bolehNotes=false;
                    binding.etContent.setError("Konten Tidak Boleh Kosong");
                }
                if(bolehNotes){
                    String userId = Utility.getValue(AddNotesActivity.this, "xUserId");
                    addNotes(content, userId);
                }
            }
        });

    }

    private void addNotes (String content, String userId){
        binding.progressBar.setVisibility(View.VISIBLE);
        APIService api = Utility.getRetrofit().create(APIService.class);
        Call<ValueNoData> call = api.addNote(content, userId);

        call.enqueue(new Callback<ValueNoData>() {
            @Override
            public void onResponse(Call<ValueNoData> call, Response<ValueNoData> response) {
                binding.progressBar.setVisibility(View.GONE);
                if(response.code()==200){
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if(success==1){
                        Toast.makeText(AddNotesActivity.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(AddNotesActivity.this, message, Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(AddNotesActivity.this, "Response : "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                System.out.println("Retrofit Error : "+ t.getMessage());

                Toast.makeText(AddNotesActivity.this, "Retrofit Error : "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
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
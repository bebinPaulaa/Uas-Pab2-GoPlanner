package com.if4b.goplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.if4b.goplanner.databinding.ActivityUpdateNoteBinding;
import com.if4b.goplanner.databinding.ActivityUpdateStudyBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateNoteActivity extends AppCompatActivity {
    private ActivityUpdateNoteBinding binding;
    private Note note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Update Notes");

        note = getIntent().getParcelableExtra("EXTRA_DATA");
        String id = note.getId();
        String content = note.getContent();
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
                    updateNote(id,content);
                    Intent intent = new Intent(UpdateNoteActivity.this,MainActivity3.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    private void updateNote(String id, String content) {
        binding.progressBar.setVisibility(View.VISIBLE);
        APIService api = Utility.getRetrofit().create(APIService.class);
        Call<ValueNoData> call = api.updateNote(id,content);
        call.enqueue(new Callback<ValueNoData>() {
            @Override
            public void onResponse(Call<ValueNoData> call, Response<ValueNoData> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.code()==200){
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if (success==1){
                        Toast.makeText(UpdateNoteActivity.this, message, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UpdateNoteActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(UpdateNoteActivity.this, "Response : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                System.out.println("Retrofit Error : "+ t.getMessage());
                Toast.makeText(UpdateNoteActivity.this, "Retrofit Error : "+ t.getMessage(), Toast.LENGTH_SHORT).show();

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
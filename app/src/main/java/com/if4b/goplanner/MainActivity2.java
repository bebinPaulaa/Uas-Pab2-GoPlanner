package com.if4b.goplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.if4b.goplanner.databinding.ActivityMain2Binding;

import java.util.ArrayList;
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

        workViewAdapter.setOnItemLongClickListener(new WorkViewAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int position) {
                PopupMenu popupMenu = new PopupMenu(MainActivity2.this, v);
                popupMenu.inflate(R.menu.menu_popup);
                popupMenu.setGravity(Gravity.RIGHT);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int idMenu = item.getItemId();

                        if (idMenu == R.id.action_edit) {
                            Intent intent = new Intent(MainActivity2.this, UpdateWorkActivity.class);
                            intent.putExtra("EXTRA_DATA", data.get(position));
                            startActivity(intent);
                            return true;
                        } else if (idMenu == R.id.action_delete) {
                            String id = data.get(position).getId();
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity2.this);
                            alertDialogBuilder.setTitle("Konfirmasi");
                            alertDialogBuilder.setMessage("Yakin ingin menghapus postingan '" + data.get(position).getContent() + "' ? ");

                            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deletePost(id);
                                }
                            });

                            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });


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
    private void deletePost(String id) { //updatePost
        APIService api = Utility.getRetrofit().create(APIService.class);
        Call<ValueNoData> call = api.deleteWork(id);
        call.enqueue(new Callback<ValueNoData>() {
            @Override
            public void onResponse(Call<ValueNoData> call, Response<ValueNoData> response) {
                if(response.code()==200){
                    int success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    if (success == 1){
                        Toast.makeText(MainActivity2.this, message, Toast.LENGTH_SHORT).show();
                        getAllWork();
                    }else{
                        Toast.makeText(MainActivity2.this, message, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity2.this, "Response "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ValueNoData> call, Throwable t) {
                System.out.println("Retrofit Errror : "+ t.getMessage());
                Toast.makeText(MainActivity2.this, "Retrofit Error : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    searchbar option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        SearchView searchView2 = (SearchView) menu.findItem(R.id.item_search).getActionView();

        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Work> worksFilter = new ArrayList<>();
                for (Work work : data){
                    String contentwork = work.getContent().toLowerCase();
                    if (contentwork.contains(newText)){
                        worksFilter.add(work);
                    }
                }
                workViewAdapter.setFilterWork(worksFilter);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllWork();
    }
}
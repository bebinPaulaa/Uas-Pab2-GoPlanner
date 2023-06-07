package com.if4b.goplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.if4b.goplanner.databinding.StudyItemBinding;

import java.util.ArrayList;
import java.util.List;

public class StudyViewAdapter extends RecyclerView.Adapter<StudyViewAdapter.ViewHolder>
{
    private List<Study> data = new ArrayList<>();
    private  OnItemLongClickListener onItemLongClickListener;

    public void setData(List<Study> data){
        this.data =data;

    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {//updatePost
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(StudyItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        Study study = data.get(pos);

        holder.studyItemBinding.tvContent.setText(study.getContent());
        holder.studyItemBinding.tvCreatedDate.setText(study.getCreate_date());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListener.onItemLongClick(v,pos);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private StudyItemBinding studyItemBinding;
        public ViewHolder(@NonNull StudyItemBinding itemView) {
            super(itemView.getRoot());
            studyItemBinding=itemView;
        }
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View v,int position);
    }
}

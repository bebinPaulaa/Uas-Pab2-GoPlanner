package com.if4b.goplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.if4b.goplanner.databinding.WorkItemBinding;

import java.util.ArrayList;
import java.util.List;

public class WorkViewAdapter extends RecyclerView.Adapter<WorkViewAdapter.ViewHolder> {
    private List<Work> data = new ArrayList<>();
    private onItemLongClickListener onItemLongClickListener;

    public void setData(List<Work> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setOnItemLongClickListener(onItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(WorkItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        Work work = data.get(pos);
        holder.workItemBinding.tvContent.setText(work.getContent());
        holder.workItemBinding.tvCreatedDate.setText(work.getCreate_date());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListener.onItemLongClick(v, pos);
                return false;
            }
        });
    }

    public int getItemCount() { return data.size(); }

    //searchbar filterModel
    public void setFilterWork(ArrayList<Work> filterWork){
        data = new ArrayList<>();
        data.addAll(filterWork);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private WorkItemBinding workItemBinding;

        public ViewHolder(@NonNull WorkItemBinding itemView) {
            super(itemView.getRoot());
            workItemBinding = itemView;
        }
    }

    public interface onItemLongClickListener {
        void onItemLongClick(View v, int position);
    }
}

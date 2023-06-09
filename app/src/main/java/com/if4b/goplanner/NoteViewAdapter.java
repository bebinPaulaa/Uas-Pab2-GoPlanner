package com.if4b.goplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.if4b.goplanner.databinding.NoteItemBinding;

import java.util.ArrayList;
import java.util.List;

public class NoteViewAdapter extends RecyclerView.Adapter<NoteViewAdapter.ViewHolder> {
    private List<Note> data = new ArrayList<>();
    private NoteViewAdapter.OnItemLongClickListener onItemLongClickListener;

    public void setData(List<Note> data) {
        this.data = data;
        notifyDataSetChanged();

    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        Note note = data.get(pos);

        holder.noteItemBinding.tvContent.setText(note.getContent());
        holder.noteItemBinding.tvCreatedDate.setText(note.getCreate_date());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListener.onItemLongClick(v,pos);
                return false;
            }
        });
    }

    public int getItemCount() { return data.size(); }

    public void setFilterNote(ArrayList<Note> filterNote){
        data = new ArrayList<>();
        data.addAll(filterNote);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private NoteItemBinding noteItemBinding;
        public ViewHolder(@NonNull NoteItemBinding itemView) {
            super(itemView.getRoot());
            noteItemBinding = itemView;
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int position);
    }
}

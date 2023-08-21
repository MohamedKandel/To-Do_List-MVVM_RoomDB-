package com.example.mvvm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    Context context;
    onItemClickListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_layout,parent,false);
        context = parent.getContext();
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int i) {
        Note note = notes.get(i);
        holder.txt_title.setText(note.getTitle());
        holder.txt_desc.setText(note.getDescription());
        holder.txt_per.setText(note.getPriority()+"");

        /*holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,holder.txt_title.getText().toString()+"\n"
                +holder.txt_desc.getText().toString()+"\t"+
                        holder.txt_per.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void SetNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        TextView txt_title,txt_desc,txt_per;
        CardView card;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.txt_title);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            txt_per = itemView.findViewById(R.id.txt_per);
            card = itemView.findViewById(R.id.card);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();
                    if(listener!=null && position >= 0 ) {
                        listener.OnItemClick(notes.get(position));
                        Log.d("position",position+"");
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void OnItemClick(Note note);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}

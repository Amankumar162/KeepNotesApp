package com.example.keepnotes.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.keepnotes.R;
import com.example.keepnotes.note.details;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<String> titles;
    List<String> content;


public Adapter(List<String> titles,List<String> content){
    this.titles = titles;
    this.content = content;

}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    holder.noteTitle.setText(titles.get(position));
    holder.noteContent.setText(content.get(position));
    final int value = getRandomColor();
    holder.mCardView.setCardBackgroundColor(holder.view.getResources().getColor(value,null));

    holder.view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(view.getContext(), details.class);
            i.putExtra("titles",titles.get(position));
            i.putExtra("content",content.get(position));
            i.putExtra("value",value);

            view.getContext().startActivity(i);
        }
    });



    }

    private int getRandomColor() {

    List<Integer>colorCode = new ArrayList<>();
    colorCode.add(R.color.red);
    colorCode.add(R.color.grey);
    colorCode.add(R.color.yellow);
    colorCode.add(R.color.orange);
    colorCode.add(R.color.mintblue);
    colorCode.add(R.color.colorPrimary);
    colorCode.add(R.color.bluevelvet);
    colorCode.add(R.color.pink);
    colorCode.add(R.color.colorAccent);
    colorCode.add(R.color.purple);

    Random randomColor = new Random();
    int number = randomColor.nextInt(colorCode.size());
    return colorCode.get(number);
}

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView noteTitle,noteContent;
    View view;
    CardView mCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);
            mCardView = itemView.findViewById(R.id.noteCard);
            view = itemView;
        }
    }
}

package com.example.modernquran.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modernquran.Model.QuranResponse;
import com.example.modernquran.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VerseAdapter extends RecyclerView.Adapter<VerseAdapter.VerseViewHolder> {

    private final List<Map.Entry<Integer, QuranResponse.Verse>> verses;

    public VerseAdapter(Map<Integer, QuranResponse.Verse> verses) {
        this.verses = new ArrayList<>(verses.entrySet());
    }

    @NonNull
    @Override
    public VerseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_verse, parent, false);
        return new VerseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerseViewHolder holder, int position) {
        QuranResponse.Verse verse = verses.get(position).getValue();
        holder.textAyahNumber.setText(String.valueOf(verse.getIdAyah()));
        holder.textViewVerseContent.setText(verse.getContent());
        holder.textViewVerseTranslation.setText(verse.getTranslationEng());
        holder.textViewVerseTransliteration.setText(verse.getTransliteration());
    }

    @Override
    public int getItemCount() {
        return verses.size();
    }

    public static class VerseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewVerseContent;
        TextView textViewVerseTranslation;
        TextView textViewVerseTransliteration;
        TextView textAyahNumber;

        public VerseViewHolder(@NonNull View itemView) {
            super(itemView);
            textAyahNumber = itemView.findViewById(R.id.ayahNumber);
            textViewVerseContent = itemView.findViewById(R.id.textViewVerseContent);
            textViewVerseTranslation = itemView.findViewById(R.id.textViewVerseTranslation);
            textViewVerseTransliteration = itemView.findViewById(R.id.textViewVerseTransliteration);
        }
    }
}

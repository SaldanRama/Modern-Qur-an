package com.example.modernquran.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modernquran.Model.QuranResponse;
import com.example.modernquran.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahViewHolder> {

    private List<QuranResponse> surahList;
    private final OnItemClickListener listener;

    public SurahAdapter(List<QuranResponse> surahList, OnItemClickListener listener) {
        this.surahList = surahList;
        this.listener = listener;

        // Sort the surahList based on their ID
        Collections.sort(surahList, new Comparator<QuranResponse>() {
            @Override
            public int compare(QuranResponse surah1, QuranResponse surah2) {
                return surah1.getId() - surah2.getId();
            }
        });
    }

    public void addSurahAtIndex(QuranResponse surah, int index) {
        surahList.add(index, surah);
        notifyItemInserted(index);
    }

    @NonNull
    @Override
    public SurahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_surah, parent, false);
        return new SurahViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahViewHolder holder, int position) {
        QuranResponse surah = surahList.get(position);
        holder.textViewSurahNumber.setText(String.valueOf(surah.getId())); // Set Surah number
        holder.textViewSurahName.setText(surah.getSurahName());
        holder.textViewSurahTranslation.setText(surah.getTranslation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(surah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return surahList.size();
    }

    public void addSurah(QuranResponse surah) {
        surahList.add(surah);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(QuranResponse surah);
    }

    static class SurahViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSurahNumber; // Add TextView for Surah number
        TextView textViewSurahName;
        TextView textViewSurahTranslation;

        SurahViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSurahNumber = itemView.findViewById(R.id.textViewSurahNumber); // Initialize Surah number TextView
            textViewSurahName = itemView.findViewById(R.id.textViewSurahName);
            textViewSurahTranslation = itemView.findViewById(R.id.textViewSurahTranslation);
        }
    }
}

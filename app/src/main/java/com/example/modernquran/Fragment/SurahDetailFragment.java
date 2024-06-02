package com.example.modernquran.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.modernquran.R;
import com.example.modernquran.Model.Verse;

import java.util.ArrayList;
import java.util.List;

public class SurahDetailFragment extends Fragment {

    private static final String ARG_SURAH_ID = "surah_id";
    private static final String ARG_SURAH_NAME = "surah_name";
    private static final String ARG_SURAH_TRANSLATION = "surah_translation";
    private static final String ARG_VERSES = "verses";

    private int surahId;
    private String surahName;
    private String surahTranslation;
    private List<Verse> verseList;

    public static SurahDetailFragment newInstance(int surahId, String surahName, String surahTranslation, List<Verse> verses) {
        SurahDetailFragment fragment = new SurahDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SURAH_ID, surahId);
        args.putString(ARG_SURAH_NAME, surahName);
        args.putString(ARG_SURAH_TRANSLATION, surahTranslation);
        args.putParcelableArrayList(ARG_VERSES, new ArrayList<>(verses));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            surahId = getArguments().getInt(ARG_SURAH_ID);
            surahName = getArguments().getString(ARG_SURAH_NAME);
            surahTranslation = getArguments().getString(ARG_SURAH_TRANSLATION);
            verseList = getArguments().getParcelableArrayList(ARG_VERSES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surah_detail, container, false);
        TextView textViewSurahName = view.findViewById(R.id.textViewSurahName);
        TextView textViewSurahTranslation = view.findViewById(R.id.textViewSurahTranslation);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        textViewSurahName.setText(surahName);
        textViewSurahTranslation.setText(surahTranslation);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        VerseAdapter verseAdapter = new VerseAdapter(verseList);
        recyclerView.setAdapter(verseAdapter);

        ImageView backBtn = view.findViewById(R.id.backbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack(); // Kembali ke fragment sebelumnya
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Mendapatkan referensi ke MeowBottomNavigation dari activity
        MeowBottomNavigation meowBottomNavigation = getActivity().findViewById(R.id.bottomNavigationView);

        // Sembunyikan MeowBottomNavigation
        meowBottomNavigation.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Mendapatkan referensi ke MeowBottomNavigation dari activity
        MeowBottomNavigation meowBottomNavigation = getActivity().findViewById(R.id.bottomNavigationView);

        // Tampilkan kembali MeowBottomNavigation
        meowBottomNavigation.setVisibility(View.VISIBLE);
    }

    static class VerseAdapter extends RecyclerView.Adapter<VerseAdapter.VerseViewHolder> {
        private List<Verse> verseList;

        public VerseAdapter(List<Verse> verseList) {
            this.verseList = verseList;
        }

        @NonNull
        @Override
        public VerseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_verse, parent, false);
            return new VerseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VerseViewHolder holder, int position) {
            Verse verse = verseList.get(position);
            holder.textAyahNumber.setText(String.valueOf(verse.getIdAyahAsInt()));
            holder.textViewVerseContent.setText(verse.getContent());
            holder.textViewVerseTranslation.setText(verse.getTranslationEng());
            holder.textViewVerseTransliteration.setText(verse.getTransliteration());
        }

        @Override
        public int getItemCount() {
            return verseList.size();
        }

        static class VerseViewHolder extends RecyclerView.ViewHolder {
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
}

package com.example.modernquran.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.modernquran.API.ApiClient;
import com.example.modernquran.Model.QuranResponse;
import com.example.modernquran.R;
import com.example.modernquran.Adapter.SurahAdapter;
import com.example.modernquran.Model.Verse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurahListFragment extends Fragment {

    private static final String apiKey = "fd3236f220msh774f9033613033ep18f090jsn1fc1299ab4ed";
    private static final String apiHost = "al-quran1.p.rapidapi.com";

    private RecyclerView recyclerView;
    private SurahAdapter surahAdapter;
    private LottieAnimationView progressBar;
    private TextView textViewSurahTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_surah_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewSurahList);
        progressBar = view.findViewById(R.id.progressBar);
        textViewSurahTitle = view.findViewById(R.id.textViewSurahTitle);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SurahAdapter.OnItemClickListener listener = new SurahAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(QuranResponse surah) {
                navigateToSurahDetail(surah);
            }
        };

        progressBar.playAnimation();

        surahAdapter = new SurahAdapter(new ArrayList<>(), listener);
        recyclerView.setAdapter(surahAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!isNetworkAvailable()) {
            Toast.makeText(getActivity(), "Tidak Ada Koneksi Internet", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            textViewSurahTitle.setVisibility(View.GONE);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isAdded()) { // Check if the fragment is added to its activity
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                textViewSurahTitle.setVisibility(View.VISIBLE);
                                getAllSurahs();
                            }
                        });
                    }
                }
            }).start();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getAllSurahs() {
        for (int chapterId = 1; chapterId <= 114; chapterId++) {
            ApiClient.getInstance().getApi().getSurah(apiKey, apiHost, chapterId).enqueue(new Callback<QuranResponse>() {
                @Override
                public void onResponse(Call<QuranResponse> call, Response<QuranResponse> response) {
                    if (response.isSuccessful()) {
                        QuranResponse surah = response.body();
                        if (surah != null) {
                            int position = surah.getId() - 1;
                            if (position >= 0 && position < surahAdapter.getItemCount()) {
                                surahAdapter.addSurahAtIndex(surah, position);
                            } else {
                                surahAdapter.addSurah(surah);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<QuranResponse> call, Throwable t) {
                    Log.e("SurahListFragment", "Error: " + t.getMessage());
                }
            });
        }
    }

    private void navigateToSurahDetail(QuranResponse surah) {
        int surahId = surah.getId();
        String surahName = surah.getSurahName();
        String surahTranslation = surah.getTranslation();
        List<Verse> verses = new ArrayList<>();

        for (Map.Entry<Integer, QuranResponse.Verse> entry : surah.getVerses().entrySet()) {
            QuranResponse.Verse quranVerse = entry.getValue();
            Verse verse = new Verse(quranVerse.getIdAyah(), quranVerse.getContent(), quranVerse.getTranslationEng(), quranVerse.getTransliteration());
            verses.add(verse);
        }

        SurahDetailFragment surahDetailFragment = SurahDetailFragment.newInstance(surahId, surahName, surahTranslation, verses);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, surahDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}

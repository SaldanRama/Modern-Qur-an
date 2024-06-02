package com.example.modernquran.API;

import com.example.modernquran.Model.QuranResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface QuranApiService {
    @GET("{chapterId}")
    Call<QuranResponse> getSurah(
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String apiHost,
            @Path("chapterId") int chapterId
    );
}






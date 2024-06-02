package com.example.modernquran.Model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Verse implements Parcelable {

    @SerializedName("id")
    private float id;

    @SerializedName("content")
    private String content;

    @SerializedName("translation_eng")
    private String translationEng;

    @SerializedName("transliteration")
    private String transliteration;

    // Empty constructor
    public Verse() {
    }

    // Constructor with parameters
    public Verse(float id, String content, String translationEng, String transliteration) {
        this.id = id;
        this.content = content;
        this.translationEng = translationEng;
        this.transliteration = transliteration;
    }

    protected Verse(Parcel in) {
        id = in.readFloat();
        content = in.readString();
        translationEng = in.readString();
        transliteration = in.readString();
    }

    public static final Creator<Verse> CREATOR = new Creator<Verse>() {
        @Override
        public Verse createFromParcel(Parcel in) {
            return new Verse(in);
        }

        @Override
        public Verse[] newArray(int size) {
            return new Verse[size];
        }
    };

    // Mengonversi float ID ke int
    public int getIdAyahAsInt() {
        return (int) id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTranslationEng() {
        return translationEng;
    }

    public void setTranslationEng(String translationEng) {
        this.translationEng = translationEng;
    }

    public String getTransliteration() {
        return transliteration;
    }

    public void setTransliteration(String transliteration) {
        this.transliteration = transliteration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(id);
        dest.writeString(content);
        dest.writeString(translationEng);
        dest.writeString(transliteration);
    }
}

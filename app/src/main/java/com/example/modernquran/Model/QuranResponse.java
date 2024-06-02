package com.example.modernquran.Model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.Map;
import java.util.Objects;

public class QuranResponse implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("surah_name")
    private String surahName;

    @SerializedName("translation")
    private String translation;

    @SerializedName("verses")
    private Map<Integer, Verse> verses;

    // Empty constructor
    public QuranResponse() {
    }

    // Constructor with parameters
    public QuranResponse(int id, String surahName, String translation, Map<Integer, Verse> verses) {
        this.id = id;
        this.surahName = surahName;
        this.translation = translation;
        this.verses = verses;
    }

    protected QuranResponse(Parcel in) {
        id = in.readInt();
        surahName = in.readString();
        translation = in.readString();
        verses = in.readHashMap(Verse.class.getClassLoader());
    }

    public static final Creator<QuranResponse> CREATOR = new Creator<QuranResponse>() {
        @Override
        public QuranResponse createFromParcel(Parcel in) {
            return new QuranResponse(in);
        }

        @Override
        public QuranResponse[] newArray(int size) {
            return new QuranResponse[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurahName() {
        return surahName;
    }

    public void setSurahName(String surahName) {
        this.surahName = surahName;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Map<Integer, Verse> getVerses() {
        return verses;
    }

    public void setVerses(Map<Integer, Verse> verses) {
        this.verses = verses;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuranResponse that = (QuranResponse) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(surahName);
        dest.writeString(translation);
        dest.writeMap(verses);
    }

    public static class Verse implements Parcelable {

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

        public float getIdAyah() {
            return id;
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
}

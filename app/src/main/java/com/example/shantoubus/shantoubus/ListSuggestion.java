package com.example.shantoubus.shantoubus;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by SuperL on 2017-3-19.
 */
public class ListSuggestion implements SearchSuggestion {

    private String mColorName;
    private boolean mIsHistory = false;

    public ListSuggestion(String suggestion) {
        this.mColorName = suggestion.toLowerCase();
    }

    public ListSuggestion(Parcel source) {
        this.mColorName = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return mColorName;
    }

    public static final Creator<ListSuggestion> CREATOR = new Creator<ListSuggestion>() {
        @Override
        public ListSuggestion createFromParcel(Parcel in) {
            return new ListSuggestion(in);
        }

        @Override
        public ListSuggestion[] newArray(int size) {
            return new ListSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mColorName);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}

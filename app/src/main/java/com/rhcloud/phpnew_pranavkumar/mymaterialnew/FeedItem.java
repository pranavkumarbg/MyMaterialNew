package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.content.Context;

/**
 * Created by Pranav on 8/14/2015.
 */
public class FeedItem {
    private String thumbnail;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getImageResourceId(Context context) {
        return context.getResources().getIdentifier(this.thumbnail, "drawable", context.getPackageName());
    }

}

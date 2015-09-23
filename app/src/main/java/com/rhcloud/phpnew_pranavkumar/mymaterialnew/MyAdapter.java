package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pranav on 8/14/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();
    private Context mContext;
    OnItemClickListener mItemClickListener;


    public MyAdapter(Context applicationContext, ArrayList<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = applicationContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.newcard, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        try
        {
            final String url = feedItemList.get(position).getThumbnail();

//            YoYo.with(Techniques.Pulse)
//                    .duration(1000)
//                    .playOn(holder.placeImage);
            //Glide.with(mContext).load(url).centerCrop().into(holder.placeImage);

           // String s="http://2.bp.blogspot.com/-6oNTuKj2y1I/VDW1uaZUu3I/AAAAAAAALDc/tJM0s5p1-5o/s1600/Untitled-1.jpg";


            Glide.with(mContext)
                    .load(url)
                    .asBitmap()
                   // .diskCacheStrategy(DiskCacheStrategy.ALL)
                    //.thumbnail(0.1f)
                .into(new BitmapImageViewTarget(holder.placeImage) {

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);


                        YoYo.with(Techniques.Pulse)
                                .duration(1000)
                                .playOn(holder.placeImage);
                        Palette.generateAsync(resource, new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {

                                int mutedLight = palette.getVibrantColor(mContext.getResources().getColor(android.R.color.black));
                                holder.placeNameHolder.setBackgroundColor(mutedLight);
                            }
                        });

                        holder.progressBar.setVisibility(View.GONE);
                    }
                });


        }
        catch (Exception e)
        {

        }


    }


    @Override
    public int getItemCount() {
        return feedItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        public TextView placeName;
        public ImageView placeImage;
        ProgressBar progressBar;
        public ViewHolder(View itemView) {
            super(itemView);

            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeName = (TextView) itemView.findViewById(R.id.placeName);
            placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
            placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
            progressBar=(ProgressBar)itemView.findViewById(R.id.image_details_loader_new);
            placeHolder.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}

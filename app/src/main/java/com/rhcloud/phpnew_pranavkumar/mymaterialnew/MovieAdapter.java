package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

/**
 * Created by Pranav on 9/2/2015.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pranav on 8/14/2015.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> implements Filterable {

    private List<MovieData> feedMovieList = new ArrayList<MovieData>();
    private List<MovieData> feedMovieListnew = new ArrayList<MovieData>();
    private Context mContext;
    OnItemClickListener mItemClickListener;

    public MovieAdapter(Context applicationContext, ArrayList<MovieData> feedMovieList) {
        this.feedMovieList = feedMovieList;
        this.mContext = applicationContext;
        feedMovieListnew=feedMovieList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.movielist, parent, false);
        return new ViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        try
        {
            Log.d("inside","onbinder");
            String url = feedMovieList.get(position).getMoviethumbnail();
           // final MovieData place = feedItemList.get(position);
            //holder.placeName.setText("Movie");
            //Glide.with(mContext).load(url).into(holder.placeImage);

            String name=feedMovieList.get(position).getMoviename();

           // Toast.makeText(mContext, url, Toast.LENGTH_LONG).show();
            holder.placeName.setText(name);
            //final PaletteTransformation paletteTransformation = new PaletteTransformation();
            final PaletteTransformation paletteTransformation = PaletteTransformation.getInstance();
//            Picasso.with(mContext)
//                    .load(url)
//                    .fit().centerCrop()
//                    .transform(paletteTransformation)
//                    .into(holder.placeImage, new Callback.EmptyCallback() {
//                        @Override
//                        public void onSuccess() {
//                            Palette palette = paletteTransformation.getPalette();
//                            // TODO apply palette to text views, backgrounds, etc.
//                            int mutedLight = palette.getMutedColor(mContext.getResources().getColor(android.R.color.black));
//                            holder.placeNameHolder.setBackgroundColor(mutedLight);
//                        }
//                    });

            Picasso.with(mContext)
                    .load(url)
                    .transform(PaletteTransformation.instance())
                    .into(holder.placeImage, new Callback.EmptyCallback() {
                        @Override public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) holder.placeImage.getDrawable()).getBitmap(); // Ew!
                            Palette palette = PaletteTransformation.getPalette(bitmap);
                            // TODO apply palette to text views, backgrounds, etc.


                            int mutedLight = palette.getVibrantColor(mContext.getResources().getColor(android.R.color.black));
                            holder.placeNameHolder.setBackgroundColor(mutedLight);
//                            int h=bitmap.getHeight();
//                            int w=bitmap.getWidth();
//                            String hi=Integer.toString(h);
//                            String wi=Integer.toString(w);

                           // holder.placeName.setText(hi+"X"+wi);
                        }
                    });



        }
        catch (Exception e)
        {

        }


    }


    @Override
    public int getItemCount() {
        return feedMovieList.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                feedMovieListnew= (ArrayList<MovieData>) results.values;
                //notifyDataSetChanged();
                MovieAdapter movieAdapter=new MovieAdapter(mContext, (ArrayList<MovieData>) feedMovieListnew);
                movieAdapter.notifyDataSetChanged();
               //mRecyclerView.setAdapter(mAdapter);
                Log.i("kk","changes"+feedMovieListnew);
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<MovieData> FilteredList= new ArrayList<MovieData>();
                if (constraint == null || constraint.length() == 0) {
                    // No filter implemented we return all the list
                    results.values = feedMovieList;
                    results.count = feedMovieList.size();
                }
                else {
                    for (int i = 0; i < feedMovieList.size(); i++) {
                        String mname = feedMovieList.get(i).getMoviename();


                        if (mname.toLowerCase().contains(constraint.toString()))  {

                            Log.i("found",constraint.toString());
                            MovieData item1 = new MovieData();

                            item1.setMoviename(feedMovieList.get(i).getMoviename());
                            item1.setMoviethumbnail(feedMovieList.get(i).getMoviethumbnail());
                            item1.setMovieurl(feedMovieList.get(i).getMovieurl());

                            FilteredList.add(item1);
                        }
                    }
                    results.values = FilteredList;
                    results.count = FilteredList.size();
                }
                return results;
            }
        };
        return filter;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public LinearLayout placeHolder;
        public LinearLayout placeNameHolder;
        public TextView placeName;
        public ImageView placeImage;

        public ViewHolder(View itemView) {
            super(itemView);


            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            placeName = (TextView) itemView.findViewById(R.id.placeName);
            placeNameHolder = (LinearLayout) itemView.findViewById(R.id.placeNameHolder);
            placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
            placeImage.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
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


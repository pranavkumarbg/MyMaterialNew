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
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<MovieData> feedMovieList = new ArrayList<MovieData>();
    private Context mContext;


    public MovieAdapter(Context applicationContext, ArrayList<MovieData> feedMovieList) {
        this.feedMovieList = feedMovieList;
        this.mContext = applicationContext;

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

            Toast.makeText(mContext, url, Toast.LENGTH_LONG).show();
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
                    .fit().centerCrop()
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

    class ViewHolder extends RecyclerView.ViewHolder {


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



        }

    }


}


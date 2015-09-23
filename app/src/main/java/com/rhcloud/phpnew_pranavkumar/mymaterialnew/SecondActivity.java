package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rhcloud.phpnew_pranavkumar.mymaterialnew.gcm.RegistrationIntentService;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Pranav on 9/1/2015.
 */
public class SecondActivity extends AppCompatActivity {
    LinearLayout placeNameHolder;
    ImageView imageView;
    String flag;
    ProgressBar loader;
    Button b;
    private PhotoViewAttacher photoViewAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        b=(Button)findViewById(R.id.buttondown);
        //progressBar=(ProgressBar)findViewById(R.id.progressBarsecact);
        //progressBar.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loader = (ProgressBar) findViewById(R.id.image_details_loader);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        imageView=(ImageView)findViewById(R.id.imagesec);
        placeNameHolder= (LinearLayout)findViewById(R.id.placeNameHolderkk);
        Intent i = getIntent();

        flag= i.getStringExtra("flag");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FileDownloadService.class);
                startService(intent);
                Toast.makeText(getApplicationContext(),"service started",Toast.LENGTH_LONG).show();
            }
        });
        //new StartActivity().execute();

        //Glide.with(this).load(flag).into(imageView);



        Glide.with(getApplicationContext())
                .load(flag)

                .fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e,
                                               String model,
                                               Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        hideLoader();
                        //TODO: maybe show a retry button?
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource,
                                                   String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {

                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

//                        renderTags(
//                                ((GlideBitmapDrawable) resource.getCurrent())
//                                        .getBitmap()
//                        );
//                        renderColors(
//                                ((GlideBitmapDrawable) resource.getCurrent())
//                                        .getBitmap()
//                        );
//                        imageView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                //toggleZoomImage();
//
//                                //Toast.makeText(getApplicationContext(),"clicked image",Toast.LENGTH_LONG).show();
//                            }
//                        });
                        hideLoader();

//                        Size size = fitToWidthAndKeepRatio(
//                                imagePage.getImageWidth(),
//                                imagePage.getImageHeight()
//                        );
//
//                        imageSize = size;



                        return false;
                    }
                })
                .into(imageView);



        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fabnewl);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                photoViewAttacher = new PhotoViewAttacher(imageView);
//                photoViewAttacher.setZoomable(true);
//                photoViewAttacher.setScaleType(ImageView.ScaleType.CENTER_CROP);

                Intent transitionIntent = new Intent(SecondActivity.this, ThirdActivity.class);

                transitionIntent.putExtra("flaghh", flag);
                //startActivity(transitionIntent);
                //Toast.makeText(getActivity(),"pos"+position,Toast.LENGTH_LONG).show();

                //ImageView placeImage = (ImageView) v.findViewById(R.id.imagesec);
                //LinearLayout placeNameHolder = (LinearLayout)v.findViewById(R.id.placeNameHolderkk);
// 2
                Pair<View, String> imagePair = Pair.create((View) imageView, "tImage");
                Pair<View, String> holderPair = Pair.create((View) placeNameHolder, "tNameHolder");
// 3
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SecondActivity.this,
                        imagePair, holderPair);
                ActivityCompat.startActivity(SecondActivity.this, transitionIntent, options.toBundle());

            }
        });


    }

    private class StartActivity extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Glide.with(SecondActivity.this).load(flag).into(imageView);

           // progressBar.setVisibility(View.GONE);

        }
    }

    private void showLoader() {
        loader.animate().alpha(1.0f).setDuration(300).start();
    }

    private void hideLoader() {
        loader.animate().alpha(0.0f).setDuration(300).start();
    }


}

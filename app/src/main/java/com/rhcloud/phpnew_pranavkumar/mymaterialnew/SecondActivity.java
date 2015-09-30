package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Pranav on 9/1/2015.
 */
public class SecondActivity extends AppCompatActivity {
    LinearLayout placeNameHolder;
    ImageView imageView;
    String flag;
    ProgressBar loader;
    Button b,b1;
    private PhotoViewAttacher photoViewAttacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        b=(Button)findViewById(R.id.btn_save);
        b1=(Button)findViewById(R.id.btn_set);
        //progressBar=(ProgressBar)findViewById(R.id.progressBarsecact);
        //progressBar.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.mipmap.pop);
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
                intent.putExtra("image",flag);
                startService(intent);
                Toast.makeText(getApplicationContext(),"service started",Toast.LENGTH_LONG).show();
            }
        });
        //new StartActivity().execute();

        //Glide.with(this).load(flag).into(imageView);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MybitmapDownloaderTask().execute(flag);

            }
        });

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


                Intent transitionIntent = new Intent(SecondActivity.this, ThirdActivity.class);

                transitionIntent.putExtra("flaghh", flag);

                Pair<View, String> imagePair = Pair.create((View) imageView, "tImage");
                Pair<View, String> holderPair = Pair.create((View) placeNameHolder, "tNameHolder");

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SecondActivity.this,
                        imagePair, holderPair);
                ActivityCompat.startActivity(SecondActivity.this, transitionIntent, options.toBundle());

            }
        });


    }



    private void showLoader() {
        loader.animate().alpha(1.0f).setDuration(300).start();
    }

    private void hideLoader() {
        loader.animate().alpha(0.0f).setDuration(300).start();
    }


    class MybitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private String url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //spinner.setVisibility(View.VISIBLE);
            loader.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            return DownloadUtil.downloadBitmap(url);
        }

        /**
         * Once the image is downloaded, associates it to the imageView
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            // get the height and width of screen
            int height = metrics.heightPixels;
            int width = metrics.widthPixels;
            // Retrieve a WallpaperManager
            WallpaperManager myWallpaperManager = WallpaperManager.getInstance(SecondActivity.this);
            try {
                myWallpaperManager.setBitmap(bitmap);

//                File root = Environment.getExternalStorageDirectory();
//                File file = new File(root.getAbsolutePath()+"/saved_images");
//                // File myDir=new File("/sdcard/saved_images");
//                file.mkdirs();
//                Random generator = new Random();
//                int n = 10000;
//                n = generator.nextInt(n);
//                String fname = "Image-"+ n +".jpg";
//                File file1= new File(file, fname);
//                if (file1.exists ()) file1.delete ();
//                try {
//                    FileOutputStream out = new FileOutputStream(file1);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//                    out.flush();
//                    out.close();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            myWallpaperManager.suggestDesiredDimensions(height, width);


            loader.setVisibility(View.GONE);
           // spinner.setVisibility(View.GONE);
            // Show a toast message on successful change
            Toast.makeText(SecondActivity.this, "Wallpaper successfully changed", Toast.LENGTH_SHORT).show();
        }
    }



}

package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

/**
 * Created by Pranav on 9/1/2015.
 */
public class SecondActivity extends AppCompatActivity {
    LinearLayout placeNameHolder;
    ImageView imageView;
    String flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Second Activity");
        imageView=(ImageView)findViewById(R.id.imagesec);
        placeNameHolder= (LinearLayout)findViewById(R.id.placeNameHolderkk);
        Intent i = getIntent();

        flag= i.getStringExtra("flag");

        Glide.with(this).load(flag).into(imageView);


        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fabnew);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
}

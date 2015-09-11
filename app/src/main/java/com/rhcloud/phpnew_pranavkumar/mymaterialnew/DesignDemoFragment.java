package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import at.grabner.circleprogress.CircleProgressView;

/**
 * Created by Pranav on 9/1/2015.
 */
public class DesignDemoFragment extends Fragment {
    private static final String TAB_POSITION = "tab_position";
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    RecyclerView.LayoutManager mLayoutManager;
    MyAdapter mAdapter;
    JSONObject jsonobject;
    JSONArray jsonarray;
    String b;
    String json;
    SpacesItemDecoration decoration;
    private boolean isListView;
    private ArrayList<FeedItem> feedItemList = new ArrayList<FeedItem>();
    CircleProgressView mCircleView;

    public DesignDemoFragment() {

    }

    public static DesignDemoFragment newInstance(int tabPosition) {
        DesignDemoFragment fragment = new DesignDemoFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_POSITION, tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        int tabPosition = args.getInt(TAB_POSITION);
        //TextView tv = new TextView(getActivity());
       // tv.setGravity(Gravity.CENTER);
        //tv.setText("Text in Tab #" + tabPosition);
        //return tv;

        String p=Integer.toString(tabPosition);

        //Toast.makeText(getActivity(),p,Toast.LENGTH_LONG).show();

        View layout = inflater.inflate(R.layout.fragment_grid_view, container, false);

        progressBar=(ProgressBar)layout.findViewById(R.id.progressBar2);
//        mCircleView = (CircleProgressView)layout. findViewById(R.id.circleView);
//        mCircleView.spin();
//        mCircleView.setMaxValue(100);
//        mCircleView.setValue(0);
//        mCircleView.setValueAnimated(24);
        progressBar.setVisibility(View.GONE);
        decoration = new SpacesItemDecoration(16);
        new DownloadJSON().execute();

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
       // mRecyclerView.addItemDecoration(decoration);
        // The number of Columns
        //mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        // StaggeredGridLayoutManager mLayoutManager1 = new StaggeredGridLayoutManager(2,1);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);


        return layout;
    }

    private class DownloadJSON extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public String doInBackground(String... params) {



            //  String json = JSONfunctions.getJSONfromURL("http://newjson-pranavkumar.rhcloud.com/GridViewJson");

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url("http://newjson-pranavkumar.rhcloud.com/GridViewJson")
                    .build();


            try {
                Response response = okHttpClient.newCall(request).execute();

                json = response.body().string();


                JSONObject reader = new JSONObject(json);
                jsonarray = reader.getJSONArray("images");

                for (int i = 0; i < jsonarray.length(); i++) {

                    jsonobject = jsonarray.getJSONObject(i);

                    FeedItem item = new FeedItem();

                    item.setThumbnail(jsonobject.optString("image"));
                    feedItemList.add(item);

                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {

            }
            return json;
        }

        @Override
        protected void onPostExecute(String args) {

            if (args != null && !args.isEmpty()) {

                mAdapter = new MyAdapter(getActivity(), feedItemList);

                mRecyclerView.setAdapter(mAdapter);

                //mCircleView.stopSpinning();
                progressBar.setVisibility(View.GONE);
               // mRecyclerView.addItemDecoration(decoration);
                mAdapter.setOnItemClickListener(onItemClickListener);

            }
            else
            {
                Toast.makeText(getActivity(),"no internet or server is down",Toast.LENGTH_LONG).show();

            }


        }
    }

   MyAdapter.OnItemClickListener onItemClickListener=new MyAdapter.OnItemClickListener()
   {

       @Override
       public void onItemClick(View view, int position) {
           Intent transitionIntent = new Intent(getActivity(), SecondActivity.class);
           String url=feedItemList.get(position).getThumbnail();
           transitionIntent.putExtra("flag", url);
           //startActivity(transitionIntent);
           //Toast.makeText(getActivity(),"pos"+position,Toast.LENGTH_LONG).show();

           ImageView placeImage = (ImageView) view.findViewById(R.id.placeImage);
           LinearLayout placeNameHolder = (LinearLayout)view.findViewById(R.id.placeNameHolder);
// 2
           Pair<View, String> imagePair = Pair.create((View) placeImage, "tImage");
           Pair<View, String> holderPair = Pair.create((View) placeNameHolder, "tNameHolder");
// 3
           ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                   imagePair, holderPair);

           ActivityCompat.startActivity(getActivity(), transitionIntent, options.toBundle());
       }
   };



}

package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pranav on 9/2/2015.
 */
public class MoviesDemoFragment extends Fragment {
    private static final String TAB_POSITION = "tab_position";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MovieAdapter mAdapter;
    JSONObject jsonobject;
    JSONArray jsonarray;
    String b;
    String json;
    MovieData item;
    private ArrayList<MovieData> feedMovieList = new ArrayList<MovieData>();

    public MoviesDemoFragment() {

    }

    public static MoviesDemoFragment newInstance(int tabPosition) {
        MoviesDemoFragment fragment = new MoviesDemoFragment();
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

        View layout = inflater.inflate(R.layout.fragment_movie, container, false);

        new DownloadJSON().execute();

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerviewk);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(getActivity(), 1);

        // StaggeredGridLayoutManager mLayoutManager1 = new StaggeredGridLayoutManager(2,1);
        mRecyclerView.setLayoutManager(mLayoutManager);



        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class DownloadJSON extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        public String doInBackground(String... params) {


            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url("http://moviejson-pranavkumar.rhcloud.com/newmoviejson")
                    .build();


            try {
                Response response = okHttpClient.newCall(request).execute();

                json = response.body().string();


                JSONObject reader = new JSONObject(json);
                jsonarray = reader.getJSONArray("movies");

                for (int i = 0; i < jsonarray.length(); i++) {

                    jsonobject = jsonarray.getJSONObject(i);

                    MovieData item = new MovieData();


                    item.setMoviename(jsonobject.optString("moviename"));
                    item.setMoviethumbnail(jsonobject.optString("thumbnail"));
                    item.setMovieurl(jsonobject.optString("url"));

                    feedMovieList.add(item);

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

                mAdapter = new MovieAdapter(getActivity(), feedMovieList);

                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(onItemClickListener);

            }
            else
            {
                Toast.makeText(getActivity(), "no internet or server is down", Toast.LENGTH_LONG).show();

            }


        }
    }

    MovieAdapter.OnItemClickListener onItemClickListener=new MovieAdapter.OnItemClickListener()
    {

        @Override
        public void onItemClick(View view, int position) {
            Intent transitionIntent = new Intent(getActivity(), MovieActivity.class);
            String url=feedMovieList.get(position).getMovieurl();
            Toast.makeText(getActivity(),url,Toast.LENGTH_LONG).show();
            transitionIntent.putExtra("flag", url);
            startActivity(transitionIntent);
            //Toast.makeText(getActivity(),"clicked"+position+url,Toast.LENGTH_LONG).show();

        }
    };
}


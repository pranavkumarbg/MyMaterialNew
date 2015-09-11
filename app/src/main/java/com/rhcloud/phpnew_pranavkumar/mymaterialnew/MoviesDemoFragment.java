package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
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

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Movies");
        query.whereExists("url");
        query.whereExists("thumbnail");
        query.whereExists("movie");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {

                    if (list.size() > 0) {

                        for (int i = 0; i < list.size(); i++) {
                            ParseObject p = list.get(i);
                            String urlget = p.getString("url");
                            String thumb = p.getString("thumbnail");
                            String name = p.getString("movie");
//                                    Toast.makeText(getActivity(), urlget, Toast.LENGTH_LONG).show();
//                                    Toast.makeText(getActivity(), thumb, Toast.LENGTH_LONG).show();
//                                    Toast.makeText(getActivity(), name, Toast.LENGTH_LONG).show();
                            item = new MovieData();

                            item.setMovieurl(urlget);
                            item.setMoviethumbnail(thumb);
                            item.setMoviename(name);

                            feedMovieList.add(item);
                        }

                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });


       // new DownloadJSON().execute();

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerviewk);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(getActivity(), 1);

        // StaggeredGridLayoutManager mLayoutManager1 = new StaggeredGridLayoutManager(2,1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MovieAdapter(getActivity(), feedMovieList);

        mRecyclerView.setAdapter(mAdapter);

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




            try {




               // feedMovieList.add(item);





            } catch (Exception e) {

            }
            return json;
        }

        @Override
        protected void onPostExecute(String args) {

           // if (args != null && !args.isEmpty()) {

//                mAdapter = new MovieAdapter(getActivity(), feedMovieList);
//
//                mRecyclerView.setAdapter(mAdapter);


//            }
//            else
//            {
//                Toast.makeText(getActivity(), "no internet or server is down", Toast.LENGTH_LONG).show();
//
//            }


        }
    }
}


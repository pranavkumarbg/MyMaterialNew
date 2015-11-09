package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.widget.Filter;

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
    SearchView searchView;
    ProgressBar progressBar;
    private ArrayList<MovieData> feedMovieList = new ArrayList<MovieData>();

    private ArrayList<MovieData> feedMovieListnext = new ArrayList<MovieData>();

    public MoviesDemoFragment() {

    }

    public static MoviesDemoFragment newInstance(int tabPosition) {
        MoviesDemoFragment fragment = new MoviesDemoFragment();
        Bundle args = new Bundle();
        args.putInt(TAB_POSITION, tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (isNetworkAvailable(getActivity())) {
            // code here
        } else {
            // code
            //Toast.makeText(getActivity(), "check internet connection", Toast.LENGTH_LONG).show();
        }
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

        //searchView = (SearchView) layout.findViewById(R.id.search);

        progressBar=(ProgressBar)layout.findViewById(R.id.progressmovie);

        if (isNetworkAvailable(getActivity())) {
            // code here
            new DownloadJSON().execute();
        } else {
            // code

            //Toast.makeText(getActivity(), "check internet connection", Toast.LENGTH_LONG).show();

        }


        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerviewk);
        mRecyclerView.setHasFixedSize(true);

        progressBar.setVisibility(View.GONE);
        // The number of Columns
        mLayoutManager = new GridLayoutManager(getActivity(), 1);

        // StaggeredGridLayoutManager mLayoutManager1 = new StaggeredGridLayoutManager(2,1);
        mRecyclerView.setLayoutManager(mLayoutManager);



//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //Toast.makeText(MainActivity.this, R.string.submitted, Toast.LENGTH_SHORT).show();
//                //se oculta el EditText
//                searchView.setQuery("", false);
//                searchView.setIconified(true);
//
//                return true;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                //mAdapter.getFilter().filter(newText);
//               // mRecyclerView.setAdapter(mAdapter);
//
//                if (newText == null || newText.length() == 0) {
//
//                    feedMovieListnext=feedMovieList;
//                    mAdapter = new MovieAdapter(getActivity(), feedMovieList);
//
//                    mRecyclerView.setAdapter(mAdapter);
//                    mAdapter.setOnItemClickListener(onItemClickListener);
//                }
//                else {
//                    for (int i = 0; i < feedMovieList.size(); i++) {
//                        if (feedMovieList.get(i).getMoviename().toLowerCase().contains(newText)) {
//                            //setDescription(values.getString(i));
//                            //Toast.makeText(getActivity(), "found", Toast.LENGTH_SHORT).show();
//                            MovieData item1 = new MovieData();
//                            ArrayList<MovieData> feedMovieListnew = new ArrayList<MovieData>();
//
//                            item1.setMoviename(feedMovieList.get(i).getMoviename());
//                            item1.setMoviethumbnail(feedMovieList.get(i).getMoviethumbnail());
//                            item1.setMovieurl(feedMovieList.get(i).getMovieurl());
//
//                            feedMovieListnew.add(item1);
//                            feedMovieListnext=feedMovieListnew;
//
//                            mAdapter = new MovieAdapter(getActivity(), feedMovieListnew);
//
//                            mRecyclerView.setAdapter(mAdapter);
//                            mAdapter.setOnItemClickListener(onItemClickListener);
//
//
//                        } else {
//                            //Toast.makeText(getActivity(), "not found", Toast.LENGTH_SHORT).show();
//
////                            mAdapter = new MovieAdapter(getActivity(), feedMovieList);
////
////                            mRecyclerView.setAdapter(mAdapter);
////                            mAdapter.setOnItemClickListener(onItemClickListener);
//
//                        }
//                    }
//                }
//
//                return true;
//            }
//        });

        return layout;
    }



    @Override
    public void onResume() {
        super.onResume();

        if (isNetworkAvailable(getActivity())) {
            // code here
        } else {
            // code
            //Toast.makeText(getActivity(), "check internet connection", Toast.LENGTH_LONG).show();
        }
    }




    private class DownloadJSON extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public String doInBackground(String... params) {




            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url("http://moviejson-pranavkumar.rhcloud.com/newmoviejson")
                    .build();
            Call call=okHttpClient.newCall(request);


//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Request request, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Response response) throws IOException {
//
//                    try {
//
//
//                        if(response.isSuccessful())
//                        {
//
//                            //Log.v("okHTTP",response.body().toString());
//                            json = response.body().string();
//
//                            JSONObject reader = new JSONObject(json);
//                            jsonarray = reader.getJSONArray("movies");
//
//                            for (int i = 0; i < jsonarray.length(); i++) {
//
//                                jsonobject = jsonarray.getJSONObject(i);
//
//                                MovieData item = new MovieData();
//
//
//                                item.setMoviename(jsonobject.optString("moviename"));
//                                item.setMoviethumbnail(jsonobject.optString("thumbnail"));
//                                item.setMovieurl(jsonobject.optString("url"));
//
//                                //Log.v("okHTTP", jsonobject.optString("moviename"));
//                                feedMovieList.add(item);
//
//                            }
//                        }
//                    }
//                    catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });

            try {
               // Response response = okHttpClient.newCall(request).execute();

                Response response=call.execute();

                if(response.isSuccessful())
                {
                    Log.v("okHTTP",response.body().toString());
                }



                json = response.body().string();


                JSONObject reader = new JSONObject(json);
                jsonarray = reader.getJSONArray("movies");

                for (int i = 0; i < jsonarray.length(); i++) {

                    jsonobject = jsonarray.getJSONObject(i);

                    MovieData item = new MovieData();


                    item.setMoviename(jsonobject.optString("moviename"));
                    item.setMoviethumbnail(jsonobject.optString("thumbnail"));
                    item.setMovieurl(jsonobject.optString("url"));

                    //Log.v("okHTTP",jsonobject.optString("moviename"));
                    feedMovieList.add(item);

                }
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            catch (Exception io)
            {
                io.printStackTrace();
            }

            //Log.v("okHTTP",json);
            return json;
        }

        @Override
        protected void onPostExecute(String args) {

            //if (args != null && !args.isEmpty()) {

                feedMovieListnext=feedMovieList;

                Log.v("okHTTP",feedMovieList.toString());
                mAdapter = new MovieAdapter(getActivity(), feedMovieList);

                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(onItemClickListener);
                //Toast.makeText(getActivity(), "done", Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);
//            }
//            else
//            {
//                Toast.makeText(getActivity(), "no internet or server is down", Toast.LENGTH_LONG).show();
//
//            }


        }
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    MovieAdapter.OnItemClickListener onItemClickListener=new MovieAdapter.OnItemClickListener()
    {

        @Override
        public void onItemClick(View view, int position) {
//            Intent transitionIntent = new Intent(getActivity(), MovieNewTest.class);
//           String url=feedMovieList.get(position).getMovieurl();
//            Toast.makeText(getActivity(),url,Toast.LENGTH_LONG).show();
//            transitionIntent.putExtra("flag", url);
//            startActivity(transitionIntent);
            //Toast.makeText(getActivity(),"clicked"+position+url,Toast.LENGTH_LONG).show();

            Intent transitionIntent = new Intent(getActivity(), MovieSecondActivity.class);


            String url=feedMovieListnext.get(position).getMovieurl();
            String image=feedMovieListnext.get(position).getMoviethumbnail();
            //Toast.makeText(getActivity(),url,Toast.LENGTH_LONG).show();
            transitionIntent.putExtra("flagurl", url);
            transitionIntent.putExtra("flagimage",image);
            startActivity(transitionIntent);

        }
    };
}


package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
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

import com.rhcloud.phpnew_pranavkumar.mymaterialnew.adapters.SimpleCursorRecyclerAdapter;
import com.rhcloud.phpnew_pranavkumar.mymaterialnew.data.MyContract;
import com.rhcloud.phpnew_pranavkumar.mymaterialnew.sync.SyncAdapter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import at.grabner.circleprogress.CircleProgressView;

/**
 * Created by Pranav on 9/1/2015.
 */
public class DesignDemoFragment extends Fragment implements  android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAB_POSITION = "tab_position";
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    RecyclerView.LayoutManager mLayoutManager;
    MyAdapter mAdapter;
    SimpleCursorRecyclerAdapter mAdapternew;
    JSONObject jsonobject;
    JSONArray jsonarray;
    String b;
    AlarmManager alarmManager;
    String json;
    Cursor c;
    Context context;


    String[] mProjection =
            {
                    //MyContract.MyEntry._ID,    // Contract class constant for the _ID column name
                    MyContract.MyEntry.COLUMN_IMAGE,   // Contract class constant for the word column name
                    // Contract class constant for the locale column name
            };

    // Defines a string to contain the selection clause
    String mSelectionClause = null;

    // Initializes an array to contain selection arguments
    String[] mSelectionArgs = {""};

    private Cursor mDetailCursor;
    private View mRootView;
    private int mPosition;
    private boolean mFirstRun = true;

    private Uri mUri;
    private static final int CURSOR_LOADER_ID = 0;

//    public static final long SECONDS_PER_MINUTE = 60;
//    public static final long SYNC_INTERVAL_IN_MINUTES = 1;
//    public static final long SYNC_INTERVAL =
//            SYNC_INTERVAL_IN_MINUTES *
//                    SECONDS_PER_MINUTE;
//    SpacesItemDecoration decoration;
//    private boolean isListView;
    private ArrayList<FeedItem> feedItemList = new ArrayList<FeedItem>();
    FeedItem item;

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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        c = getActivity().getContentResolver().query(MyContract.MyEntry.CONTENT_URI,
                new String[]{MyContract.MyEntry._ID},
                null,
                null,
                null);

        if (c.getCount() == 0) {
            Log.i("my", "first time");

            //insertData();
            //Log.i("insertdata", "insertdata");
           // SyncAdapter.initializeSyncAdapter(getActivity());
           // SyncAdapter.syncImmediately(getActivity());

            new DownloadJSONAda().execute();

            new DownloadJSON().execute();
            //Log.i("my", "first time");


        }
        // initiale loader

        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

        Log.i("my", "second time");
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Bundle args = getArguments();
        //int tabPosition = args.getInt(TAB_POSITION);
        //TextView tv = new TextView(getActivity());
        // tv.setGravity(Gravity.CENTER);
        //tv.setText("Text in Tab #" + tabPosition);
        //return tv;
       // Bundle args = this.getArguments();


        //String p = Integer.toString(tabPosition);

        //Toast.makeText(getActivity(),p,Toast.LENGTH_LONG).show();

        View layout = inflater.inflate(R.layout.fragment_grid_view, container, false);

       // Log.i("my", "onviewcreated");


//        progressBar=(ProgressBar)layout.findViewById(R.id.progressBar2);
////        mCircleView = (CircleProgressView)layout. findViewById(R.id.circleView);
////        mCircleView.spin();
////        mCircleView.setMaxValue(100);
////        mCircleView.setValue(0);
////        mCircleView.setValueAnimated(24);
//        progressBar.setVisibility(View.GONE);
//        decoration = new SpacesItemDecoration(16);
//        new DownloadJSON().execute();
//
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview);
//        mRecyclerView.setHasFixedSize(true);
//       // mRecyclerView.addItemDecoration(decoration);
//        // The number of Columns
//        //mLayoutManager = new GridLayoutManager(getActivity(), 2);
//        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
//
//        // StaggeredGridLayoutManager mLayoutManager1 = new StaggeredGridLayoutManager(2,1);
//        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
       // mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        // StaggeredGridLayoutManager mLayoutManager1 = new StaggeredGridLayoutManager(2,1);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        mAdapternew = new SimpleCursorRecyclerAdapter(getActivity(), null, 0, CURSOR_LOADER_ID);

        mRecyclerView.setAdapter(mAdapternew);
        mAdapternew.setOnItemClickListener(onItemClickListener);


        Log.i("my", "cursor adapter set");

        // new DownloadJSON().execute();




       // getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

        return layout;
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        return new CursorLoader(getActivity(), MyContract.MyEntry.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {


       mAdapternew.swapCursor(data);

      // mRecyclerView.setAdapter(mAdapternew);
        //getActivity().getContentResolver().notifyChange();
       // mAdapternew.notifyDataSetChanged();


    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapternew.swapCursor(null);
    }



    SimpleCursorRecyclerAdapter.OnItemClickListener onItemClickListener = new SimpleCursorRecyclerAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View view, int position, Cursor cursor) {
            Intent transitionIntent = new Intent(getActivity(), SecondActivity.class);
            String title=null;
            //String url = feedItemList.get(position).getThumbnail();
            //cursor.moveToPrevious();
           // cursor.move(position);
            //int versionIndex = cursor.getColumnIndex(MyContract.MyEntry.COLUMN_IMAGE);

            //Log.i("imageshiiiiiiii", "hi" + versionIndex);
            //cursor.moveToFirst();
            //cursor.moveToPosition(position);

            if (cursor.moveToPosition(position)) {
                title = cursor.getString(cursor.getColumnIndex(MyContract.MyEntry.COLUMN_IMAGE));

            }
//            int a=cursor.getInt(position);
//            Toast.makeText(getActivity(),"pos"+a, Toast.LENGTH_LONG).show();
//
//            cursor.moveToPosition(a);
//            final String versionName = cursor.getString(a);
            transitionIntent.putExtra("flag",title );
            //startActivity(transitionIntent);
            Toast.makeText(getActivity(), title+"pos" + position, Toast.LENGTH_LONG).show();

            ImageView placeImage = (ImageView) view.findViewById(R.id.placeImage);
            LinearLayout placeNameHolder = (LinearLayout) view.findViewById(R.id.placeNameHolder);
// 2
            Pair<View, String> imagePair = Pair.create((View) placeImage, "tImage");
            Pair<View, String> holderPair = Pair.create((View) placeNameHolder, "tNameHolder");
// 3
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    imagePair, holderPair);

            ActivityCompat.startActivity(getActivity(), transitionIntent, options.toBundle());
        }
    };


    private class DownloadJSON extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        public String doInBackground(String... params) {


            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url("http://newjson-pranavkumar.rhcloud.com/GridViewJson")
                    .build();


            try {
                Response response = okHttpClient.newCall(request).execute();


                json = response.body().string();


                JSONObject reader = new JSONObject(json);
                JSONArray jsonarray = reader.getJSONArray("images");

                for (int i = 0; i < jsonarray.length(); i++)
                {

                    JSONObject jsonobject = jsonarray.getJSONObject(i);

                    item = new FeedItem();

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

              //  mAdapternew = new SimpleCursorRecyclerAdapter(getActivity(), null, 0, CURSOR_LOADER_ID);

                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(onItemClickListenernew);

                Log.i("my", "list adapter set");

            }
            else
            {
                Toast.makeText(getActivity(), "no internet or server is down", Toast.LENGTH_LONG).show();

            }


        }
    }


    MyAdapter.OnItemClickListener onItemClickListenernew=new MyAdapter.OnItemClickListener()
    {

        @Override
        public void onItemClick(View view, int position) {
            Intent transitionIntent = new Intent(getActivity(), SecondActivity.class);
            String url=feedItemList.get(position).getThumbnail();
            //Toast.makeText(getActivity(),url,Toast.LENGTH_LONG).show();
            transitionIntent.putExtra("flag", url);
           // startActivity(transitionIntent);
            //Toast.makeText(getActivity(),"clicked"+position+url,Toast.LENGTH_LONG).show();

            ImageView placeImage = (ImageView) view.findViewById(R.id.placeImage);
            LinearLayout placeNameHolder = (LinearLayout) view.findViewById(R.id.placeNameHolder);
// 2
            Pair<View, String> imagePair = Pair.create((View) placeImage, "tImage");
            Pair<View, String> holderPair = Pair.create((View) placeNameHolder, "tNameHolder");
// 3
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    imagePair, holderPair);

            ActivityCompat.startActivity(getActivity(), transitionIntent, options.toBundle());

        }
    };


    private class DownloadJSONAda extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        public String doInBackground(String... params) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url("http://newjson-pranavkumar.rhcloud.com/GridViewJson")
                    .build();


            try {
                Response response = okHttpClient.newCall(request).execute();


                json = response.body().string();

                // String p=json.getClass().getName();
                Object jsonn = new JSONTokener(json).nextValue();
                if (jsonn instanceof JSONObject)
                {
                    Log.i("class","object"+jsonn.toString());

                }
                //you have an object
                else if (jsonn instanceof JSONArray)
                //you have an array
                {
                    Log.i("class","array"+jsonn.toString());
                }

                JSONObject reader = new JSONObject(json);
                JSONArray jsonarray = reader.getJSONArray("images");

                for (int i = 0; i < jsonarray.length(); i++)
                {

                    JSONObject jsonobject = jsonarray.getJSONObject(i);

                    item = new FeedItem();

                    item.setThumbnail(jsonobject.optString("image"));
                    feedItemList.add(item);

                    // String h=jsonobject.optString("image");
                    //Log.i("images-json",h);


                    String s = item.getThumbnail();

                    //Log.i("images-feeditem",s);
                    mSelectionClause = MyContract.MyEntry.COLUMN_IMAGE + " = ?";

                    // Moves the user's input string to the selection arguments.
                    mSelectionArgs[0] = s;


                    Cursor mCursor = getActivity().getContentResolver().query(
                            MyContract.MyEntry.CONTENT_URI,  // The content URI of the words table
                            mProjection,                       // The columns to return for each row
                            mSelectionClause,                  // Either null, or the word the user entered
                            mSelectionArgs,                    // Either empty, or the string the user entered
                            null);                       // The sort order for the returned rows

                    // Some providers return null if an error occurs, others throw an exception
                    if (null == mCursor) {

                        // If the Cursor is empty, the provider found no matches

                        ContentValues[] flavorValuesArr = new ContentValues[feedItemList.size()];
                        // Loop through static array of Flavors, add each to an instance of ContentValues
                        // in the array of ContentValues

                        // Log.i("imagesffff", "");
                        for (int j = 0; j < feedItemList.size(); j++) {
                            FeedItem sk = feedItemList.get(j);
                            Log.i("images", sk.getThumbnail());
                            flavorValuesArr[j] = new ContentValues();
                            flavorValuesArr[j].put(MyContract.MyEntry.COLUMN_IMAGE, sk.getThumbnail());

                            Log.i("images", "iterating" + flavorValuesArr[i]);
                            // getApplication().getContentResolver().insert(MyContract.MyEntry.CONTENT_URI,flavorValuesArr[i]);


                        }

                        getActivity().getContentResolver().bulkInsert(MyContract.MyEntry.CONTENT_URI, flavorValuesArr);
                        //notifyme();





                    } else if (mCursor.getCount() < 1) {


                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MyContract.MyEntry.COLUMN_IMAGE, s);
                        getActivity().getContentResolver().insert(MyContract.MyEntry.CONTENT_URI, contentValues);



                        Log.i("servicei", "inserted new record");
                        //cursor.close();


                        //notifyme();

//                    final LayoutInflater mInflater = LayoutInflater.from(getContext());
//                    final View sView = mInflater.inflate(R.layout.newcard, null, false);
//                    RecyclerView mRecyclerView = (RecyclerView) sView.findViewById(R.id.recyclerview);
//
//                    SimpleCursorRecyclerAdapter mAdapternew = new SimpleCursorRecyclerAdapter(getContext(), null, 0, 0);
//
//                    mRecyclerView.setAdapter(mAdapternew);


                    } else {
                        // Insert code here to do something with the results
                        Log.i("service", "record is present");
                        // cursor.close();
                    }


                }

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {

            }

        return null;
        }

        @Override
        protected void onPostExecute(String args) {


        }
    }

}

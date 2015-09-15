package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.app.Activity;
import android.app.AlarmManager;
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

import org.json.JSONArray;
import org.json.JSONObject;

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
            //insertData();
            Log.i("insertdata", "insertdata");
           // SyncAdapter.initializeSyncAdapter(getActivity());
            SyncAdapter.syncImmediately(getActivity());

            //insertdata();

        }
        // initiale loader

       // getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

        Log.i("actvity", "created");
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


        //new DownloadJSON().execute();

        mAdapternew = new SimpleCursorRecyclerAdapter(getActivity(), null, 0, CURSOR_LOADER_ID);



        mRecyclerView.setAdapter(mAdapternew);

        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);

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
       // mAdapternew.notifyDataSetChanged();


    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapternew.swapCursor(null);
    }





    private class DownloadJSON extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public String doInBackground(String... params) {




            return null;
        }

        @Override
        protected void onPostExecute(String args) {






        }
    }

    SimpleCursorRecyclerAdapter.OnItemClickListener onItemClickListener = new SimpleCursorRecyclerAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View view, int position) {
            Intent transitionIntent = new Intent(getActivity(), SecondActivity.class);
            String url = feedItemList.get(position).getThumbnail();
            transitionIntent.putExtra("flag", url);
            //startActivity(transitionIntent);
            //Toast.makeText(getActivity(),"pos"+position,Toast.LENGTH_LONG).show();

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


}

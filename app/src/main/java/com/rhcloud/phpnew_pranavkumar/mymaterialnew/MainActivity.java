package com.rhcloud.phpnew_pranavkumar.mymaterialnew;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.rhcloud.phpnew_pranavkumar.mymaterialnew.data.MyContract;
import com.rhcloud.phpnew_pranavkumar.mymaterialnew.gcm.QuickstartPreferences;
import com.rhcloud.phpnew_pranavkumar.mymaterialnew.gcm.RegistrationIntentService;
import com.rhcloud.phpnew_pranavkumar.mymaterialnew.sync.SyncAdapter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private DrawerLayout mDrawerLayout;
    // ParseUser currentUser;
    String IMAGES = "Images";
    TextView textView;
    ImageView imageView;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    ViewPager viewPager;
    FloatingActionButton fab;
    DesignDemoPagerAdapter adapter;
    TabLayout tabLayout;
    Toolbar toolbar;

    // Connection detector class
    ConnectionDetector cd;
    Cursor c;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Server API Key help
//        AIzaSyBFKpI_76IDa0PsyuLy9JIExYCtFJSEHm8
//        Sender ID help
//        82163403463

        textView = (TextView) findViewById(R.id.headertext);
        imageView = (ImageView) findViewById(R.id.imageViewavt);
        ParseAnalytics.trackAppOpened(getIntent());
        cd = new ConnectionDetector(getApplicationContext());
//        ParseUser currentUser = ParseUser.getCurrentUser();
//        //currentUser.getClass();
//        if (currentUser == null) {
//
//            navigatToLogin();
//
//        } else {
//            Log.i("login", currentUser.getUsername());
//            String e = currentUser.getEmail();
//            textView.setText(e);
//
//            ParseFile p = currentUser.getParseFile("ImageFile");
//
//            //Toast.makeText(this,"image"+p,Toast.LENGTH_LONG).show();
//            byte[] file = new byte[0];
//            try {
//                file = p.getData();
//                Bitmap image = BitmapFactory.decodeByteArray(file, 0, file.length);
//                imageView.setImageBitmap(image);
//            } catch (ParseException e1) {
//                e1.printStackTrace();
//            }
//
//
//        }


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.i("gcm:----","Token retrieved and sent to server! You can now use gcmsender to\n" +
                            "        send downstream messages to this app");
                   // mInformationTextView.setText(getString(R.string.gcm_send_message));
                } else {
                    //mInformationTextView.setText(getString(R.string.token_error_message));
                    Log.i("gcm:----","An error occurred while either fetching the InstanceID token,\n" +
                            "        sending the fetched token to the server or subscribing to the PubSub topic. Please try\n" +
                            "        running the sample again.");
                }
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

       // startService();

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action);
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setLogo(R.mipmap.pop);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                //Toast.makeText(MainActivity.this, menuItem.getItemId(), Toast.LENGTH_LONG).show();

                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_attachment:
                        //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainActivity.this, AboutUs.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        break;
                    case R.id.navigation_item_images:
                        //Toast.makeText(MainActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();

                        Intent intentv = new Intent(MainActivity.this, ContactUs.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentv);
                        break;


                }

                return true;
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(findViewById(R.id.coordinator), "I'm a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MainActivity.this, "Snackbar Action", Toast.LENGTH_LONG).show();
//                    }
//                }).show();
//                DesignDemoFragment designDemoFragment=new DesignDemoFragment();
//                designDemoFragment.toggle();

                RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
                StaggeredGridLayoutManager st = (StaggeredGridLayoutManager) mRecyclerView.getLayoutManager();

                final int sp = st.getSpanCount();


                if (sp == 1) {
                    st.setSpanCount(2);
                    fab.setImageResource(R.drawable.ic_list);

                } else {
                    st.setSpanCount(1);
                    fab.setImageResource(R.drawable.ic_grid);
                }
                //fab.setBackgroundResource(R.drawable.ic_place);
            }
        });


        adapter = new DesignDemoPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        //viewPager.setOnTouchListener(this);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());


                int t=tab.getPosition();
                //int k=tabLayout.getSelectedTabPosition();

               // Toast.makeText(getApplicationContext(),"id"+t,Toast.LENGTH_LONG).show();

                if (t == 1) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInternetPresent = cd.isConnectingToInternet();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));

        if (isInternetPresent) {
            // Internet Connection is Present
            // make HTTP requests

        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            Snackbar.make(findViewById(R.id.coordinator), "Hi No Internet Connection", Snackbar.LENGTH_LONG).show();

        }


    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);

        super.onPause();
    }

//    private void startService() {
//        Calendar calendar = Calendar.getInstance();
//
//        calendar.set(Calendar.MONTH, 6);
//        calendar.set(Calendar.YEAR, 2013);
//        calendar.set(Calendar.DAY_OF_MONTH, 13);
//
//        calendar.set(Calendar.HOUR_OF_DAY, 20);
//        calendar.set(Calendar.MINUTE, 48);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.AM_PM, Calendar.PM);
//
//        long firstTime = SystemClock.elapsedRealtime();
//        firstTime += 10 * 1000;
//
//        Intent myIntent = new Intent(this, MyReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
//
//        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        //alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
//        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime,
//                10 * 1000, pendingIntent);
//
//
//    }

    private void navigatToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:

                //ParseUser.logOut();
                //navigatToLogin();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int c = viewPager.getCurrentItem();

        //int k=tabLayout.getSelectedTabPosition();

        // Toast.makeText(this,"id"+k,Toast.LENGTH_LONG).show();

        if (c == 0) {
            fab.hide();
        } else {
            fab.show();
        }
        return false;
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}

package com.radiantraccon.probe;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.radiantraccon.probe.data.ResultData;
import com.radiantraccon.probe.site.Okky;
import com.radiantraccon.probe.site.Quasarzone;
import com.radiantraccon.probe.site.Ruliweb;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*
     * Permissions
     */
    private String[] permissionList = {
            Manifest.permission.INTERNET
    };
    // TODO: data classes could be extends abstract class
    // Toolbar

    private NavController navController;
    private Crawler crawler = new Crawler();

    private ArrayList<ResultData> histoyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check permissions and request
        if(!hasPermissions()) {
            requestPermissions(permissionList, 0);
        }
        //////////////////////////////////
        navController = Navigation.findNavController(this,R.id.frameLayout);
        Toolbar mainToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mainToolbar);
        mainToolbar.setTitleTextColor(Color.WHITE);

        //////////////////////////////////
        //region BottomNavigationView
        final BottomNavigationView bnv = findViewById(R.id.bottomNavView);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.mainFragment,
                R.id.historyFragment,
                R.id.optionFragment
        ).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(mainToolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bnv, navController);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int id = destination.getId();
                switch(id) {
                    case R.id.webFragment:
                        getSupportActionBar().hide();
                        bnv.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        getSupportActionBar().show();
                        bnv.setVisibility(View.VISIBLE);
                }
            }
        });

        CrawlOption.loadPreferences(this);
    }

    public void addHistory(ResultData data) {
        histoyList.add(data);
    }

    public ArrayList<ResultData> getHistoryList() {
        return histoyList;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.addFragment:
                return false;
        }
        return false;
    }

    //////////////////////////////////
    // region Permissions
    private boolean hasPermissions() {
        for (String p : permissionList) {
            int check = checkCallingOrSelfPermission(p);
            return check != PackageManager.PERMISSION_DENIED;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0){
            for(int i=0; i<grantResults.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // TODO: if Permissions accepted, keep going
                }
                else {
                    // TODO: if Permissions denied, request agian
                }
            }
        }
    }
    // endregion
    //////////////////////////////////

    public void crawl(String address, String keyword, String startPage, String lastPage, String nav) {
        // if nav == true, navigate mainFragment to resultFragment
        // if nav == false, don't
        crawler = new Crawler();
        crawler.execute(address, keyword, startPage, lastPage, nav);
    }

    private class Crawler extends AsyncTask<String, Void, ArrayList<ResultData>> {

        /* TODO:
         * check http://siteaddress/robots.txt
         * default image icon http://siteaddress/favicon.ico
         * prevent getting blacklisted (delay random seconds?)
         * get response code

         *
         * change ArrayList<ResultData> parameter if dont need
        TODO: execute in background per X minute, compare data, alarm new item
        TODO: AlarmManager, Service, file read/write for resultData?
        */

        private ProgressDialog progressDialog;
        private ArrayList<ResultData> results = new ArrayList<>();
        private String address;
        private String keyword;
        private String startPage;
        private String lastPage;
        private String nav;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // TODO: Wait dialoag?
            // temprary dialog
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();
        }

        @Override
        protected ArrayList<ResultData> doInBackground(String... strings) {
            Log.e("AsyncTask" , "Address: " + strings[0] + "\nKeyword: "+ strings[1]+ "\nstartPage: "+ strings[2]+ "\nlastPage: " +strings[3]);
            address = strings[0];
            keyword = strings[1];
            startPage = strings[2];
            lastPage = strings[3];
            nav = strings[4];
            int start = Integer.parseInt(startPage);
            int last = Integer.parseInt(lastPage);
            switch(address) {
                // TODO: if imageURL is favicon, donwload once
                case Quasarzone.NEWS_GAME:
                case Quasarzone.NEWS_HARDWARE:
                case Quasarzone.NEWS_MOBILE:
                case Quasarzone.NEWS_SALE:
                    for(int i=start; i<=last; i++) {
                        results.addAll(Quasarzone.getData(address, keyword, i));
                    }
                    break;
                case Okky.TECH:
                case Okky.COLUMS:
                case Okky.JOBS:
                    for(int i=start; i<=last; i++) {
                        results.addAll(Okky.getData(address, keyword, i));
                    }
                    break;
                case Ruliweb.NEWS_SALE:
                case Ruliweb.NEWS_PC:
                case Ruliweb.NEWS_MOBILE:
                case Ruliweb.NEWS_CONSOLE:
                    for(int i=start; i<=last; i++) {
                        results.addAll(Ruliweb.getData(address, keyword, i));
                    }
                    break;

            }
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<ResultData> resultData) {
            super.onPostExecute(resultData);
            //TODO: temprary dialog
            progressDialog.dismiss();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("results", results);
            bundle.putString("keyword", keyword);
            bundle.putString("address", address);
            bundle.putString("startPage", startPage);
            bundle.putString("lastPage", lastPage);
            if(nav.equals("true")) {
                navController.navigate(R.id.action_mainFragment_to_resultFragment, bundle);
            } else {
                navController.navigate(R.id.action_resultFragment_self, bundle);

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(ArrayList<ResultData> resultData) {
            super.onCancelled(resultData);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}

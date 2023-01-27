package com.example.listallapps;


import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView text;
    App app;
    AppAdapter adaptador;
    List<String> apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialise layout
        listView = findViewById(R.id.listview);
        text = findViewById(R.id.totalapp);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String package_clicked = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA).get(i).packageName;
                Toast.makeText(MainActivity.this, package_clicked, Toast.LENGTH_SHORT).show();
                // DO SOMETHING WITH THIS INFORMATION
                //
                //
                //

            }

        });
    }





    public void getallapps(View view) {
        // get list of all the apps installed
        List<ApplicationInfo> infos = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        List<PackageInfo> packList = getPackageManager().getInstalledPackages(0);
        Log.i("Checking: infos and packList are the same size", String.valueOf(infos.size() == packList.size()));
        // create a list with size of total number of apps

        adaptador = new AppAdapter(getApplicationContext(), R.layout.celula);

        for (int i = 0; i < packList.size(); i++) {
            String pack_name = infos.get(i).packageName;
            PackageInfo packInfo = packList.get(i);
            String app_name = packInfo.applicationInfo.loadLabel(getPackageManager()).toString();

            app = new App(app_name, pack_name);

            try
            {
                Drawable d = getPackageManager().getApplicationIcon(pack_name);
                app.setIcon(d);
            }
            catch (PackageManager.NameNotFoundException e)
            {

            }
            adaptador.add(app);


        }

        // set all the apps name in list view
        listView.setAdapter(adaptador);
        // write total count of apps available.
        text.setText(infos.size() + " Apps are installed");
    }



    @Override
    protected void onStart() {
        super.onStart();

    }
}

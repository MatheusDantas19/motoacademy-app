package com.example.listallapps;


import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    ListView listView;
    App app;
    ArrayList<App> apps;
    AppAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setta o Package Manager
        PackageManager pm = this.getPackageManager();

        // Cria uma intent que contenha categorias 'acao' e 'launcher' (executavel)
        Intent main=new Intent(Intent.ACTION_MAIN, null);
        main.addCategory(Intent.CATEGORY_LAUNCHER);

        // Retorno todos os apps/packs que cumprem as condicoes da intent
        List<ResolveInfo> launchables = pm.queryIntentActivities(main, 0);

        adaptador = new AppAdapter(getApplicationContext(), R.layout.celula);
        apps      = new ArrayList<>();

        Collections.sort(launchables,
                new ResolveInfo.DisplayNameComparator(pm));

        // Percorro por todos os arquivos ResolveInfo para extrair nome, pacote e icone.
        for (ResolveInfo rInfo : launchables) {
            String appname = rInfo.activityInfo.applicationInfo.loadLabel(pm).toString();
            String packname = rInfo.activityInfo.packageName;

            app = new App(appname, packname);

            try
            {
                Drawable d = getPackageManager().getApplicationIcon(packname);
                app.setIcon(d);
            }
            catch (PackageManager.NameNotFoundException e)
            {
            }

            adaptador.add(app);
            apps.add(app);
        }

        // initializa o listView
        listView = findViewById(R.id.listview);

        // seta todos os apps na list view
        listView.setAdapter(adaptador);

        // Action by on click listview item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String package_clicked = apps.get(i).getPackageName();
                String app_clicked     = apps.get(i).getName();
                String message = "Aplicação " + app_clicked + " atribuída ao botão Custom Action.";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                try {
                    Runtime.getRuntime().exec("setprop package_name " + package_clicked);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Runtime.getRuntime().exec("setprop app_name " + app_clicked);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();

    }
}

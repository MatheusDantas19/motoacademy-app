package com.example.listallapps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppAdapter extends ArrayAdapter {

    public AppAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View minhaView;
        minhaView = convertView;
        AppView viewApp;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            minhaView = inflater.inflate(R.layout.celula,parent,false);

            viewApp = new AppView();
            viewApp.appName =       (TextView)  minhaView.findViewById(R.id.celula_appName);
            viewApp.packageName =   (TextView)  minhaView.findViewById(R.id.celula_appPackage);
            viewApp.icon =          (ImageView) minhaView.findViewById(R.id.celula_appIcon);

            minhaView.setTag(viewApp);

        }else {
            viewApp = (AppView) minhaView.getTag();
        }

        App app;
        app = (App) this.getItem(position);


        viewApp.appName.setText(app.getName());
        viewApp.packageName.setText(app.getPackageName());
        viewApp.icon.setImageDrawable(app.getIcon());

        return minhaView;
    }
}
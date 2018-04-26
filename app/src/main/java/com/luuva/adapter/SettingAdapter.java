package com.luuva.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.luuva.model.Payment;
import com.luuva.model.Setting;
import com.luuva.orderfood.R;

import java.util.List;

/**
 * Created by NhuongPH on 4/13/2018.
 */

public class SettingAdapter extends ArrayAdapter<Setting> {
    Activity context;
    int resource;
    List<Setting> objects;

    public SettingAdapter(@NonNull Activity context, int resource, @NonNull List<Setting> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(R.layout.settting_item,null);


        ImageView imgIcon_pay = row.findViewById(R.id.image_icon_set);
        TextView pay = row.findViewById(R.id.set);


        Setting setting = this.objects.get(position);

        imgIcon_pay.setImageResource(setting.getIcon());
        pay.setText(setting.getSetting());
        return row;
    }
}


package com.luuva.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.luuva.model.Category;
import com.luuva.model.Food;
import com.luuva.orderfood.FoodByCategory;
import com.luuva.orderfood.ProductActivity;
import com.luuva.orderfood.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by luuva on 3/9/2018.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {
    private Activity context;
    private int resource;
    private List<Category> objects;
    public CategoryAdapter(@NonNull Activity context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(R.layout.gv_item,null);

        ImageButton btnCategory = row.findViewById(R.id.btnCategory);
        TextView txtCategory = row.findViewById(R.id.txtCategory);

        final Category category = this.objects.get(position); // lấy đối tượng từ List
        btnCategory.setImageResource(category.getPicture());
        txtCategory.setText(category.getName_cat());
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), FoodByCategory.class);
                intent.putExtra("idCat", category.getId());
                intent.putExtra("nameCat", category.getName_cat());
                context.startActivity(intent);
            }
        });
        return row;
    }
}

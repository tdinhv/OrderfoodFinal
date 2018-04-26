package com.luuva.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.luuva.adapter.SettingAdapter;
import com.luuva.model.Setting;

import java.util.ArrayList;

/**
 * Created by NhuongPH on 4/13/2018.
 */

public class SettingActivity extends AppCompatActivity {
    ListView lvItem_set;
    ArrayList<Setting> dsSet;
    SettingAdapter setAdapter;
    TextView ttB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        addControl();
    }
    private void addControl() {
        lvItem_set = findViewById(R.id.lvItem_set);
        dsSet = new ArrayList<>();

        dsSet.add(new Setting("Hình đại diện", R.drawable.pay1));
        dsSet.add(new Setting("Thông tin và liên hệ", R.drawable.pay2));
        dsSet.add(new Setting("Mật khẩu", R.drawable.pay31));
        dsSet.add(new Setting("Quản lý phiên hoạt động", R.drawable.pay41));
        dsSet.add(new Setting("Đổi ngôn ngữ", R.drawable.icon_5));
        dsSet.add(new Setting("Cài đặt thông báo", R.drawable.icon_5));

        setAdapter = new SettingAdapter(SettingActivity.this,R.layout.settting_item,dsSet);
        lvItem_set.setAdapter(setAdapter);
        ttB = findViewById(R.id.BackSet);
        ttB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lvItem_set.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1){
                    Intent intent = new Intent(view.getContext(),UserInformation.class) ;
                    startActivity(intent);
                }
            }
        });
    }
}

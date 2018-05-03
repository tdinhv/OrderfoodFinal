package com.luuva.orderfood;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.luuva.adapter.FoodAdapter;
import com.luuva.background.UserSession;
import com.luuva.model.Food;
import com.luuva.model.User;
import com.luuva.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("Registered")
public class FoodByCategory extends AppCompatActivity {
    private ListView lvFood;
    ArrayList<Food> arrFood;
    FoodAdapter productAdapter;
    int idCat = 0;
    TextView txtDanhMuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_by_category);
        txtDanhMuc = findViewById(R.id.tvTenDanhMuc);
        txtDanhMuc.setText(getIntent().getStringExtra("nameCat"));
        lvFood = findViewById(R.id.lv_Product);
        arrFood = new ArrayList<>();
        idCat = getIntent().getIntExtra("idCat", -1);
        UserSession session = new UserSession(getApplication());
        final User userLogin = session.getUserLogin();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment =null;
                Intent intent;
                Bundle bundle = new Bundle();
                switch (item.getItemId()){
                    case R.id.action_home:
                        intent = new Intent(FoodByCategory.this, MainActivity.class);
                        bundle.putInt("fragment", 1);
                        intent.putExtra("frag", bundle);
                        startActivity(intent);
                        break;
                    case R.id.action_noti:
                        intent = new Intent(FoodByCategory.this, MainActivity.class);
                        bundle.putInt("fragment", 2);
                        intent.putExtra("frag", bundle);
                        startActivity(intent);
                        break;
                    case R.id.action_acc:
                        if(userLogin==null){
                            Intent intent3 = new Intent(FoodByCategory.this, MainLoginActivity.class);
                            startActivity(intent3);
                        }else{
                            intent = new Intent(FoodByCategory.this, MainActivity.class);
                            bundle.putInt("fragment", 3);
                            intent.putExtra("frag", bundle);
                            startActivity(intent);
                        }
                        break;
                }
                return false;
            }
        });
        Log.d("conce+++", idCat + "");
        getList();

        productAdapter = new FoodAdapter(FoodByCategory.this, R.layout.lv_item_food, arrFood);
        lvFood.setAdapter(productAdapter);

        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Food food = arrFood.get(i);
                Intent intent = new Intent(FoodByCategory.this, FoodDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("food", food);
                intent.putExtra("packageFood", bundle);
                startActivity(intent);
            }
        });
    }

    public void getList() {
        String tag_string_req = "getlistfood";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.Duongdansanphamtheodanhmuc, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Food objFood;
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            objFood = new Food(jObj.getInt("id"), jObj.getString("name_food"), jObj.getInt("price"), jObj.getString("image"), jObj.getString("date_create"), jObj.getString("address"), jObj.getInt("shop_id"),jObj.getInt("id_cat"), jObj.getString("description"));
                            arrFood.add(objFood);
                            productAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("idCat", String.valueOf(idCat));
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
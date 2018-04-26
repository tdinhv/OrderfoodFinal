package com.luuva.orderfood;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.luuva.adapter.FoodAdapter;
import com.luuva.background.CatDao;
import com.luuva.background.FoodDao;
import com.luuva.background.UserSession;
import com.luuva.model.Food;
import com.luuva.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("Registered")
public class ProductActivity extends AppCompatActivity {
    private ListView lvFood;
    ArrayList<Food> arrFood;
    FoodAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        lvFood = findViewById(R.id.lv_Product);
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
                        intent = new Intent(ProductActivity.this, MainActivity.class);
                        bundle.putInt("fragment", 1);
                        intent.putExtra("frag", bundle);
                        startActivity(intent);
                        break;
                    case R.id.action_noti:
                        intent = new Intent(ProductActivity.this, MainActivity.class);
                        bundle.putInt("fragment", 2);
                        intent.putExtra("frag", bundle);
                        startActivity(intent);
                        break;
                    case R.id.action_acc:
                        if(userLogin==null){
                            Intent intent3 = new Intent(ProductActivity.this, MainLoginActivity.class);
                            startActivity(intent3);
                        }else{
                            intent = new Intent(ProductActivity.this, MainActivity.class);
                            bundle.putInt("fragment", 3);
                            intent.putExtra("frag", bundle);
                            startActivity(intent);
                        }
                        break;
                }
                return false;
            }
        });
        arrFood = new ArrayList<>();
        getList();
        productAdapter = new FoodAdapter(ProductActivity.this, R.layout.lv_item_food, arrFood);
        lvFood.setAdapter(productAdapter);

        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                Food food = arrFood.get(i);
                Intent intent = new Intent(ProductActivity.this, FoodDetail.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("food", food);
                intent.putExtra("packageFood", bundle);
                startActivity(intent);
            }
        });
    }

    public void getList() {

        final String url = "https://lebavy1611.000webhostapp.com/get_all_products.php";
        String tag_string_req = "getlistfood";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    Food objFood;
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jObj = response.getJSONObject(i);
                            objFood = new Food(jObj.getInt("id"), jObj.getString("name_food"), jObj.getInt("price"), "R.drawable." + jObj.getString("image"), jObj.getString("date_create"), jObj.getString("address"), jObj.getInt("shop_id"), jObj.getInt("id_cat"));
                            arrFood.add(objFood);
                        }
                        productAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Login Error: " + error.getMessage());
                //toast("Unknown Error occurred");
                //progressDialog.hide();
            }
        });

        AndroidLoginController.getInstance().addToRequestQueue(jsonArrayRequest, tag_string_req);
    }

}
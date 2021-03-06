package com.luuva.orderfood;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TabHost;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.luuva.adapter.CategoryAdapter;
import com.luuva.adapter.FoodAdapter;
import com.luuva.model.Category;
import com.luuva.model.Food;
import com.luuva.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by luuva on 3/9/2018.
 */

public class HomeFragment extends Fragment {
    private GridView gvCategory;
    private ArrayList<Category> categories;
    private CategoryAdapter adapter;
    private ListView lvFood, lvNearMe;
    private ArrayList<Food> listFoodFavorite, foodsNearMe;
    private FoodAdapter foodAdapter, nearMeAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        gvCategory = view.findViewById(R.id.gvCategory);
        categories = new ArrayList<>();
        getListCats(view);

        //tab1
        lvFood = view.findViewById(R.id.lvPro);
        listFoodFavorite = new ArrayList<>();
        getListFoodFavorite(view);
        //tab2
        lvNearMe = view.findViewById(R.id.lvNearMe);
        foodsNearMe = new ArrayList<>();
        foodsNearMe.add(new Food(1, "Hủ Tiếu", 12000, R.drawable.anh1 + "", "Vinh Nam", "Huế", 1, 1,""));
        foodsNearMe.add(new Food(1, "Mì Quảng", 22000, R.drawable.anh2 + "", "Vinh Nam", "Huế", 1, 1,""));
        foodsNearMe.add(new Food(1, "Bún Bò", 32000, R.drawable.anh3 + "", "Vinh Nam", "Huế", 1, 1,""));
        foodsNearMe.add(new Food(1, "Mì Tôm", 45000, R.drawable.anh4 + "", "Vinh Nam", "Huế", 1, 1,""));
        foodsNearMe.add(new Food(1, "Cơm Gà", 21000, R.drawable.anh5 + "", "Vinh Nam", "Huế", 1, 1,""));

        nearMeAdapter = new FoodAdapter(getActivity(), R.layout.lv_item_food, foodsNearMe);
        lvNearMe.setAdapter(nearMeAdapter);

        TabHost tabHost = view.findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator("Đặt nhiều");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("Gần tôi");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);
        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food food = listFoodFavorite.get(i);
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                /*
                Bundle bundle = new Bundle();
                bundle.putSerializable("food",food);*/
                startActivity(intent);
            }
        });
        return view;
    }

    public void getListCats(View v) {
        String tag_string_req = "getlistcart";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.Duongdantatcadanhmuc, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    Category objCat;
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jObj = response.getJSONObject(i);
                            int picture = 0;
                            switch (i){
                                case 0:
                                    picture = R.drawable.anh1;
                                    break;
                                case 1:
                                    picture = R.drawable.anh2;
                                    break;
                                case 2:
                                    picture = R.drawable.anh3;
                                    break;
                                case 3:
                                    picture = R.drawable.anh4;
                                    break;
                                case 4:
                                    picture = R.drawable.anh5;
                                    break;
                                case 5:
                                    picture = R.drawable.anh6;
                                    break;
                                default:
                                    picture = R.drawable.anh6;
                                    break;
                            }
                            objCat = new Category(jObj.getInt("id"), jObj.getString("name_cat"), picture);
                            categories.add(objCat);
                        }
                        adapter = new CategoryAdapter(getActivity(), R.layout.gv_item, categories);
                        gvCategory.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
        requestQueue.add(jsonArrayRequest);
    }

    public void getListFoodFavorite(View v) {
        String tag_string_req = "getlistfood";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.Duongdansanphamyeuthich, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    Food objFood;
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jObj = response.getJSONObject(i);
                            objFood = new Food(jObj.getInt("id"), jObj.getString("name_food"), jObj.getInt("price"), jObj.getString("image"), jObj.getString("date_create"), jObj.getString("address"), jObj.getInt("shop_id"),jObj.getInt("id_cat"), jObj.getString("description"));
                            listFoodFavorite.add(objFood);
                        }
                        foodAdapter = new FoodAdapter(getActivity(), R.layout.lv_item_food, listFoodFavorite);
                        lvFood.setAdapter(foodAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
        requestQueue.add(jsonArrayRequest);
    }
}


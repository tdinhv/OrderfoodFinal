package com.luuva.orderfood;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.luuva.background.UserSession;
import com.luuva.model.Cart;
import com.luuva.model.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Cart> listCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserSession session = new UserSession(getApplication());
        final User userLogin = session.getUserLogin();
        Intent intent =getIntent();
        Fragment fragmentLoad = null;
        //Array giỏ hàng
        if(listCart != null){

        }else{
            listCart= new ArrayList<>();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment =null;
                switch (item.getItemId()){
                    case R.id.action_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.action_noti:
                        fragment = new NotificationsFragment();
                        break;
                    case R.id.action_acc:
                        if(userLogin==null){
                            Intent intent = new Intent(MainActivity.this, MainLoginActivity.class);
                            startActivity(intent);
                        }else{
                            fragment = new UserFragment();
                        }
                        break;

                }
                return loadFragment(fragment);
            }
        });
        if(intent!=null) {
            Bundle bundle = intent.getBundleExtra("frag");

            if (bundle != null){
                int x = bundle.getInt("fragment");
                Log.d("fragment",x+"");
                switch (x) {
                    case 1:
                        fragmentLoad = new HomeFragment();
                        Log.d("fragment 1",x+"");
                        break;
                    case 2:
                        fragmentLoad = new NotificationsFragment();
                        Log.d("fragment 2",x+"");
                        break;
                    case 3:
                        if (userLogin == null) {
                            Intent intent2 = new Intent(MainActivity.this, MainLoginActivity.class);
                            startActivity(intent2);
                        } else {
                            Log.d("fragment 3",x+"");
                            fragmentLoad = new UserFragment();
                        }
                        break;
                }
                loadFragment(fragmentLoad);
                //finish();
            }else{
                Log.d("st","vao");
                loadFragment(new HomeFragment());
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean loadFragment(Fragment fragment){
        if (fragment!=null){
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }


}

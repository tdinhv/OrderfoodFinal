package com.luuva.orderfood;


import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.text.InputType;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.luuva.background.BackgroundWorker;
import com.luuva.background.UserSession;
import com.luuva.model.User;
import com.luuva.model.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by NhuongPH on 3/24/2018.
 */

public class UserInformation extends AppCompatActivity {
    private TextView ttB,fullName,btnLuu;
    private EditText edtUsername, edtName, edtEmail, edtBirthday, edtPhone, edtAddress,edtNewPass,edtConfirmPass;
    private RadioButton rdNam, rdNu,radioButton;
    private RadioGroup rdgroup;
    private User userLogin;
    private String getId, getUserName,getFullName,getEmailId,getLocation,getMobileNumber,getPassword,getConfirmPassword,gender,getDateOfBirth;
    private UserSession session;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        edtUsername = findViewById(R.id.edtUsername);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtBirthday = findViewById(R.id.edtBirthday);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        rdgroup = findViewById(R.id.rdgroup);
        rdNam = findViewById(R.id.radioButtonNam);
        rdNu = findViewById(R.id.radioButtonNu);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtConfirmPass = findViewById(R.id.edtConfirmPass);
        edtNewPass.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edtConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        fullName = findViewById(R.id.fullName);
        btnLuu = findViewById(R.id.btnLuu);

        final UserSession session = new UserSession(this.getApplication());
        userLogin = session.getUserLogin();

        fullName.setText(userLogin.getFullname());
        //edtName.setText("adad");
        edtUsername.setText(userLogin.getUsername());
        edtName.setText(userLogin.getFullname());
        edtEmail.setText(userLogin.getEmail());
        edtBirthday.setText(String.valueOf(userLogin.getDate_of_brith()));
        edtPhone.setText(userLogin.getPhone());
        edtAddress.setText(userLogin.getAddress());
        if(userLogin.getGender()==1){
            rdNam.setChecked(true);
        }else rdNu.setChecked(true);
        ttB = findViewById(R.id.tvBack);
        ttB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getId = userLogin.getId()+"";
        getUserName = edtUsername.getText().toString();
        getFullName = edtName.getText().toString();
        getEmailId = edtEmail.getText().toString();
        getMobileNumber = edtPhone.getText().toString();
        getLocation = edtAddress.getText().toString();
        getDateOfBirth =edtBirthday.getText().toString();
        getPassword = edtNewPass.getText().toString();
        getConfirmPassword = edtConfirmPass.getText().toString();
        int selectedId = rdgroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        gender= (String) radioButton.getText();
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation(view);

            }
        });
    }
    private void checkValidation(View v) {

        // Get all edittext texts
        getUserName = edtUsername.getText().toString();
        getFullName = edtName.getText().toString();
        getEmailId = edtEmail.getText().toString();
        getMobileNumber = edtPhone.getText().toString();
        getLocation = edtAddress.getText().toString();
        getDateOfBirth =edtBirthday.getText().toString();
        getPassword = edtNewPass.getText().toString();
        getConfirmPassword = edtConfirmPass.getText().toString();
        int selectedId = rdgroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        gender= (String) radioButton.getText();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);
        Pattern p2 = Pattern.compile(Utils.regPass);
        Matcher m2 = p2.matcher(getPassword);
        // Check if all strings are null or not
        if (getFullName.equals("") || getFullName.length() == 0
                || getMobileNumber.equals("") || getMobileNumber.length() == 0
                || getLocation.equals("") || getLocation.length() == 0||getDateOfBirth.equals("")||getDateOfBirth.length()==0)

            toast("Mời nhập đầy đủ thông tin");
            // Check if email id valid or not
        else if (!m.find())
            toast("Email sai định dạng.");

            // Check if both password should be equal
            // Check if pass is valid or not
        else
        if(!getPassword.equals("")&&getPassword.length()!=0||!getConfirmPassword.equals("")&&getConfirmPassword.length()!=0) {
            if (!m2.find()) {
                toast("Password sai định dạng.");

            } else if (!getConfirmPassword.equals(getPassword)) {
                toast("Xác nhận mật khẩu không khớp.");
            }
            else {
                editUser(v);
            }
        } else {
            editUser(v);
        }

    }



    private void editUser(final View v) {
        getId = userLogin.getId()+"";
        getUserName = edtUsername.getText().toString();
        getFullName = edtName.getText().toString();
        getEmailId = edtEmail.getText().toString();
        getMobileNumber = edtPhone.getText().toString();
        getLocation = edtAddress.getText().toString();
        getDateOfBirth =edtBirthday.getText().toString();
        getPassword = edtNewPass.getText().toString();
        getConfirmPassword = edtConfirmPass.getText().toString();
        int selectedId = rdgroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);

        gender= (String) radioButton.getText();
        String tag_string_req="edit_user";
        String url="https://lebavy1611.000webhostapp.com/edituser.php";
        Log.d("edit user","nee");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //
                String s = response.trim();
                Log.d("server edit",s);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONObject user = jObj.getJSONObject("user");
                        String idUserLogin = user.getString("id");
                        Timestamp dateOfBrith = Timestamp.valueOf(user.getString("date_of_brith"));
                        Timestamp dateCreate = Timestamp.valueOf(user.getString("date_create"));
                        User userLogin = new User(user.getInt("id"),
                                user.getString("username"),
                                user.getString("password"),
                                user.getString("fullname"),
                                user.getString("email"),
                                user.getString("phone"),
                                user.getString("address"),
                                user.getInt("gender"),
                                dateOfBrith,
                                user.getInt("role_id"),
                                dateCreate,
                                user.getInt("active"));
                        // Inserting row in users table
                        session = new UserSession(v.getContext());
                        session.setLoggedin(true, userLogin);
                        toast("Chỉnh sửa thành công");
                        Intent intent = new Intent(UserInformation.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("fragment", 3);
                        intent.putExtra("frag", bundle);
                        startActivity(intent);
                        //intent.putExtra("EMAIL",email);
                        //startActivity(new Intent(v.getContext(), UserInformation.class));
                        //finish();
                    }else {
                        //progressDialog.setMessage("Đăng nhập thất bại.");
                        //progressDialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Login Error: " + error.getMessage());
                //toast("Unknown Error occurred");
                //progressDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",getId);
                params.put("fullname",getFullName);
                params.put("email",getEmailId);
                params.put("phone",getMobileNumber);
                params.put("address",getLocation);
                params.put("gender",gender);
                params.put("date_of_brith",getDateOfBirth);
                params.put("password",getPassword);
                //params.put("password",password);
                return params;
            }
        };
        Log.d("vao k", String.valueOf(stringRequest));
        //requestQueue.add(stringRequest);
        AndroidLoginController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    private void toast(String x){
        Toast.makeText(this, x, Toast.LENGTH_LONG).show();
    }
}




package com.example.testnotification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    RequestQueue requestQueue;
    EditText email;
    EditText password;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        loginButton= findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data="{"+
                        "\"email\""       + ":"+  "\""+email.getText().toString()+"\","+
                        "\"password\""    + ":"+   "\""+password.getText().toString()+"\""+
                        "}";
                userLogin(data);
            }
        });

    }

    private void userLogin(String data) {
        final String useremail = email.getText().toString();
        final String userpassword = password.getText().toString();
        if (TextUtils.isEmpty(useremail)) {
            email.setError("Please enter your username");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userpassword)) {
            password.setError("Please enter your password");
            password.requestFocus();
            return;
        }


            String savedata = data;
            String URL = "https://mcc-users-api.herokuapp.com/login";
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            Log.d("TAG", "requestQueue: " + requestQueue);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject objres = new JSONObject(response);
                        Log.d("TAG", "onResponse: " + objres.toString());
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("email",email.getText().toString());
                        intent.putExtra("pass",password.getText().toString());
                        startActivity(intent);
                    } catch (JSONException e) {
                        Log.d("TAG", "Server Error ");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TAG", "onErrorResponse: " + error);
                    Toast.makeText(getApplicationContext() , "*** Invalid email and password ***" , Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        Log.d("TAG", "savedata: " + savedata);
                        return savedata == null ? null : savedata.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        return null;
                    }
                }
            };
            requestQueue.add(stringRequest);

        }

    public void swipToSignup(View v) {
        Log.e("tagg", "selected00");
        Intent intent = new Intent(Login.this, Signup.class);
        startActivity(intent);
    }
}







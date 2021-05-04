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
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;

public class Signup extends AppCompatActivity {
    RequestQueue requestQueue;
    EditText firstName,secondName,email,password;
    Button signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstName=findViewById(R.id.first_name);
        secondName=findViewById(R.id.second_name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signupButton= findViewById(R.id.btn_register);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data="{"+
                        "\"firstName\""   + ":"+  "\""+firstName.getText().toString()+"\","+
                        "\"secondName\""  + ":"+  "\""+secondName.getText().toString()+"\","+
                        "\"email\""       + ":"+  "\""+email.getText().toString()+"\","+
                        "\"password\""    + ":"+   "\""+password.getText().toString()+"\""+
                        "}";
                Submit(data);

            }
        });

    }
    private void Submit(String data) {

        if (TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Please enter your username");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Please enter your password");
            password.requestFocus();
            return;
        }
        String savedata=data;
        String URL="https://mcc-users-api.herokuapp.com/add_new_user";
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        Log.d("TAG", "requestQueue: "+requestQueue);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);
                    Log.d("TAG", "onResponse: "+objres.toString());
                    Intent intent = new Intent(Signup.this, MainActivity.class);
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
                Log.d("TAG", "onErrorResponse: "+error);
                Toast.makeText(getApplicationContext() , "Email already exist Choose another one ..." , Toast.LENGTH_LONG).show();

            }
        })
        {
            @Override
            public String getBodyContentType(){return "application/json; charset=utf-8";}
            @Override
            public byte[] getBody() throws AuthFailureError {
                try{
                    Log.d("TAG", "savedata: "+savedata);
                    return savedata==null?null:savedata.getBytes("utf-8");
                }catch(UnsupportedEncodingException uee){
                    return null;
                }
            }
        };
        requestQueue.add(stringRequest);
    }

    public void swipToLogin(View v) {
        Intent intent = new Intent(Signup.this, Login.class);
        startActivity(intent);
    }

}



package com.example.p.cityguide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText password;
    EditText email;
    String id, name, refreshToken;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password = (EditText) findViewById(R.id.pass);
        email = (EditText) findViewById(R.id.email);
        password.setTypeface(Typeface.DEFAULT);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    public void login(View v) {
        btnLogin.setEnabled(false);
        String emailId = email.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (emailId.isEmpty() || pass.isEmpty()) {
            Toast.makeText(Login.this, "Please Enter the Credentials!!!", Toast.LENGTH_SHORT).show();

        } else {
            Map<String, String> requ = new HashMap<String, String>();
            requ.put("email", emailId);
            requ.put("password", pass);

            String url = "http://139.59.70.9/api/auth";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(requ), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONObject data = null;
                    try {
                        data = response.getJSONObject("data");
                        id = data.getString("_id");
                        name = data.getString("first_name");
                        refreshToken = data.getString("refresh_token");

                        SharedPreferences file = getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = file.edit();
                        editor.putString("category", id);
                        editor.putString("name", name);
                        editor.putString("refreshToken", refreshToken);
                        editor.commit();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(Login.this, Homepage.class);
                    intent.putExtra("category", id);
                    intent.putExtra("name", name);
                    intent.putExtra("refreshToken", refreshToken);
                    startActivity(intent);

                    finish();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body = "body";
                    if(error instanceof NetworkError || error instanceof NoConnectionError)
                    {
                        Toast.makeText(Login.this, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
                    }else {
                        String statuscode = String.valueOf(error.networkResponse.statusCode);
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {
                            try {
                                body = new String(error.networkResponse.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                        } else {
                            String errorMessage = error.getClass().getSimpleName();
                            if (!TextUtils.isEmpty(errorMessage)) {
                                //Toast.makeText(Login.this, "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (statuscode.equals("422")) {
                            Toast.makeText(Login.this, "Check Your Credentials!!!", Toast.LENGTH_SHORT).show();
                        }
                        if (statuscode.equals("403")) {
                            Toast.makeText(Login.this, "Email Verification Remaining!!!", Toast.LENGTH_SHORT).show();
                        }
                        if (statuscode.equals("404")) {
                            Toast.makeText(Login.this, "No Account Associated with this email found!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }


            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            SingletonRequest.getInstance(this.getApplicationContext()).addtoRequestQueue(request);

        }
        btnLogin.setEnabled(true);
    }

    public void signUp(View V) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}

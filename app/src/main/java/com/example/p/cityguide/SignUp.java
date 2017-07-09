package com.example.p.cityguide;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
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

public class SignUp extends AppCompatActivity {

    Toolbar toolbar;
    EditText email;
    EditText firstname;
    EditText lastname;
    EditText password;
    String first, last, emailid, pass;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = (Toolbar) findViewById(R.id.businesstoolbar);
        email = (EditText) findViewById(R.id.editEmailId);
        firstname = (EditText) findViewById(R.id.editFirstName);
        lastname = (EditText) findViewById(R.id.editLastName);
        password = (EditText) findViewById(R.id.editPass);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        password.setTypeface(Typeface.DEFAULT);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    public void Validate(View view) {
        btnSignUp.setEnabled(false);
        first = firstname.getText().toString().trim();
        last = lastname.getText().toString().trim();
        emailid = email.getText().toString().trim();
        pass = password.getText().toString().trim();
        boolean valid = isValidEmail(emailid);
        if (first.length() > 15) {
            Toast.makeText(SignUp.this, "First Name can't be more than 15", Toast.LENGTH_SHORT).show();
        } else if (first.length() == 0) {
            Toast.makeText(SignUp.this, "Please Enter the First Name", Toast.LENGTH_SHORT).show();
        } else if (last.length() == 0) {
            Toast.makeText(SignUp.this, "Please Enter the Last Name", Toast.LENGTH_SHORT).show();
        } else if (pass.length() == 0) {
            Toast.makeText(SignUp.this, "Please Enter the Password Name", Toast.LENGTH_SHORT).show();
        } else if (last.length() > 15) {
            Toast.makeText(SignUp.this, "Last Name can't be more than 15", Toast.LENGTH_SHORT).show();
        } else if (valid == false) {
            Toast.makeText(SignUp.this, "Invalid Email-ID", Toast.LENGTH_SHORT).show();
        } else if (pass.length() < 5) {
            Toast.makeText(SignUp.this, "Password can't be less than 5", Toast.LENGTH_SHORT).show();
        } else {
            Map<String, String> requ = new HashMap<String, String>();
            requ.put("first_name", first);
            requ.put("last_name", last);
            requ.put("email", emailid);
            requ.put("password", pass);

            String url = "http://139.59.70.9/api/users";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(requ), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONObject status = response.getJSONObject("status");
                        int statuscode = status.getInt("code");
                        JSONObject data = response.getJSONObject("data");
                        String id = data.getString("_id");
                        if (statuscode == 201) {
                            Toast.makeText(SignUp.this, "Verification Email have been to send to your E-mail ID", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, VerifyMail.class);
                            intent.putExtra("category", id);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUp.this, "Something Went Wrong!!!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body = "body";
                    if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        Toast.makeText(SignUp.this, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        String statuscode = String.valueOf(error.networkResponse.statusCode);
                        if (error.networkResponse.data != null) {
                            try {
                                body = new String(error.networkResponse.data, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                        }
                        if (statuscode.equals("422")) {
                            Toast.makeText(SignUp.this, "E-Mail already used!!!", Toast.LENGTH_SHORT).show();
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


            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            SingletonRequest.getInstance(this.getApplicationContext()).addtoRequestQueue(request);
        }

        btnSignUp.setEnabled(true);

    }


    public boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty()) {
            return false;
        } else {
            if (email.matches(emailPattern)) {
                return true;
            } else {
                return false;
            }
        }
    }
}

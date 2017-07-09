package com.example.p.cityguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class VerifyMail extends AppCompatActivity {

    EditText verify;
    String id;
    Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifymail);
        verify = (EditText) findViewById(R.id.editVerify);
        btnVerify = (Button) findViewById(R.id.btnVerify);
        id = getIntent().getStringExtra("category");

    }

    public void verify(View v) {
        btnVerify.setEnabled(false);
        final String securitycode = verify.getText().toString().trim();
        if (securitycode.isEmpty()) {
            Toast.makeText(VerifyMail.this, "Please Enter the Security Code!!!", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject object = new JSONObject();
            try {
                object.put("security_code", securitycode);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = "http://139.59.70.9/api/verify/" + id;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject status = response.getJSONObject("status");
                        int code = status.getInt("code");
                        if (code == 200) {
                            Toast.makeText(VerifyMail.this, "Email Successfully Validated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(VerifyMail.this, Login.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(VerifyMail.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(VerifyMail.this, "Please Check your Internet Connection!!!", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(VerifyMail.this, "Invalid Code!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            });
            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            SingletonRequest.getInstance(this.getApplicationContext()).addtoRequestQueue(request);
        }
        btnVerify.setEnabled(true);
    }
}

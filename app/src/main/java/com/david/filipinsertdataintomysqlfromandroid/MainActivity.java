package com.david.filipinsertdataintomysqlfromandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    EditText firstname, lastname, age;
    Button insert, show;
    TextView result;
    RequestQueue requestQueue;
    String insertUrl = "http://foaletrading.com/post/insertStudent.php";
    String showUrl = "http://foaletrading.com/post/showStudents.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        age = (EditText) findViewById(R.id.age);
        insert = (Button) findViewById(R.id.insert);
        show = (Button) findViewById(R.id.show);
        result = (TextView) findViewById(R.id.textView);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        showUrl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray students = response.getJSONArray("students");
                            for (int i = 0; i < students.length(); i++) {
                                JSONObject student = students.getJSONObject(i);

                                String firstname = student.getString("firstname");
                                String lastname = student.getString("lastname");
                                String age = student.getString("age");

                                result.append(firstname + " " + lastname + "" + age + "\n");
                            }
                            result.append("===\n");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());

                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("firstname", firstname.getText().toString());
                        parameters.put("lastname", lastname.getText().toString());
                        parameters.put("age", age.getText().toString());

                        return parameters;
                    }
                };
                requestQueue.add(request);
            }

        });

    }
                            }

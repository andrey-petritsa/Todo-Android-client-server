package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class WorkCreateClick extends AppCompatActivity {

    private String token;
    private Integer task_id;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_create_click);
        queue = Volley.newRequestQueue(this);

        Intent main_activity_intent = getIntent();
        token = main_activity_intent.getStringExtra("token");
        task_id = main_activity_intent.getIntExtra("task_id", -1);

        Button btn_create_work = findViewById(R.id.work_create_click_button_create_work);


        btn_create_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView work_name_tv = findViewById(R.id.work_create_click_text_view_name);
                final String work_name = work_name_tv.getText().toString();
                TextView work_date_tv = findViewById(R.id.work_create_click_text_view_date);
                final String date = work_date_tv.getText().toString();
                createWork(task_id, work_name, date);
            }
        });
    }

    private void createWork(int task_id, String work_name, String work_date) {
        String create_work_url = String.format("%s?method=createWork&params={token=%s,task_id=%d,work_name=%s,work_date=%s)", Settings.ADRESS, token, task_id, work_name, work_date);
        StringRequest request_create_work = new StringRequest(Request.Method.GET, create_work_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Respose Error", error.getMessage());
            }
        });

        queue.add(request_create_work);
    }
}

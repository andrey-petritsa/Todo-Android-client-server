package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class WorkClick extends AppCompatActivity {

    RequestQueue request_queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_click2);

        request_queue = Volley.newRequestQueue(this);
        Bundle second_activity_data = getIntent().getExtras();
        final String token = second_activity_data.getString("token");
        final Work work = (Work) second_activity_data.getSerializable(Work.class.getSimpleName());

        //Настраиваем кнопки
        Button btnChangeName = (Button) findViewById(R.id.btn_name);
        //Button btnPriority = (Button) findViewById(R.id.btn_priority);
        Button btnDone = (Button) findViewById(R.id.btn_done);

        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView new_name_tv = findViewById(R.id.ETname);
                String new_name = new_name_tv.getText().toString();
                changeName(work, token, new_name);
            }
        });

        /*btnPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView new_priority_tv = findViewById(R.id.ETPriority);
                int new_priority = Integer.parseInt(new_priority_tv.getText().toString());
                changePriotiy(work, token, new_priority);
            }
        });*/

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDone(work, token);
            }
        });
    }

    private void changeName(Work work, String token, String new_name) {
        String change_name_url = String.format("%s?method=changeWorkName&params={work_id=%d,token=%s,name=%s}", Settings.ADRESS, work.id, token, new_name);
        StringRequest request = new StringRequest(Request.Method.GET, change_name_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("request", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Respose Error", error.getMessage());
            }
        });

        request_queue.add(request);
    }
/*
    private void changePriotiy(Work work, String token, int new_priority) {
        String change_priority_url = String.format("%s?method=changeWorkPriority&params={work_id=%d,token=%s,work_priority=%d}", Settings.ADRESS, work.id, token, new_priority);
        StringRequest request = new StringRequest(Request.Method.GET, change_priority_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("request", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Respose Error", error.getMessage());
            }
        });

        request_queue.add(request);
    }*/

    private void changeDone(Work work, String token) {
        String done_work_url = String.format("%s?method=changeWorkDone&params={work_id=%d,token=%s}", Settings.ADRESS, work.id, token);
        StringRequest request = new StringRequest(Request.Method.GET, done_work_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("request", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Respose Error", error.getMessage());
            }
        });

        request_queue.add(request);
    }
}

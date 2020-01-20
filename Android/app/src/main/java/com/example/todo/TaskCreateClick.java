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

public class TaskCreateClick extends AppCompatActivity {

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create_click);
        queue = Volley.newRequestQueue(this);

        Bundle data = getIntent().getExtras();
        final String token = data.getString("token");

        Button btn_create_task = findViewById(R.id.btn_create_task);
        btn_create_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTask(token);
            }
        });

    }

    private void createTask(String token) {
        TextView task_name_tv = (TextView) findViewById(R.id.taskcreateclick_edittext_name);
        TextView task_priority_tv = (TextView) findViewById(R.id.taskcreateclick_edittext_priority);

        String task_name = task_name_tv.getText().toString();
        int task_priority = Integer.parseInt(task_priority_tv.getText().toString());

        String create_task_url = String.format("%s?method=createTask&params={token=%s,task_name=%s,task_priority=%d}", Settings.ADRESS, token, task_name, task_priority);
        StringRequest request_create_task = new StringRequest(Request.Method.GET, create_task_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("request", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Response Error", error.getMessage());
            }
        });
        queue.add(request_create_task);
    }
}

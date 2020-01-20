package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskClick extends AppCompatActivity {
    private Intent workCreateI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_with_task);


        Bundle second_activity_data = getIntent().getExtras();
        final String token = second_activity_data.getString("token");
        final Task task = (Task) second_activity_data.getSerializable(Task.class.getSimpleName());

        workCreateI = new Intent(TaskClick.this, WorkCreateClick.class);
        workCreateI.putExtra("token", token);

        //Настраиваем кнопку выполнения задачи
        Button btn_done_task = findViewById(R.id.btn_done_task);
        btn_done_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneTask(task, token);
            }
        });

        //Настраиваем кнопку изменения приоритета
        Button btn_change_priority = findViewById(R.id.btn_change_priority);
        btn_change_priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText priority_view = (EditText) findViewById(R.id.task_priority);
                String priority_string = priority_view.getText().toString();
                int priority_integer = Integer.parseInt(priority_string);
                changePriority(task, token, priority_integer);
            }
        });

        //Настраиваем кнопку изменения имени задачи
        Button btn_change_name = findViewById(R.id.btn_change_name);
        btn_change_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name_view = (EditText) findViewById(R.id.task_name);
                changeName(task, token, name_view.getText().toString());
            }
        });

        Button btn_create_work = findViewById(R.id.TaskClick_btn_create_work);
        btn_create_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workCreateI.putExtra("task_id", task.id);
                startActivity(workCreateI);
            }
        });
    }

    private void doneTask(Task task, String token) {
        String done_task_url = String.format("%s?method=doneTask&params={task_id=%d,token=%s}", Settings.ADRESS, task.id, token);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request_done_tasks = new StringRequest(Request.Method.GET, done_task_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("my_requests", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Respose Error", error.getMessage());
            }
        });
        queue.add(request_done_tasks );
    }

    private void changeName(Task task, String token, String name) {
        String change_name_url = String.format("%s?method=changeTaskName&params={task_id=%d,token=%s,name=%s}", Settings.ADRESS, task.id, token, name );
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request_done_tasks = new StringRequest(Request.Method.GET, change_name_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("my_requests", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Respose Error", error.getMessage());
            }
        });
        queue.add(request_done_tasks );
    }

    private void changePriority(Task task, String token, int priority) {
        String change_priority_url = String.format("%s?method=changeTaskPriority&params={task_id=%d,token=%s,priority=%d}", Settings.ADRESS, task.id, token, priority);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request_done_tasks = new StringRequest(Request.Method.GET, change_priority_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("my_requests", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Respose Error", error.getMessage());
            }
        });
        queue.add(request_done_tasks );
    }
}

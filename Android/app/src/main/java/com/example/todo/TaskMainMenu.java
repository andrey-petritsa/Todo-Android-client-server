package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskMainMenu extends AppCompatActivity {

    private String token;
    private Intent taskClickI;
    private Intent workClickI;
    private Intent taskCreateClickI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        setupActivity();
    }

    private void setupActivity() {
        //Получаем токен из первой активности
        Intent main_activity_intent = getIntent();
        token = main_activity_intent.getStringExtra("token");
        taskClickI = new Intent(TaskMainMenu.this, TaskClick.class);
        taskClickI.putExtra("token", token);

        workClickI = new Intent(TaskMainMenu.this, WorkClick.class);
        workClickI.putExtra("token", token);

        taskCreateClickI = new Intent(TaskMainMenu.this, TaskCreateClick.class);
        taskCreateClickI.putExtra("token", token);

        final LinearLayout vertical_layout = findViewById(R.id.vertical_layout);
        Button btn_create_task = new Button(TaskMainMenu.this);
        btn_create_task.setText("Создать задачу");
        btn_create_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(taskCreateClickI);
            }
        });
        vertical_layout.addView(btn_create_task);

        //Настраиваем отправку запросова
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("%s?method=getTask&params={token=%s}", Settings.ADRESS, token);

        StringRequest request_get_tasks = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Парсим полученные из json данные в объект Task
                        ArrayList<Task> tasks = parseTasks(response);
                        //LinearLayout vertical_layout = findViewById(R.id.vertical_layout);
                        insertTasksAtScreen(tasks, vertical_layout);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Respose Error", error.getMessage());
            }
        });

        String get_share_tasks_url = String.format("%s?method=getShareTasks&params={token=%s}", Settings.ADRESS, token);
        StringRequest request_get_share_tasks = new StringRequest(Request.Method.GET, get_share_tasks_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Парсим полученные из json данные в объект Task
                        ArrayList<Task> tasks = parseTasks(response);
                        //LinearLayout vertical_layout = findViewById(R.id.vertical_layout);
                        insertTasksAtScreen(tasks, vertical_layout);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Respose Error", error.getMessage());
            }
        });

        queue.add(request_get_tasks);
        queue.add(request_get_share_tasks);
    }

    protected void onRestart() {
        super.onRestart();
        LinearLayout vertical_layout = (LinearLayout) findViewById(R.id.vertical_layout);
        vertical_layout.removeAllViews();
        setupActivity();

    }

    private void insertTasksAtScreen(ArrayList<Task> tasks, LinearLayout screen) {
        //Расставим виджеты на vertical_layout второй активности (Виджеты задачи)
        for (Iterator<Task> it = tasks.iterator(); it.hasNext();) {
            Task task = it.next();
            LinearLayout task_layout = getTaskLayout(task);
            screen.addView(task_layout);
        }
    }

    private void drawBorder(LinearLayout layout) {
        //Рисуем рамку
        //GradientDrawable border = new GradientDrawable();
        //border.setColor(0xFFFFFFFF); //white background
        //border.setStroke(5, 0xFF000000); //black border with full opacity
        //layout.setBackgroundDrawable(border);
    }

    private int calculate_background_color_by_date(String end_work_date) {
        // календарь на текущую дату
        long milisec_ten_days = 864000000;
        long milisec_five_days = 432000000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-m-dd");
        Date today_date = new Date();
        try {
            Date exp_date = sdf.parse(end_work_date);
            long result_date = exp_date.getTime() - today_date.getTime();

            if (result_date >= milisec_ten_days) {
                return 0xFF00FF00;
            }
            if (result_date >= milisec_five_days) {
                return 0xFFFFA500;
            }
            return 0xFFFF0000;
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return 0x000000;
    }

    //Метод формирует представление задачи в форме набора View
    private LinearLayout getTaskLayout(final Task task)
    {
        LinearLayout main_layout = new LinearLayout(TaskMainMenu.this);

        LinearLayout task_layout = new LinearLayout(TaskMainMenu.this);
        main_layout.setOrientation(LinearLayout.VERTICAL);
        task_layout.setOrientation(LinearLayout.VERTICAL);
        drawBorder(main_layout);
        drawBorder(task_layout);
        main_layout.setPadding(Settings.MAIN_LAYOUT_PADDING_LEFT, Settings.MAIN_LAYOUT_PADDING_TOP, Settings.MAIN_LAYOUT_PADDING_RIGHT, Settings.MAIN_LAYOUT_PADDING_BOTTOM);


        //в main_layout находится task_layout и work_layout
        TextView tv_task_name= new TextView(TaskMainMenu.this);
        tv_task_name.setText(String.format("Название задачи: %s", task.name));
        TextView tv_task_priority = new TextView(TaskMainMenu.this);
        tv_task_priority.setText(String.format("Приоритет: %d", task.priority));
        TextView tv_task_date = new TextView(TaskMainMenu.this);
        tv_task_date.setText(String.format("Создано: %s", task.date));
        TextView tv_task_done = new TextView(TaskMainMenu.this);
        tv_task_done.setText(String.format("Статус выполнения: %s", task.done));

        task_layout.addView(tv_task_name);
        task_layout.addView(tv_task_priority);
        task_layout.addView(tv_task_date);
        task_layout.addView(tv_task_done);

        task_layout.setBackgroundColor(0x333F51B5);
        main_layout.addView(task_layout);

        //Привяжем к нажатию на задачу изменение задачи
        task_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskClickI.putExtra(Task.class.getSimpleName(), task);
                startActivity(taskClickI);
            }
        });
        //Теперь создадим слои для работ вставим эти слои внутрь задачи
        for (Iterator<Work> work_iterator = task.works.iterator(); work_iterator.hasNext();) {
            final Work work = work_iterator.next();
            LinearLayout work_layout = new LinearLayout(TaskMainMenu.this);

            drawBorder(work_layout);
            work_layout.setOrientation(LinearLayout.VERTICAL);

            TextView tv_work_name = new TextView(TaskMainMenu.this);
            tv_work_name.setText(String.format("Название подзадачи: %s", work.name));
            TextView tv_work_date = new TextView(TaskMainMenu.this);
            tv_work_date.setText(String.format("Создано: %s", work.date));
            TextView tv_work_planned_end_date = new TextView(TaskMainMenu.this);
            tv_work_planned_end_date.setText(String.format("Дата завершения: %s", work.planned_end_date));
            TextView tv_work_done = new TextView(TaskMainMenu.this);
            tv_work_done.setText(String.format("Статус выполнения: %s", work.done));

            work_layout.addView(tv_work_name);
            work_layout.addView(tv_work_date);
            work_layout.addView(tv_work_planned_end_date);
            work_layout.addView(tv_work_done);
            work_layout.setPadding(Settings.WORK_LAYOUT_PADDING_LEFT, Settings.WORK_LAYOUT_PADDING_TOP, Settings.WORK_LAYOUT_PADDING_RIGHT ,Settings.WORK_LAYOUT_PADDING_BOTTOM );

            //Привяжем к подзадаче щелчек
            work_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    workClickI.putExtra(Work.class.getSimpleName(), work);
                    startActivity(workClickI);
                }
            });

            //Проверяем цвет
            int color = calculate_background_color_by_date(work.planned_end_date);
            work_layout.setBackgroundColor(color);
            main_layout.addView(work_layout);
        }
        return main_layout;
    }

    private ArrayList<Task> parseTasks(String json_string) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            JSONArray json_array = new JSONArray(json_string);
            //Пройдемся по каждой задаче

            for (int i = 0; i < json_array.length(); i++) {
                JSONObject json_task = json_array.getJSONObject(i);

                //Получим информацию о задаче
                int task_id = json_task.getInt("task_id");
                String task_name = json_task.getString("task_name");
                String take_date = json_task.getString("date");
                int task_priority = json_task.getInt("priority");
                String task_done = json_task.getString("done");


                //Получим информацию о работе внутри задачи
                ArrayList<Work> works = new ArrayList<>();
                JSONArray json_work_array = json_task.getJSONArray("works");
                for (int j = 0; j < json_work_array.length(); j++) {
                    JSONObject json_work = json_work_array.getJSONObject(j);
                    int work_id = json_work.getInt("work_id");
                    String work_name = json_work.getString("name");
                    String work_date = json_work.getString("date");
                    String work_planned_end_date = json_work.getString("planned_end_date");
                    String work_done = json_work.getString("done");
                    works.add(new Work(work_id, work_name, work_date, work_planned_end_date, work_done));
                }
                tasks.add(new Task(task_id, task_name, take_date, task_priority, task_done, works));
            }

        } catch (JSONException error) {
            Log.e("json", error.getMessage());
        }
        return tasks;
    }





}

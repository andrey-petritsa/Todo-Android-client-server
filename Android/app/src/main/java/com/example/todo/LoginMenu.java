package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnEnter = (Button) findViewById(R.id.button_enter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((TextView) findViewById(R.id.email_enter)).getText().toString();
                String password = ((TextView) findViewById(R.id.password_enter)).getText().toString();
                Authorise(email, password);
            }
        });

        Button btnRegister = findViewById(R.id.LoginMenu_Button_Register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerI = new Intent(LoginMenu.this, Register.class);
                startActivity(registerI);
            }
        });
    }

    private void Authorise(String email, String password){
        final TextView textView = (TextView) findViewById(R.id.LoginHint);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format("%s?method=login&params={email=%s,password=%s}",Settings.ADRESS, email, password);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Пытаемся залогиниться
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String token = jsonObject.getString("token");

                            //Запускаем вторую активность если прошли успешно авторизацю
                            Intent intent = new Intent(LoginMenu.this, TaskMainMenu.class);
                            intent.putExtra("token", token);
                            startActivity(intent);


                        } catch (JSONException e) {
                            textView.setText("Ошибка");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.getMessage());
            }
        });

        queue.add(stringRequest);
    }


}

package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

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

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        queue = Volley.newRequestQueue(this);

        Button btn_register = findViewById(R.id.Register_Button_Register);
        TextView email_tv = findViewById(R.id.Register_TextView_Email);
        TextView password_tv = findViewById(R.id.Register_TextView_Pass);



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn_register = findViewById(R.id.Register_Button_Register);
                TextView email_tv = findViewById(R.id.Register_TextView_Email);
                TextView password_tv = findViewById(R.id.Register_TextView_Pass);
                TextView first_name_tv = findViewById(R.id.Register_TextView_first_name);
                TextView last_name_tv = findViewById(R.id.Register_TextView_last_name);
                TextView patron_name_tv = findViewById(R.id.Register_TextView_patron_name);

                String email = email_tv.getText().toString();
                String password = password_tv.getText().toString();
                String first_name = first_name_tv.getText().toString();
                String last_name = last_name_tv.getText().toString();
                String patron_name = patron_name_tv.getText().toString();

                registerUser(email, password, first_name, last_name, patron_name);
            }
        });
    }

    private void registerUser(String email, String password, String first_name, String last_name, String patron_name) {
        String register_url = String.format("%s?method=registerUser&params={email=%s,password=%s,first_name=%s,last_name=%s,patron_name=%s}", Settings.ADRESS, email, password, first_name, last_name, patron_name);
        StringRequest register_request = new StringRequest(Request.Method.GET, register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(register_request);
    }
}

package com.example.todo;

import java.io.Serializable;
import java.util.HashMap;

public class Work implements Serializable {
    public int id;
    public String name;
    public String date;
    public String planned_end_date;
    public String done;

    Work(int id, String name, String date, String planned_end_date, String done) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.planned_end_date = planned_end_date;
        this.done = done;
    }
}

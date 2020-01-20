package com.example.todo;
import com.example.todo.Work;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Task implements Serializable {
    public int id;
    public String name;
    public String date;
    public int priority;
    public String done;
    public ArrayList<Work> works;

    Task(int id, String name, String date, int priority, String done, ArrayList<Work> works) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.priority = priority;
        this.done = done;
        this.works = works;
    }

}

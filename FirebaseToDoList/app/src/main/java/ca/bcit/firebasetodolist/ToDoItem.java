package ca.bcit.firebasetodolist;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoItem {
    private String task;
    private String who;
    private Date dueDate;
    private boolean done;

    public ToDoItem() {}

    public ToDoItem(String task, String who, Date dueDate, boolean done) {
        this.task = task;
        this.who = who;
        this.dueDate = dueDate;
        this.done = done;
    }

    public String getFormattedDueDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        return format.format(dueDate);
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}

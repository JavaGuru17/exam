package reminder_scheduler;

import java.util.Date;

public class Reminder {
    private String task;
    private Date time;
    private boolean completed;

    public Reminder(String task, Date time) {
        this.task = task;
        this.time = time;
        this.completed = false;
    }

    public String getTask() {
        return task;
    }

    public Date getTime() {
        return time;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

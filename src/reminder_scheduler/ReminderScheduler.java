package reminder_scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReminderScheduler {
    private ScheduledExecutorService executor;
    private List<Reminder> reminders;

    public ReminderScheduler() {
        executor = Executors.newScheduledThreadPool(1);
        reminders = new ArrayList<>();
    }

    public void scheduleReminder(Reminder reminder) {
        reminders.add(reminder);
        long delay = reminder.getTime().getTime() - System.currentTimeMillis();
        executor.schedule(() -> runReminder(reminder), delay, TimeUnit.MILLISECONDS);
    }

    public void cancelReminder(Reminder reminder) {
        reminder.setCompleted(true);
        boolean removed = reminders.remove(reminder);

        if (removed) {
            System.out.println("Reminder canceled: " + reminder.getTask());
        } else {
            System.out.println("No reminder found for task: " + reminder.getTask());
        }
    }

    public void markReminderAsCompleted(Reminder reminder) {
        reminder.setCompleted(true);
        System.out.println("Reminder marked as completed: " + reminder.getTask());
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void shutdown() {
        executor.shutdown();
    }

    private void runReminder(Reminder reminder) {
        if (!reminder.isCompleted()) {
            System.out.println("Reminder: " + reminder.getTask() + ", Time: " + reminder.getTime() + ", is due now!");
        }
    }
}

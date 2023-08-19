package reminder_scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

//todo - Create a program that simulates a simple reminder application.
// The program should allow users to schedule reminders for specific tasks
// at designated times
// Requirements:
//    Create a Reminder class that represents a single reminder. It should have the following properties: task (String), time (Date), and completed (boolean).
//    Implement a ReminderScheduler class that manages the scheduling and execution of reminders using a ScheduledThreadPoolExecutor.
//    The ReminderScheduler class should have the following methods:
//        scheduleReminder(Reminder reminder): Schedules a reminder for execution at the specified time.
//        cancelReminder(Reminder reminder): Cancels a scheduled reminder.
//        markReminderAsCompleted(Reminder reminder): Marks a reminder as completed.
//    The program should provide a simple command-line interface (CLI) for users to interact with the ReminderScheduler. Users should be able to:
//        Schedule a new reminder by providing the task description and the time (in the format "yyyy-MM-dd HH:mm:ss").
//        Cancel a scheduled reminder by specifying the task description.
//        Mark a reminder as completed by specifying the task description.
public class ExecutorServices {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ReminderScheduler scheduler = new ReminderScheduler();

        while (true) {
            System.out.println("\nReminder Application");
            System.out.println("1. Schedule a new reminder");
            System.out.println("2. Cancel a scheduled reminder");
            System.out.println("3. Mark a reminder as completed");
            System.out.println("4. Exit");

            System.out.print("Enter your choice (1-4): ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    String task = getUserInput("Enter the task description: ");
                    String timeStr = getUserInput("Enter the reminder time (yyyy-MM-dd HH:mm:ss): ");
                    Date reminderTime = parseDate(timeStr);

                    if (reminderTime == null) {
                        System.out.println("Invalid datetime format. Please try again.");
                        continue;
                    }

                    Reminder reminder = new Reminder(task, reminderTime);
                    scheduler.scheduleReminder(reminder);
                    System.out.println("Reminder scheduled for task: " + task + ", Time: " + reminderTime);
                    break;

                case "2":
                    task = getUserInput("Enter the task description to cancel: ");
                    Reminder reminderToCancel = findReminderByTask(scheduler, task);

                    if (reminderToCancel != null) {
                        scheduler.cancelReminder(reminderToCancel);
                    } else {
                        System.out.println("No active reminder found for task: " + task);
                    }
                    break;

                case "3":
                    task = getUserInput("Enter the task description to mark as completed: ");
                    Reminder reminderToMark = findReminderByTask(scheduler, task);

                    if (reminderToMark != null) {
                        scheduler.markReminderAsCompleted(reminderToMark);
                    } else {
                        System.out.println("No active reminder found for task: " + task);
                    }
                    break;

                case "4":
                    System.out.println("Exiting...");
                    scheduler.shutdown();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    private static Date parseDate(String dateStr) {
        try {
            return DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    private static Reminder findReminderByTask(ReminderScheduler scheduler, String task) {
        List<Reminder> reminders = scheduler.getReminders();
        for (Reminder reminder : reminders) {
            if (reminder.getTask().equals(task) && !reminder.isCompleted()) {
                return reminder;
            }
        }
        return null;
    }
}

package project;

import java.util.*;

public class Library {
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<String> logs = new ArrayList<>();

    // Inner class
    public class Notification {
        private String message;

        public Notification(String msg) {
            this.message = msg;
            logs.add("NOTIFICATION: " + msg);
        }

        public String getMessage() {
            return message;
        }
    }

    public void addItem(Item item) {
        items.add(item);
        new Notification("Added item: " + item.getTitle());
    }

    public void addUser(User user) {
        users.add(user);
        new Notification("Added user: " + user.getName());
    }

    public Item findItem(String id) {
        for (Item i : items) {
            if (i.getId().equals(id)) return i;
        }
        return null;
    }

    public User findUser(String id) {
        for (User u : users) {
            if (u.getId().equals(id)) return u;
        }
        return null;
    }

    public TreeSet<Item> getSortedItems() {
        return new TreeSet<>(items);
    }

    public void printLogs() {
        for (String s : logs) {
            System.out.println(s);
        }
    }
}

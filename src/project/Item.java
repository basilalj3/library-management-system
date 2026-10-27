package project;

/*
 Abstract superclass for all library items.
 */
public abstract class Item implements Comparable<Item>, Cloneable {
    // Protected fields so subclasses can access them.
    protected String id;
    protected String title;
    protected int year;
    protected ItemType type;

    public Item() {
        this.id = "Unknown";
        this.title = "Untitled";
        this.year = 0;
        this.type = ItemType.BOOK;
    }
    // Parameterized constructor for creating real items.
    public Item(String id, String title, int year, ItemType type) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.type = type;

    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public ItemType getType() {
        return type;
    }

    // Abstract
    public abstract double calculateLateFee(int DaysLate);

    @Override
    public int compareTo(Item other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(Object o) {
        if (!(o instanceof Item)) return false;
        Item i = (Item) o;
        return id.equals(i.id) && title.equals(i.title) && year == i.year;

    }

    public String toString() {
        return String.format("%s (ID: %s, %d)", title, id, year);

    }
}



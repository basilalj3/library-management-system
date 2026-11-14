package project.model;

import jakarta.persistence.*;

import java.util.Objects;

/*
 Abstract superclass for all library items.
 Stored in one table "items"; the item_type column says which subclass each row is.
 */
@Entity
@Table(name = "items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type")
public abstract class Item implements Comparable<Item>, Cloneable {
    // Protected fields so subclasses can access them.
    @Id
    protected String id;

    @Column(nullable = false)
    protected String title;

    @Column(name = "publish_year") // "year" is a reserved word in SQL
    protected int year;

    // Read-only view of the discriminator column (Hibernate fills it in)
    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", insertable = false, updatable = false)
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
    public abstract double calculateLateFee(int daysLate);

    @Override
    public int compareTo(Item other) {
        return this.title.compareToIgnoreCase(other.title);
    }

    @Override
    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item i = (Item) o;
        return id.equals(i.id) && title.equals(i.title) && year == i.year;
    }

    // Must use the same fields as equals(), otherwise HashSet/HashMap break
    @Override
    public int hashCode() {
        return Objects.hash(id, title, year);
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %s, %d)", title, id, year);
    }
}

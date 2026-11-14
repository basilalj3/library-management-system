package project.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("EBOOK")
public class EBook extends Item {
    private String downloadLink;

    public EBook() {
        super();
    }

    public EBook(String id, String title, int year, String link) {
        super(id, title, year, ItemType.EBOOK);
        this.downloadLink = link;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    @Override
    public double calculateLateFee(int daysLate) {
        return 0;
    }

    @Override
    public String toString() {
        return "EBook: " + super.toString() + " [Download link: " + downloadLink + "]";
    }
}

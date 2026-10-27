package project;

public class EBook extends Item {
    private String downloadLink;

    public EBook() {
        super();
    }

    public EBook(String id, String title, int year, String link) {
        super(id, title, year, ItemType.EBOOK);
        this.downloadLink = link;
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


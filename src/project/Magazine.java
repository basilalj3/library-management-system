package project;

public class Magazine extends Item implements Borrowable {
    private String borrowedById; // null = available

    public Magazine() {
        super();
    }

    public Magazine(String id, String title, int year) {
        super(id, title, year, ItemType.MAGAZINE);
    }

    @Override
    public double calculateLateFee(int daysLate) {
        return daysLate * 0.3;
    }

    @Override
    public void borrowItem(String userId) throws ItemNotAvailableException {
        if (borrowedById != null) {
            throw new ItemNotAvailableException("Magazine not available.");
        }
        borrowedById = userId;
    }

    @Override
    public void returnItem(String userId) throws Exception {
        if (borrowedById == null) {
            throw new Exception("Magazine is not borrowed.");
        }

        if (!borrowedById.equals(userId)) {
            throw new Exception("This user did not borrow the magazine.");
        }

        borrowedById = null; // magazine becomes available again
    }

    @Override
    public boolean isAvailable() {
        return borrowedById == null;
    }

    @Override
    public String toString() {
        String status;
        if (borrowedById == null) {
            status = "Available";
        } else {
            status = "Borrowed";
        }

        return "Magazine: " + super.toString() + " [" + status + "]";
    }
}


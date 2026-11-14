package project.model;

import org.junit.jupiter.api.Test;
import project.exception.InvalidReturnException;
import project.exception.ItemNotAvailableException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 Plain unit tests for the model classes (no Spring, no database).
 */
class ItemTest {

    @Test
    void lateFeesDependOnItemType() {
        assertThat(new Book("B1", "A", 2020).calculateLateFee(10)).isEqualTo(5.0);
        assertThat(new Magazine("M1", "A", 2020).calculateLateFee(10)).isEqualTo(3.0);
        assertThat(new EBook("E1", "A", 2020, "link").calculateLateFee(10)).isEqualTo(0.0);
    }

    @Test
    void bookCanBeBorrowedAndReturned() throws Exception {
        Book book = new Book("B1", "Java", 2020);
        assertThat(book.isAvailable()).isTrue();

        book.borrowItem("S1");
        assertThat(book.isAvailable()).isFalse();

        book.returnItem("S1");
        assertThat(book.isAvailable()).isTrue();
    }

    @Test
    void cannotBorrowABorrowedBook() throws Exception {
        Book book = new Book("B1", "Java", 2020);
        book.borrowItem("S1");

        assertThatThrownBy(() -> book.borrowItem("S2"))
                .isInstanceOf(ItemNotAvailableException.class)
                .hasMessage("Book not available.");
    }

    @Test
    void cannotReturnAnItemThatIsNotBorrowed() {
        Magazine magazine = new Magazine("M1", "Tech", 2023);

        assertThatThrownBy(() -> magazine.returnItem("S1"))
                .isInstanceOf(InvalidReturnException.class)
                .hasMessage("Magazine is not borrowed.");
    }

    @Test
    void onlyTheBorrowerCanReturn() throws Exception {
        Book book = new Book("B1", "Java", 2020);
        book.borrowItem("S1");

        assertThatThrownBy(() -> book.returnItem("S2"))
                .isInstanceOf(InvalidReturnException.class)
                .hasMessage("This user did not borrow the book.");
        assertThat(book.isAvailable()).isFalse();
    }

    @Test
    void toStringHasClosingParenthesis() {
        assertThat(new Book("B1", "Java", 2020).toString())
                .isEqualTo("Book: Java (ID: B1, 2020) [Available]");
    }

    @Test
    void equalItemsHaveEqualHashCodes() {
        Book a = new Book("B1", "Java", 2020);
        Book b = new Book("B1", "Java", 2020);

        assertThat(a).isEqualTo(b);
        assertThat(a.hashCode()).isEqualTo(b.hashCode());

        Set<Item> set = new HashSet<>(List.of(a, b));
        assertThat(set).hasSize(1); // only works because hashCode() matches equals()
    }

    @Test
    void itemsSortByTitleIgnoringCase() {
        TreeSet<Item> sorted = new TreeSet<>(List.of(
                new Book("B1", "zebra", 2020),
                new Book("B2", "Apple", 2020),
                new Magazine("M1", "mango", 2020)));

        assertThat(sorted).extracting(Item::getTitle).containsExactly("Apple", "mango", "zebra");
    }

    @Test
    void cloneCreatesAnEqualButSeparateObject() {
        Book book = new Book("B1", "Java", 2020);
        Item copy = book.clone();

        assertThat(copy).isEqualTo(book).isNotSameAs(book);
    }
}

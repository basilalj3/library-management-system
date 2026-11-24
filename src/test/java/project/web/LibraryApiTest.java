package project.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 Tests the whole app (controller + service + in-memory database) through HTTP.
 Starts with the sample data from DataInitializer.
 @Transactional rolls back after each test, so tests don't affect each other.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LibraryApiTest {

    @Autowired
    MockMvc mvc;

    @Test
    void listsItemsSortedByTitle() throws Exception {
        mvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title", contains("Algorithms", "Java Programming", "Tech Monthly")));
    }

    @Test
    void unknownItemReturns404() throws Exception {
        mvc.perform(get("/api/items/NOPE"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Item not found: NOPE"));
    }

    @Test
    void borrowThenBorrowAgainReturns409() throws Exception {
        mvc.perform(post("/api/items/B001/borrow").param("userId", "S001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(false));

        mvc.perform(post("/api/items/B001/borrow").param("userId", "P001"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value("Book not available."));
    }

    @Test
    void returnByWrongUserReturns400() throws Exception {
        mvc.perform(post("/api/items/B001/borrow").param("userId", "S001"))
                .andExpect(status().isOk());

        mvc.perform(post("/api/items/B001/return").param("userId", "P001"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("This user did not borrow the book."));

        mvc.perform(post("/api/items/B001/return").param("userId", "S001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").value(true));
    }

    @Test
    void ebookCannotBeBorrowed() throws Exception {
        mvc.perform(post("/api/items/E001/borrow").param("userId", "S001"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("EBOOK items cannot be borrowed."));
    }

    @Test
    void borrowWithUnknownUserReturns404() throws Exception {
        mvc.perform(post("/api/items/B001/borrow").param("userId", "X999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addItemSavesItInTheDatabase() throws Exception {
        mvc.perform(post("/api/items").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"type":"EBOOK","id":"E002","title":"Spring Boot","year":2025,"downloadLink":"x.com"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("EBOOK"));

        mvc.perform(get("/api/items/E002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.downloadLink").value("x.com"));
    }

    @Test
    void duplicateItemIdReturns409() throws Exception {
        mvc.perform(post("/api/items").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"type":"BOOK","id":"B001","title":"Copy","year":2020}
                                """))
                .andExpect(status().isConflict());
    }

    @Test
    void invalidItemReturns400WithFieldErrors() throws Exception {
        mvc.perform(post("/api/items").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"type":"BOOK","id":"B9","title":""}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", allOf(containsString("title"), containsString("year"))));
    }

    @Test
    void addUserAndListUsers() throws Exception {
        mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"role":"STUDENT","id":"S002","name":"Sara","departmentOrFaculty":"IT"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.role").value("STUDENT"));

        mvc.perform(get("/api/users"))
                .andExpect(jsonPath("$[*].id", contains("P001", "S001", "S002")));
    }

    @Test
    void lateFeeIsCalculated() throws Exception {
        mvc.perform(get("/api/items/M001/late-fee").param("days", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fee").value(3.0));
    }

    @Test
    void borrowingIsWrittenToTheActivityLog() throws Exception {
        mvc.perform(post("/api/items/B001/borrow").param("userId", "S001"));

        mvc.perform(get("/api/logs"))
                .andExpect(jsonPath("$[0].message").value("Alice borrowed: Java Programming"));
    }

    @Test
    void deleteItemRemovesIt() throws Exception {
        mvc.perform(delete("/api/items/M001"))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/items/M001"))
                .andExpect(status().isNotFound());



    }

    @Test
    void deleteUnknownItemReturns404() throws Exception {
        mvc.perform(delete("/api/items/NOPE"))
                .andExpect(status().isNotFound());
    }

    @Test
    void cannotDeleteABorrowedItem() throws Exception {
        mvc.perform(post("/api/items/B001/borrow").param("userId", "S001"))
                .andExpect(status().isOk());
        mvc.perform(delete("/api/items/B001"))
                .andExpect(status().isConflict());
    }

    @Test
    void filterItemsByYear() throws Exception {
        mvc.perform(get("/api/items").param("year", "2020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", contains("B001")));
    }

    @Test
    void filterByYearWithNoMatchesReturnsEmptyList() throws Exception {
        mvc.perform(get("/api/items").param("year", "1999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}

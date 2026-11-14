package project.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import project.model.ItemType;

/*
 JSON body for POST /api/items, e.g.
 {"type": "BOOK", "id": "B002", "title": "Clean Code", "year": 2008}
 downloadLink is only used for EBOOK.
 */
public record ItemRequest(
        @NotNull ItemType type,
        @NotBlank String id,
        @NotBlank String title,
        @NotNull @PositiveOrZero Integer year,
        String downloadLink) {
}

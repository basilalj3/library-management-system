package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Item;

import java.util.List;

/*
 Spring Data generates the implementation (findAll, findById, save, ...) automatically.
 */
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findByYear(int year);
}

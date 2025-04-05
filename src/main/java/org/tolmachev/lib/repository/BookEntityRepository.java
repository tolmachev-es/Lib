package org.tolmachev.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tolmachev.lib.entity.BookEntity;

import java.util.Optional;

@Repository
public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {
    Optional<BookEntity> findBookEntityByNameAndAuthor(String bookName, String author);
    Optional<BookEntity> findBookEntityByName(String name);
}
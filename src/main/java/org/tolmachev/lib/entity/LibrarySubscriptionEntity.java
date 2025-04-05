package org.tolmachev.lib.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "subscription")
public class LibrarySubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<BookInSubscriptionEntity> books = new HashSet<>();

    public BookInSubscriptionEntity addBook(BookEntity book) {
        BookInSubscriptionEntity bookInSubscriptionEntity = new BookInSubscriptionEntity(this, book);
        book.addSubscription(bookInSubscriptionEntity);
        if (books == null) {
            books = new HashSet<>();
        }
        books.add(bookInSubscriptionEntity);
        return bookInSubscriptionEntity;
    }
}

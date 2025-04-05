package org.tolmachev.lib.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "subscription_book")
public class BookInSubscriptionEntity {
    @EmbeddedId
    private BookSubscriptionId bookSubscriptionId;

    @ManyToOne
    @MapsId("subscriptionId")
    @JoinColumn(name = "subscription_id", insertable = false, referencedColumnName = "id")
    private LibrarySubscriptionEntity subscription;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id", insertable = false, referencedColumnName = "book_id")
    private BookEntity book;

    @Column(name = "start_date")
    private LocalDate startDate;

    public BookInSubscriptionEntity(LibrarySubscriptionEntity subscription, BookEntity book) {
        this.subscription = subscription;
        this.book = book;
        this.bookSubscriptionId = new BookSubscriptionId(subscription.getId(), book.getId());
    }

    public void setBook(BookEntity book) {
        this.book = book;
        if (bookSubscriptionId == null) {
            bookSubscriptionId = new BookSubscriptionId();
        }
        bookSubscriptionId.setBookId(book != null ? book.getId() : null);
    }

    public void setSubscription(LibrarySubscriptionEntity subscription) {
        this.subscription = subscription;
        if (bookSubscriptionId == null) {
            bookSubscriptionId = new BookSubscriptionId();
        }
        bookSubscriptionId.setSubscriptionId(subscription != null ? subscription.getId() : null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        BookInSubscriptionEntity that = (BookInSubscriptionEntity) o;
        return Objects.equals(subscription, that.subscription) && Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscription, book);
    }
}

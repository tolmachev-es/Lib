package org.tolmachev.lib.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BookSubscriptionId implements Serializable {
    @Column(name = "subscription_id")
    private Long subscriptionId;
    @Column(name = "book_id")
    private Long bookId;

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
package org.tolmachev.lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tolmachev.lib.entity.BookInSubscriptionEntity;
import org.tolmachev.lib.entity.BookSubscriptionId;

@Repository
public interface BookInSubscriptionRepository extends JpaRepository<BookInSubscriptionEntity, BookSubscriptionId> {
}
package org.tolmachev.lib.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.tolmachev.lib.entity.BookEntity;
import org.tolmachev.lib.entity.BookInSubscriptionEntity;
import org.tolmachev.lib.entity.LibrarySubscriptionEntity;
import org.tolmachev.lib.mappers.SubscriptionMapper;
import org.tolmachev.lib.model.Book;
import org.tolmachev.lib.model.Data;
import org.tolmachev.lib.model.Subscription;
import org.tolmachev.lib.repository.SubscriptionRepository;
import org.tolmachev.lib.service.LibraryService;
import org.tolmachev.lib.service.SubscriptionService;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.tolmachev.lib.BaseTestClass.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class LibraryIntegrationTest {
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private SubscriptionRepository repository;
    @Autowired
    private SubscriptionMapper subscriptionMapper;
    @Autowired
    private LibraryService libraryService;

    @Test
    void checkCountSave() {
        List<Data> data = List.of(getDataFirst(), getDataSecond(), getDataThird());
        subscriptionService.saveSubscriptions(data);
        List<LibrarySubscriptionEntity> all = repository.findAll();

        assertEquals(3, all.size());
        Set<BookInSubscriptionEntity> collect = all.stream()
                                                   .map(LibrarySubscriptionEntity::getBooks)
                                                   .flatMap(Collection::stream)
                                                   .collect(Collectors.toSet());
        assertEquals(3, collect.size());
    }

    @Test
    void checkSaveOldData(){
        Data dataFirst = getDataFirst();
        List<Data> data = List.of(getDataFirst());
        subscriptionService.saveSubscriptions(data);
        List<LibrarySubscriptionEntity> all = repository.findAll();

        assertEquals(1, all.size());


        LibrarySubscriptionEntity subscription = all.get(0);

        assertEquals(dataFirst.getUsername(), subscription.getUsername());
        assertEquals(dataFirst.getUserFullName(), subscription.getFullName());
        assertEquals(dataFirst.getUserActive(), subscription.getActive());

        Set<BookInSubscriptionEntity> books = subscription.getBooks();
        assertEquals(1, books.size());

        BookEntity book = books.iterator().next().getBook();

        assertEquals(dataFirst.getBookAuthor(), book.getAuthor());
        assertEquals(dataFirst.getBookName(), book.getName());
    }

    @Test
    void checkResponse() {
        Data dataFirst = getDataFirst();
        List<Data> data = List.of(getDataFirst());
        subscriptionService.saveSubscriptions(data);
        List<LibrarySubscriptionEntity> all = repository.findAll();

        assertEquals(1, all.size());


        LibrarySubscriptionEntity subscription = all.get(0);
        Subscription response = subscriptionMapper.map(subscription);

        assertEquals(dataFirst.getUsername(), response.getUsername());
        assertEquals(dataFirst.getUserFullName(), response.getFullName());
        assertEquals(dataFirst.getUserActive(), response.getActive());

        assertEquals(1, response.getBooks().size());

        Book book = response.getBooks().iterator().next();

        assertEquals(dataFirst.getBookName(), book.getBookName());
        assertEquals(dataFirst.getBookAuthor(), book.getBookAuthor());
    }

    @Test
    void checkFindUser() {
        Data dataFirst = getDataFirst();
        List<Data> data = List.of(getDataFirst());
        subscriptionService.saveSubscriptions(data);

        Subscription subscription = libraryService.getSubscription(dataFirst.getUserFullName());

        assertEquals(dataFirst.getUsername(), subscription.getUsername());
        assertEquals(dataFirst.getUserFullName(), subscription.getFullName());
        assertEquals(dataFirst.getUserActive(), subscription.getActive());

        assertEquals(1, subscription.getBooks().size());

        Book book = subscription.getBooks().iterator().next();

        assertEquals(dataFirst.getBookName(), book.getBookName());
        assertEquals(dataFirst.getBookAuthor(), book.getBookAuthor());
    }

}
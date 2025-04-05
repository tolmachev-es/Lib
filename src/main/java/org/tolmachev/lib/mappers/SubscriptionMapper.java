package org.tolmachev.lib.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.tolmachev.lib.entity.BookInSubscriptionEntity;
import org.tolmachev.lib.entity.LibrarySubscriptionEntity;
import org.tolmachev.lib.model.Book;
import org.tolmachev.lib.model.Subscription;

import java.util.List;
import java.util.Set;

@Mapper(uses = {BookMapper.class})
public interface SubscriptionMapper {
    BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "books", qualifiedByName = "bookMappers", target = "books")
    Subscription map(LibrarySubscriptionEntity subscription);

    @Named("bookMappers")
    default List<Book> map(Set<BookInSubscriptionEntity> subscriptions) {
        return subscriptions.stream()
                            .map(BookInSubscriptionEntity::getBook)
                            .map(bookMapper::map)
                            .toList();
    }
}

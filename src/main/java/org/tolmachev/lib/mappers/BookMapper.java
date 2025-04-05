package org.tolmachev.lib.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tolmachev.lib.entity.BookEntity;
import org.tolmachev.lib.entity.BookInSubscriptionEntity;
import org.tolmachev.lib.model.Book;

@Mapper
public interface BookMapper {


    @Mapping(expression = "java(book.getBook().getAuthor())", target = "bookAuthor")
    @Mapping(expression = "java(book.getBook().getName())", target = "bookName")
    Book map(BookInSubscriptionEntity book);

    @Mapping(source = "author", target = "bookAuthor")
    @Mapping(source = "name", target = "bookName")
    Book map(BookEntity book);
}
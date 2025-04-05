package org.tolmachev.lib.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Subscription {
    private String username;
    private String fullName;
    private Boolean active;
    private List<Book> books;
}
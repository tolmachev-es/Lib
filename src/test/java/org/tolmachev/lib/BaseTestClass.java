package org.tolmachev.lib;

import org.tolmachev.lib.model.Data;

public class BaseTestClass {
    public static Data getDataFirst() {
        Data data = new Data();
        data.setUsername("testuser1");
        data.setUserFullName("test user1");
        data.setUserActive(true);
        data.setBookAuthor("test author1");
        data.setBookName("test book1");
        return data;
    }

    public static Data getDataSecond() {
        Data data = new Data();
        data.setUsername("testuser2");
        data.setUserFullName("test user2");
        data.setUserActive(true);
        data.setBookAuthor("test author2");
        data.setBookName("test book2");
        return data;
    }

    public static Data getDataThird() {
        Data data = new Data();
        data.setUsername("testuser3");
        data.setUserFullName("test user3");
        data.setUserActive(true);
        data.setBookAuthor("test author3");
        data.setBookName("test book3");
        return data;
    }

}

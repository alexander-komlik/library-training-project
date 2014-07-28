package com.pvt.app.util;

import com.pvt.app.entity.Author;
import com.pvt.app.entity.Book;
import com.pvt.app.entity.Text;
import com.pvt.app.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BookForm {

    private long id;
    private String title;
    private Author author1;
    private Author author2;
    private Author author3;
    private Author author4;
    private Author author5;
    private Text text;
    private User user;

    public BookForm() {}

    public BookForm(Book book) {
        id = book.getId();
        title = book.getTitle();
        text = book.getText();
        user = book.getUser();
        for(Author a: book.getAuthors()) {
            if(author1 == null) author1 =a;
            else if(author2 == null) author2=a;
            else if(author3 == null) author3=a;
            else if(author4 == null) author4=a;
            else if(author5 == null) author5=a;
            else {
                //todo
            }
        }
    }

    public Book getBook() {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setText(text);
        book.setUser(user);
        Set<Author> aSet = new HashSet<Author>();
        if(author1!=null && author1.getId()!=0) aSet.add(author1);
        if(author2!=null && author2.getId()!=0) aSet.add(author2);
        if(author3!=null && author3.getId()!=0) aSet.add(author3);
        if(author4!=null && author4.getId()!=0) aSet.add(author4);
        if(author5!=null && author5.getId()!=0) aSet.add(author5);
        book.setAuthors(new ArrayList<Author>(aSet));
        return book;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor1() {
        return author1;
    }

    public void setAuthor1(Author author1) {
        this.author1 = author1;
    }

    public Author getAuthor2() {
        return author2;
    }

    public void setAuthor2(Author author2) {
        this.author2 = author2;
    }

    public Author getAuthor3() {
        return author3;
    }

    public void setAuthor3(Author author3) {
        this.author3 = author3;
    }

    public Author getAuthor4() {
        return author4;
    }

    public void setAuthor4(Author author4) {
        this.author4 = author4;
    }

    public Author getAuthor5() {
        return author5;
    }

    public void setAuthor5(Author author5) {
        this.author5 = author5;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BookForm{" +
                "id=" + id +
                '}';
    }
}

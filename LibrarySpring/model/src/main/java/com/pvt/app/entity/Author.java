package com.pvt.app.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "t_author")
@NamedQueries({
        @NamedQuery(name = "Author.getById", query = "SELECT a FROM Author a JOIN FETCH a.text WHERE a.id=:id"),
})
public class Author implements Serializable, MyEntity {

    private static final long serialVersionUID = 101L;

    @Id
    @GeneratedValue
    private long id;
    @Column
    private String name;
    @Column
    private String name4table;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "text_id")
    private Text text;
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book> books;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Author() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName4table() {
        return name4table;
    }

    public void setName4table(String name4table) {
        this.name4table = name4table;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (id != author.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("Author{id=").append(id).append("\nname=").append(name)
                .append("\nname4table").append(name4table).append("\ntext=").append(text)
                .append("\nuser=").append(user).append("\nbooks:");
        for(Book b:books){
            sb.append("\nid=").append(b.getId()).append(" title=").append(b.getTitle());
        }
        sb.append('}');
        return sb.toString();
    }
}

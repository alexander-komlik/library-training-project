package com.pvt.app.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Entity
@Table(name = "t_book")
@NamedQueries({
        @NamedQuery(name = "Book.getById", query = "SELECT b FROM Book b JOIN FETCH b.text WHERE b.id=:id")
})
public class Book implements Serializable, MyEntity {

    private static final long serialVersionUID = 102L;

    @Id
    @GeneratedValue
    private long id;
    @Column
    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastUpdate;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Text text;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Book() {
        lastUpdate = new GregorianCalendar();
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

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != book.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Book{id=").append(id).append("\ntitle=").append(title)
                .append("\nlastUpdate=").append(lastUpdate.getTime()).append("\ntext=").append(text)
                .append("\nuser=").append(user).append("\nauthors:");
        for(Author a:authors) {
            sb.append("\nid=").append(a.getId()).append(" name=").append(a.getName4table());
        }
        sb.append('}');
        return sb.toString();
    }
}

package com.pvt.app.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_text")
public class Text implements Serializable, MyEntity {

    private static final long serialVersionUID = 103L;

    @Id
    @GeneratedValue
    private long id;

    @Lob
    private String text;

    public Text() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Text text = (Text) o;

        if (id != text.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Text{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}

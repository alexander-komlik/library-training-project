package com.pvt.app.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_accessData")
//@NamedQueries({
//        @NamedQuery(name = "AccessData.getUserData",
//                query = "SELECT a.pass, u.name FROM AccessData a JOIN FETCH User u WHERE u.email=:email")
//})
public class AccessData implements Serializable, MyEntity {

    private static final long serialVersionUID = 104L;

    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private String pass;
    @Transient
    private String confirmPass;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public AccessData() {}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

        AccessData that = (AccessData) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "AccessData{" +
                "id=" + id +
                ", pass='" + pass + '\'' +
                ", user=" + user +
                '}';
    }
}

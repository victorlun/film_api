package com.film_api.model;
import jakarta.persistence.*;

@Entity
@Table(name = "character")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "alias")
    private String alias;

    @Column(name = "gender")
    private String gender;

    @Column(name = "url_photo")
    private String photo;

    public Character() {

    }

    public Character(String name, String alias, String gender, String url) {
        this.fullName = name;
        this.alias = alias;
        this.gender = gender;
        this.photo = url;
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String newName) {
        this.fullName = newName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String newAlias) {
        this.alias = newAlias;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String newGender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String newUrl) {
        this.photo = newUrl;
    }

    @Override
    public String toString() {
        return "Character [id=" + id + ", name=" + fullName + ", alias=" + alias + ", gender=" + gender + ", photo url=" + photo + "]";
    }
}

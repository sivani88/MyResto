package model;

import androidx.annotation.Nullable;

import java.util.List;

public class User {
    private String uid;
    private String userName;
    private String email;
    @Nullable
    private String urlPicture;
    @Nullable
    private Restaurant userRestaurant;
    @Nullable
    private List<String> favoritesRestaurants;

    public User(String uid, String userName, String urlPicture) {
    }

    public User(String uid, String userName, String email, @Nullable String urlPicture, @Nullable Restaurant userRestaurant, @Nullable List<String> favoritesRestaurants) {
        this.uid = uid;
        this.userName = this.userName;
        this.email = email;
        this.urlPicture = this.urlPicture;
        this.userRestaurant = userRestaurant;
        this.favoritesRestaurants = favoritesRestaurants;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlImage(@Nullable String urlImage) {
        urlPicture = urlPicture;
    }

    @Nullable
    public Restaurant getUserRestaurant() {
        return userRestaurant;
    }

    public void setUserRestaurant(@Nullable Restaurant userRestaurant) {
        this.userRestaurant = userRestaurant;
    }

    @Nullable
    public List<String> getFavoritesRestaurants() {
        return favoritesRestaurants;
    }

    public void setFavoritesRestaurants(@Nullable List<String> favoritesRestaurants) {
        this.favoritesRestaurants = favoritesRestaurants;
    }


}

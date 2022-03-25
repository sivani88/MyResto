package model;

public class Coworker{

    String avatar;
    String nameOfCoworkers;
    String nameOfRestaurant;
    String placeId;

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getNameOfCoworkers() {
        return nameOfCoworkers;
    }

    public void setNameOfCoworkers(String nameOfCoworkers) {
        this.nameOfCoworkers = nameOfCoworkers;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNameOfRestaurant() {
        return nameOfRestaurant;
    }

    public void setNameOfRestaurant(String nameOfRestaurant) {
        this.nameOfRestaurant = nameOfRestaurant;
    }

    public Coworker(String avatar, String nameOfWorkMates, String nameOfRestaurant, String placeId) {
        this.avatar = avatar;
        this.nameOfCoworkers = getNameOfCoworkers();
        this.nameOfRestaurant = nameOfRestaurant;
        this.placeId = placeId;
    }

    public Coworker(){

    }



}



package com.example.boardgames.Model;

public class Hotness {
    private String nameGame, image, rank, year;
    private Integer id;

    public void setNameGame(String nameGame) {
        this.nameGame = nameGame;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameGame() {
        return nameGame;
    }

    public String getImage() {
        return image;
    }

    public String getRank() {
        return rank;
    }

    public String getYear() {
        return year;
    }

    public Integer getId() {
        return id;
    }
}

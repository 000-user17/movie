package cs.nju.edu;

import java.util.Date;

public class Movie {
    private String name;
    private String actor;
    private double score;
    private double time;
    private double price;
    private int number;
    private Date StartTime;

    public int scoreNum;

    public int getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(int scoreNum) {
        this.scoreNum = scoreNum;
    }

    public Movie(){

    }
    public Movie(String name, String actor, double time, double price, int number, Date startTime) {
        this.name = name;
        this.actor = actor;
        this.score = score;
        this.time = time;
        this.price = price;
        this.number = number;
        StartTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }
}

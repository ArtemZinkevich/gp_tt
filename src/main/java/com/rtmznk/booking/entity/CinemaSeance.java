package com.rtmznk.booking.entity;

import java.util.List;

/**
 * Created by RTM on 03.04.2017.
 */
public class CinemaSeance {
    private long id;
    private String movieName;
    private String date;
    private List<Integer> seats;

    public CinemaSeance() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CinemaSeanse{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", date='" + date + '\'' +
                ", seats=" + seats +
                '}';
    }

    public String getFullName() {
        return movieName + " : " + date;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }
}

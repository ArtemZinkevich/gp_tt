package com.rtmznk.booking.entity;

import com.rtmznk.booking.dao.BookingsDAO;
import com.rtmznk.booking.idgenerator.BookingIdGen;

import java.util.List;

/**
 * Created by RTM on 03.04.2017.
 */
public class Booking {
    private long id;
    private long seanseId;
    private List<Integer> seats;

    public Booking() {
        long genId=BookingIdGen.getId();
        long maxId=new BookingsDAO().reciveMaxBookingId();
        if(maxId>genId){
            BookingIdGen.updateBookingIdGen(maxId);
            genId=BookingIdGen.getId();
        }
        id = genId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSeanseId() {
        return seanseId;
    }

    public void setSeanseId(long seanseId) {
        this.seanseId = seanseId;
    }

    public List<Integer> getSeats() {
        return seats;
    }

    public void setSeats(List<Integer> seats) {
        this.seats = seats;
    }
}

package com.rtmznk.booking.idgenerator;

import com.rtmznk.booking.dao.BookingsDAO;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by RTM on 05.04.2017.
 */
public class BookingIdGen {
    private static AtomicLong id = new AtomicLong(0);
    public static long getId(){
        return id.getAndIncrement();
    }

   public static void updateBookingIdGen(long id){
       BookingIdGen.id.set(id);
    }
}

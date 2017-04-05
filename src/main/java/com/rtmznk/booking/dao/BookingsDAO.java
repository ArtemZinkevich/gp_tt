package com.rtmznk.booking.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtmznk.booking.entity.Booking;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by RTM on 04.04.2017.
 */
public class BookingsDAO {
    private static final File BOOKINGS_FILE = new BookingsDAO().recieveBookingFile();
    private static List<Booking> bookingsCache = new BookingsDAO().recieveBookings();
    private Lock fileLock = new ReentrantLock();
    private Lock cacheLock = new ReentrantLock();

    private List<Booking> recieveBookings() {
        List<Booking> result;
        if (bookingsCache == null || bookingsCache.isEmpty()) {
            result = new ArrayList<>();
            ObjectMapper mapper = new ObjectMapper();
            try {
                fileLock.lock();
                Files.lines(BOOKINGS_FILE.toPath(), StandardCharsets.UTF_8).forEach((s) -> {
                    Booking booking = null;
                    try {
                        booking = (Booking) mapper.readValue(s, Booking.class);
                        result.add(booking);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fileLock.unlock();
            }

            cacheLock.lock();
            bookingsCache = result;
            cacheLock.unlock();
        } else {
            result = bookingsCache;
        }

        return result;
    }

    public Booking reciveBooking(long id) {
        cacheLock.lock();
        Booking result=null;
        Optional<Booking>bookOpt = bookingsCache.stream().filter((b) -> {
            return b.getId() == id;
        }).findFirst();
        cacheLock.unlock();
        if(bookOpt.isPresent()){
            result=bookOpt.get();
        }
        return result;
    }

    public void deleteBooking(long id) {
        Booking removed = reciveBooking(id);
        List<Integer> booked = removed.getSeats();
        cacheLock.lock();
        bookingsCache.remove(removed);
        cacheLock.unlock();
        new SeancesDAO().unbookSeansePlaces(removed.getSeanseId(), booked);
        updateBookingFile();
    }

    public long reciveMaxBookingId() {
        long result = 0;
        cacheLock.lock();
        for (Booking b : bookingsCache) {
            if (b.getId() > result) {
                result = b.getId();
            }
        }
        cacheLock.unlock();
        return result;
    }

    public void addBooking(Booking booking) {
        cacheLock.lock();
        bookingsCache.add(booking);
        cacheLock.unlock();
        ObjectMapper mapper = new ObjectMapper();
        try {
            fileLock.lock();
            FileWriter fileWriter = new FileWriter(BOOKINGS_FILE, true);
            mapper.writeValue(fileWriter, booking);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileLock.unlock();
        }
    }

    private void updateBookingFile() {
        ObjectMapper mapper = new ObjectMapper();
        cacheLock.lock();
        bookingsCache.forEach((booking -> {
            try {
                fileLock.lock();
                mapper.writeValue(recieveBookingFile(), booking);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fileLock.unlock();
            }
        }));
        cacheLock.unlock();
    }

    private File recieveBookingFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL fileUrl = classLoader.getResource("files/bookings.txt");
        String filePath = fileUrl.getPath();
        filePath = filePath.replaceAll("%20", " ");
        File file = new File(filePath);
        return file;
    }


}

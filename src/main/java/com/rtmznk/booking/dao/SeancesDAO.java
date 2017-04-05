package com.rtmznk.booking.dao;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rtmznk.booking.entity.Booking;
import com.rtmznk.booking.entity.CinemaSeance;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by RTM on 03.04.2017.
 */
public class SeancesDAO {
    private static final String SEANSES_FILE_PATH = "files/seanses.txt";
    private static CopyOnWriteArrayList<CinemaSeance> seansesCache = new SeancesDAO().recieveSeances();
    private Lock fileLock = new ReentrantLock();
    private Lock editingSeancePlacesLock = new ReentrantLock();

    public CopyOnWriteArrayList<CinemaSeance> recieveSeances() {
        CopyOnWriteArrayList<CinemaSeance> result;
        if (seansesCache == null || seansesCache.isEmpty()) {
            result = new CopyOnWriteArrayList<>();
            ClassLoader classLoader = getClass().getClassLoader();
            URL fileUrl = classLoader.getResource(SEANSES_FILE_PATH);
            String filePath = fileUrl.getPath();
            filePath = filePath.replaceAll("%20", " ");
            File file = new File(filePath);
            ObjectMapper mapper = new ObjectMapper();
            try {
                fileLock.lock();
                Files.lines(Paths.get(file.getCanonicalPath()), StandardCharsets.UTF_8).forEach((s) -> {
                    CinemaSeance seanse = null;
                    try {
                        seanse = (CinemaSeance) mapper.readValue(s, CinemaSeance.class);
                        result.add(seanse);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fileLock.unlock();
            }
            seansesCache = result;
        } else {
            result = seansesCache;
        }
        return result;
    }

    public void bookSeancePlaces(long id, List<Integer> seats) {
        editingSeancePlacesLock.lock();
        try {
            CinemaSeance searchedSeance = seansesCache.stream().filter(seance -> {
                return seance.getId() == id;
            }).findFirst().get();
            for (int i : seats) {
                searchedSeance.getSeats().set(i, 1);
            }
        } finally {
            editingSeancePlacesLock.unlock();
        }
        updateSeanceFile();
    }

    public CinemaSeance reciveSeance(long id) {
        CinemaSeance result = seansesCache.stream().filter((s) -> {
            return s.getId() == id;
        }).findFirst().get();
        return result;
    }

    private void updateSeanceFile() {
        ObjectMapper mapper = new ObjectMapper();
        recieveSeances().forEach((seance -> {
            try {
                fileLock.lock();
                mapper.writeValue(new File(getFileName()), seance);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fileLock.unlock();
            }
        }));
    }

    public void unbookSeansePlaces(long id, List<Integer> seats) {
        editingSeancePlacesLock.lock();
        try {
            CinemaSeance searchedSeance = seansesCache.stream().filter(seance -> {
                return seance.getId() == id;
            }).findFirst().get();
            for (int i : seats) {
                searchedSeance.getSeats().set(i, 0);
            }
        } finally {
            editingSeancePlacesLock.unlock();
        }
        updateSeanceFile();
    }

    private String getFileName() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("files/seanses.txt").getFile());
        return file.getPath().replaceAll("%20", " ");
    }


}

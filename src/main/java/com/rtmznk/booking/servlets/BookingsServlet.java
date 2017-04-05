package com.rtmznk.booking.servlets;

import com.rtmznk.booking.dao.BookingsDAO;
import com.rtmznk.booking.dao.SeancesDAO;
import com.rtmznk.booking.entity.Booking;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;

/**
 * Created by RTM on 04.04.2017.
 */
@WebServlet("/bookings")
public class BookingsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String referer = request.getHeader("referer");
        int seanseId = Integer.parseInt(referer.replace("http://localhost:8080/seances/", ""));
        Set<String> keys=request.getParameterMap().keySet();
        ArrayList<Integer> bookedSeats = new ArrayList<>();
        for(String key:keys) {
            bookedSeats.add(Integer.parseInt(key));
        }
        Booking newBooking = new Booking();
        newBooking.setSeats(bookedSeats);
        newBooking.setSeanseId(seanseId);
        BookingsDAO bookingsDAO = new BookingsDAO();
        bookingsDAO.addBooking(newBooking);
        SeancesDAO seancesDAO = new SeancesDAO();
        seancesDAO.bookSeancePlaces(seanseId, bookedSeats);
        response.sendRedirect("booking/" + newBooking.getId());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/bookings.jsp");
        dispatcher.forward(request, response);
    }
}


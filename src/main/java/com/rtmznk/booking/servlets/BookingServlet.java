package com.rtmznk.booking.servlets;

import com.rtmznk.booking.dao.BookingsDAO;
import com.rtmznk.booking.dao.SeancesDAO;
import com.rtmznk.booking.entity.Booking;
import com.rtmznk.booking.htmlmaker.HtmlPageMaker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

/**
 * Created by RTM on 05.04.2017.
 */
@WebServlet("/booking/*")
public class BookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String referer = request.getHeader("referer");
        int bookingId = Integer.parseInt(referer.replace("http://localhost:8080/booking/", ""));
        BookingsDAO bookingsDAO = new BookingsDAO();
        bookingsDAO.deleteBooking(bookingId);
        response.sendRedirect(""+bookingId);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String id = request.getRequestURI().replace("/booking/", "");
        Scanner sc = new Scanner(id);
        if (sc.hasNextInt()) {
            int bookingId = sc.nextInt();
            BookingsDAO bookingsDAO = new BookingsDAO();
            Booking booking = bookingsDAO.reciveBooking(bookingId);
            response.getWriter().write(HtmlPageMaker.getBookingPage(booking));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

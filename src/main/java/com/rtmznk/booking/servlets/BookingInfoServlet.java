package com.rtmznk.booking.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * Created by RTM on 05.04.2017.
 */
@WebServlet("/booking")
public class BookingInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map map = request.getParameterMap();
        String sId = ((String[]) map.get("id"))[0];
        int id = Integer.parseInt(sId);
        response.sendRedirect("booking/" + id);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("bookings");
    }
}

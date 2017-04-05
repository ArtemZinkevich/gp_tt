package com.rtmznk.booking.servlets;

import com.rtmznk.booking.dao.BookingsDAO;
import com.rtmznk.booking.dao.SeancesDAO;
import com.rtmznk.booking.htmlmaker.HtmlPageMaker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by RTM on 03.04.2017.
 */
@WebServlet("/seances")
public class SeancesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        SeancesDAO seancesDAO = new SeancesDAO();
        out.println(HtmlPageMaker.getSeansesPage(seancesDAO.recieveSeances()));
    }
}

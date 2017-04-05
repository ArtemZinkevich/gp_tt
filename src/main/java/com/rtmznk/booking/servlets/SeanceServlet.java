package com.rtmznk.booking.servlets;

import com.rtmznk.booking.dao.SeancesDAO;
import com.rtmznk.booking.entity.CinemaSeance;
import com.rtmznk.booking.htmlmaker.HtmlPageMaker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by RTM on 04.04.2017.
 */
@WebServlet("/seances/*")
public class SeanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getRequestURI().replace("/seances/", "");
        response.setContentType("text/html");
        int seanceId;
        Scanner sc = new Scanner(id);
        if (sc.hasNextInt()) {
            seanceId = sc.nextInt();
            PrintWriter out = response.getWriter();
            SeancesDAO seancesDAO = new SeancesDAO();
            CinemaSeance s = null;
            for (CinemaSeance ss : seancesDAO.recieveSeances()) {
                if (ss.getId() == seanceId) {
                    s = ss;
                }
            }
            if (s == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                out.println(HtmlPageMaker.getSeancePage(s));
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

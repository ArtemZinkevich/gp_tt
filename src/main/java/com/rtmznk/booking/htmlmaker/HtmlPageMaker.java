package com.rtmznk.booking.htmlmaker;

import com.rtmznk.booking.dao.SeancesDAO;
import com.rtmznk.booking.entity.Booking;
import com.rtmznk.booking.entity.CinemaSeance;

import java.util.List;

/**
 * Created by RTM on 04.04.2017.
 */
public class HtmlPageMaker {
    private static final String PAGE_START = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "    <head>\n" +
            "        <meta charset=\"utf-8\">\n" +
            "        <title>My page</title>\n" +
            "    </head>\n" +
            "    <body>";
    private static final String PAGE_END = " </body>\n" +
            "</html>";

    public static String getSeansesPage(List<CinemaSeance> seanseList) {
        StringBuilder sb = new StringBuilder();
        sb.append(PAGE_START);
        sb.append("<h1>Seances</h1>");
        if (seanseList.isEmpty()) {
            sb.append("No Seances Available");
        }
        sb.append("<ul>\n");
        for (CinemaSeance s : seanseList) {
            sb.append("<li><a href=\"");
            sb.append("seances/" + s.getId());
            sb.append("\">");
            sb.append(s.getMovieName());
            sb.append(" ");
            sb.append(s.getDate());
            sb.append("</a></li>\n");
        }
        sb.append("</ul><br><a href=\"\\\">back to main</a>");
        sb.append(PAGE_END);

        return sb.toString();
    }

    public static String getSeancePage(CinemaSeance seans) {
        StringBuilder sb = new StringBuilder();
        sb.append(PAGE_START);
        sb.append("<h1>Seance :#" + seans.getId() + " | " + seans.getMovieName() + " | " + seans.getDate() + "</h1>\n<br><br>");
        sb.append("<p>Seats : </p>");
        sb.append("<form action=\"\\bookings\" method=\"post\">");
        List<Integer> seats = seans.getSeats();
        for (int i = 0; i < 5; i++) {
            sb.append("row " + (i + 1) + ":");
            for (int j = 0; j < 10; j++) {
                if (seats.get(i * 10 + j) == 0) {
                    sb.append("<input type=\"checkbox\" name =\"" + (i * 10 + j) + "\" >\n");
                } else {
                    sb.append("&ensp;X&ensp;");
                }
            }
            sb.append("<br><br>");
        }
        sb.append("<input type=\"submit\" value=\"Book selected\">\n" +
                "</form><br><a href=\"\\\">back to main</a><br><a href=\"\\seances\">all seanses</a>");
        sb.append(PAGE_END);

        return sb.toString();
    }

    public static String getBookingPage(Booking booking) {
        StringBuilder sb = new StringBuilder();
        sb.append(PAGE_START);
        if (booking != null) {
            sb.append("<h1>Info about booking #" + booking.getId() + " : </h1>");
            CinemaSeance seance = new SeancesDAO().reciveSeance(booking.getSeanseId());
            sb.append("<ul><li>Seance : Number#");
            sb.append(seance.getId());
            sb.append("| Movie name : ");
            sb.append(seance.getMovieName());
            sb.append("| Date : " + seance.getDate());
            sb.append("</li><br>");
            sb.append("<li>Booked places: <ul>");
            for (int i : booking.getSeats()) {
                sb.append("<li>row : ").append(i / 10).append(",place #").append(i % 10).append("</li><br>");
            }
            sb.append("</ul><br></li></ul><br>");
            sb.append("<form action=\"\\booking\\\" method=\"post\">");
            sb.append("<input type=\"submit\" value=\"Delete this booking\">\n" +
                    "</form><br><a href=\"\\\">back to main</a>"
            );
        } else {
            sb.append("<h2>There is no such booking anymore!</h2><br><a href=\"\\bookings\">try to find another booking here</a>");
        }

        sb.append(PAGE_END);
        return sb.toString();
    }

}

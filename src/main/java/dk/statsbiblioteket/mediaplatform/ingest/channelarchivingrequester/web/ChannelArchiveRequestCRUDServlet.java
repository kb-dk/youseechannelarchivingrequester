package dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web;

import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;
import dk.statsbiblioteket.mediaplatform.ingest.model.WeekdayCoverage;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchiveRequestDAO;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchiveRequestDAOIF;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 *
 */
public class ChannelArchiveRequestCRUDServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChannelArchiveRequestDAO dao = new ChannelArchiveRequestDAO();
        ChannelArchiveRequest caRequest = new ChannelArchiveRequest();
        Date date = new Date();
        caRequest.setFromDate(date);
        caRequest.setFromTime(date);
        caRequest.setToDate(date);
        caRequest.setToTime(date);
        caRequest.setsBChannelId("tv2d");
        caRequest.setWeekdayCoverage(WeekdayCoverage.SATURDAY_AND_SUNDAY);
        dao.create(caRequest);
        req.setAttribute("page_attr", "archiving_requests.jsp");
        req.getSession().getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}

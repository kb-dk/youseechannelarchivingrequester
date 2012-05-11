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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class ChannelArchiveRequestCRUDServlet extends HttpServlet {

    public static final String FROM_DATE = "fromDate";
    public static final String TO_DATE = "toDate";
    public static final String FROM_TIME_HOURS = "fromTimeHours";
    public static final String TO_TIME_HOURS = "toTimeHours";
    public static final String FROM_TIME_MINUTES = "fromTimeMinutes";
    public static final String TO_TIME_MINUTES = "toTimeMinutes";
    public static final String CHANNEL = "channel";
    public static final String COVERAGE = "coverage";
    public static final String SUBMIT_ACTION = "action";
    public static final String Id = "ID";

    public static final String CREATE = "create";
    public static final String UPDATE = "update";

    public static final SimpleDateFormat JAVA_DATE_FORMAT =  new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ChannelArchiveRequestDAO dao = new ChannelArchiveRequestDAO();

        String action = req.getParameter(SUBMIT_ACTION);
        //For a delete action, all of these may be null. Only Id is needed
        String channel = req.getParameter(CHANNEL);
        String coverageS = req.getParameter(COVERAGE);
        String fromTimeHours = req.getParameter(FROM_TIME_HOURS);
        String fromTimeMinutes = req.getParameter(FROM_TIME_MINUTES);
        String toTimeHours = req.getParameter(TO_TIME_HOURS);
        String toTimeMinutes = req.getParameter(TO_TIME_MINUTES);
        String fromDateS = req.getParameter(FROM_DATE);
        if (fromDateS == null || fromDateS.equals("")) {
            fromDateS = "2012-05-01";
        }
        String toDateS = req.getParameter(TO_DATE);
        if (toDateS == null || toDateS.equals("")) {
            toDateS = "3000-01-01";
        }
        //For a create action, the id is null
        String idS = req.getParameter(Id);

        if (action.equals(CREATE) || action.equals(UPDATE)) {
            ChannelArchiveRequest caRequest = new ChannelArchiveRequest();
            caRequest.setsBChannelId(channel);
            try {
                Date fromDate = JAVA_DATE_FORMAT.parse(fromDateS);
                fromDate.setHours(0);
                fromDate.setMinutes(0);
                fromDate.setSeconds(0);
                Date toDate = JAVA_DATE_FORMAT.parse(toDateS);
                toDate.setHours(0);
                toDate.setMinutes(0);
                toDate.setSeconds(0);
                caRequest.setFromDate(fromDate);
                caRequest.setToDate(toDate);
            } catch (ParseException e) {
                throw new RuntimeException("Could not parse date", e);
            }
            Date fromTime = new Date(0);
            fromTime.setHours(Integer.parseInt(fromTimeHours));
            fromTime.setMinutes(Integer.parseInt(fromTimeMinutes));
            Date toTime = new Date(0,0,1, Integer.parseInt(toTimeHours), Integer.parseInt(toTimeMinutes), 0);
            caRequest.setToTime(toTime);
            caRequest.setFromTime(fromTime);
            caRequest.setWeekdayCoverage(WeekdayCoverage.valueOf(coverageS));
            if (action.equals(CREATE)) {
                dao.create(caRequest);
            }
        }



        req.setAttribute("page_attr", "archiving_requests.jsp");
        req.getSession().getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }


}

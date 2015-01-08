package dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web;

import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web.CAR;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;
import dk.statsbiblioteket.mediaplatform.ingest.model.WeekdayCoverage;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchiveRequestDAO;
import dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchiveRequestDAOIF;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.ChannelArchiveRequestService;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.ChannelArchiveRequestServiceIF;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.ServiceException;
/*
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
*/import java.util.List;


@Path("/")
public class ChannelArchiveRequestRESTServlet {
    private static ChannelArchiveRequestServiceIF service = null;
    private SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm");
    private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final int CHANNEL = 0;
    private static final int START_TIME = 1;
    private static final int END_TIME = 2;
    private static final int FROM_DATE = 3;
    private static final int TO_DATE = 4;
    private static final int COVERAGE = 5;

    public static final List<String> COLUMN_LIST =
            Arrays.asList("Channel", "Start Time", "End Time", "From", "To", "Coverage");

    public ChannelArchiveRequestRESTServlet() {
        service = new ChannelArchiveRequestService();
    }

    @GET
    @Path("/getCARList")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ChannelArchiveRequest> getCARList() throws ServiceException {
        return service.getAllRequests();
    }
    /*
    public String getCARList() throws ServiceException {
        String result = "";
        return "[aaData: " + service.getAllRequests() +"]";
    }*/

    @POST
    @Path("/deleteCAR")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    ///Param ID is a unique number identifying the unique CAR object, which is a number
    public String deleteCAR(@FormParam("id") String id) throws ServiceException {

        try {
            //Convert the id (as a string) to an integer id
            Long realId = Long.parseLong(id.substring(4));
            //Get a list of CAR objects that has the unique id.
            List<ChannelArchiveRequest> carList = service.getRequestByID(realId);
            //Get the first element of the list. We can safely assume that there is only object, since we know it is unique
            ChannelArchiveRequest caRequest = carList.get(0);
            //Delete the CAR object
            service.delete(caRequest);
        } catch (ServiceException ex) {
            return "Error";
        }

        return "ok";
    }

    @POST
    @Path("/updateCAR")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateCAR(@FormParam("id") String id,
                              @FormParam("value") String value,
                              @FormParam("columnName") String columnName,
                              @FormParam("columnPosition") String columnPosition,
                              @FormParam("columnId") String columnId,
                              @FormParam("rowId") String rowId) throws ServiceException {
        //Indicates whether the result is ok or not
        boolean ok = true;
        String errorStr = "";
        try {
            //Convert the id (as a string) to an integer id
            Long realId = Long.parseLong(id.substring(4));
            //Get a list of CAR objects that has the unique id.
            List<ChannelArchiveRequest> carList = service.getRequestByID(realId);
            //Get the requested CAR object
            ChannelArchiveRequest caRequest = carList.get(0);
            //Find the column by the column name
            int indexOfColumn = (COLUMN_LIST.indexOf(columnName));
            //Update the CAR object
            if (indexOfColumn == CHANNEL)
                caRequest.setsBChannelId(value);
            else if (indexOfColumn == COVERAGE) {
                WeekdayCoverage i = WeekdayCoverage.valueOf(value);
                caRequest.setWeekdayCoverage(WeekdayCoverage.valueOf(value));
            } else if (indexOfColumn == START_TIME) {
                Date newFromTime = new Date();
                try {
                    newFromTime = formatTime.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (newFromTime.before(caRequest.getToTime()) || newFromTime.equals(caRequest.getToTime()))
                    caRequest.setFromTime(newFromTime);
                else {
                    ok = false;
                    errorStr = "Start time cannot be after end time";
                }
            } else if (indexOfColumn == END_TIME) {
                Date newToTime = new Date(0);
                try {
                    newToTime = formatTime.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (newToTime.after(caRequest.getFromTime()) || newToTime.equals(caRequest.getFromTime()))
                    caRequest.setToTime(newToTime);
                else {
                    ok = false;
                    errorStr = "End time cannot be before start time";
                }
            } else if (indexOfColumn == FROM_DATE) {
                Date newFromDate = new Date(0);
                try {
                    newFromDate = formatTime.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (newFromDate.before(caRequest.getToDate()) || newFromDate.equals(caRequest.getToDate()))
                    caRequest.setToDate(newFromDate);
                else {
                    ok = false;
                    errorStr = "From date cannot be after to date";
                }
            } else if (indexOfColumn == TO_DATE) {
                Date newToDate = new Date(0);
                try {
                    if (formatDate.parse(value) != null)
                        newToDate = formatTime.parse(value);
                    else {
                        //If no date is given then put it at 3000-01-01
                        newToDate = formatTime.parse("3000-01-01");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (newToDate.after(caRequest.getFromDate()) || newToDate.equals(caRequest.getFromDate()))
                    caRequest.setToDate(newToDate);
                else {
                    ok = false;
                    errorStr = "To date cannot be before from date";
                }
            }
            //Sends update request to the service, that when the input is valid updates DB
            service.update(caRequest);
        } catch (ServiceException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (ok)
            return Response.ok(value).build();
        else
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorStr).build();

    }

    @POST
    @Path("/addCAR")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String addCAR(@FormParam("channel") String channel,
                         @FormParam("fromTime") String fromTime,
                         @FormParam("toTime") String toTime,
                         @FormParam("fromDate") String fromDate,
                         @FormParam("toDate") String toDate,
                         @FormParam("weekdayCoverage") String weekdayCoverage) throws ServiceException {
        try {
            //Get the requested CAR object
            ChannelArchiveRequest caRequest = new ChannelArchiveRequest();
            caRequest.setsBChannelId(channel);
            Date newFromTime = new Date();
            try {
                if (formatTime.parse(fromTime) != null)
                    newFromTime = formatTime.parse(fromTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            caRequest.setFromTime(newFromTime);
            Date newToTime = new Date(0);
            try {
                if (formatTime.parse(toTime) != null)
                    newToTime = formatTime.parse(toTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            caRequest.setToTime(newToTime);
            Date newFromDate = new Date(0);
            try {
                if (formatDate.parse(fromDate) != null)
                    newFromDate = formatDate.parse(fromDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            caRequest.setFromDate(newFromDate);
            Date newToDate = new Date(0);
            try {
                if (formatDate.parse(toDate) != null)
                    newToDate = formatDate.parse(toDate);
                else {
                    //If no date is given then put it at 3000-01-01
                    newToDate = formatDate.parse("3000-01-01");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            caRequest.setToDate(newToDate);
            caRequest.setWeekdayCoverage(WeekdayCoverage.values()[Integer.parseInt(weekdayCoverage) - 1]);
            //Insert the object in db
            service.insert(caRequest);
        } catch (ServiceException e) {
            return "Error ";
        }
        return "ok";
    }

}

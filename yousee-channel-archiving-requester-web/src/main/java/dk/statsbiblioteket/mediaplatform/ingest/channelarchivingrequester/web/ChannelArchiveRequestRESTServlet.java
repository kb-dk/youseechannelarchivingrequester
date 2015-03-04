package dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web;

import dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest;
import dk.statsbiblioteket.mediaplatform.ingest.model.WeekdayCoverage;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.ChannelArchiveRequestService;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.ChannelArchiveRequestServiceIF;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.ServiceException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Path("/channelRequests/")
public class ChannelArchiveRequestRESTServlet {
    private static ChannelArchiveRequestServiceIF service = null;
    private SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    private static final int CHANNEL = 0;
    private static final int START_TIME = 1;
    private static final int END_TIME = 2;
    private static final int FROM_DATE = 3;
    private static final int TO_DATE = 4;
    private static final int COVERAGE = 5;

    public static final List<String> COLUMN_LIST =
            Arrays.asList("Channel", "Start Time", "End Time", "From", "To", "Coverage");


    private Pattern pattern;
    private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    public ChannelArchiveRequestRESTServlet() {
        service = new ChannelArchiveRequestService();
        pattern = Pattern.compile(TIME24HOURS_PATTERN);
        formatDate.setLenient(false);
    }

    /**
     * Validate time in 24 hours format with regular expression
     *
     * @param time time address for validation
     * @return true valid time fromat, false invalid time format
     */
    public boolean validate(final String time) {
        Matcher matcher;
        matcher = pattern.matcher(time);
        return matcher.matches();

    }

    @GET
    @Path("/getCARList")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ChannelArchiveRequest> getCARList() throws ServiceException {
        return service.getAllRequests();
    }

    @POST
    @Path("/deleteCAR")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    ///Param ID is a unique number identifying the unique CAR object, which is a number
    public String deleteCAR(@FormParam("id") String id) throws ServiceException {

        try {
            if (id.length() < 4)
                return "Error";
            Long realId = Long.parseLong(id.substring(4));
            //Get the first element of the list. We can safely assume that there is only object, since we know it is unique
            ChannelArchiveRequest caRequest = service.getRequestByID(realId);
            //Delete the CAR object
            if (caRequest == null)
                return "Error";
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
            if (id.length() < 4)
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Invalid id").build();

            Long realId = Long.parseLong(id.substring(4));
            //Get the requested CAR object
            ChannelArchiveRequest caRequest = service.getRequestByID(realId);
            if (caRequest == null)
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Id not found").build();

            //Find the column by the column name
            int indexOfColumn = (COLUMN_LIST.indexOf(columnName));
            //Update the CAR object
            switch (indexOfColumn) {
                case CHANNEL:
                    caRequest.setsBChannelId(value);
                    break;
                case COVERAGE:
                    caRequest.setWeekdayCoverage(WeekdayCoverage.valueOf(value));
                    break;
                case START_TIME:
                    Date newFromTime;
                    value = "1900-01-01 " + value;
                    newFromTime = formatTime.parse(value);
                    if (newFromTime.before(caRequest.getToTime()) || newFromTime.equals(caRequest.getToTime()))
                        caRequest.setFromTime(newFromTime);
                    else {
                        ok = false;
                        errorStr = "Start time cannot be after end time";
                    }
                    break;
                case END_TIME:
                    Date newToTime;
                    if (value.equals("00:00"))
                        value = "1900-01-02 " + value;
                    else
                        value = "1900-01-01 " + value;
                    newToTime = formatTime.parse(value);
                    if (newToTime.after(caRequest.getFromTime()) || newToTime.equals(caRequest.getFromTime()))
                        caRequest.setToTime(newToTime);
                    else {
                        ok = false;
                        errorStr = "End time cannot be before start time";
                    }
                    break;
                case FROM_DATE:
                    Date newFromDate;
                    if (value == null || "".equals(value)) {
                        value = "1900-01-01";
                    }
                    newFromDate = formatDate.parse(value);
                    if (newFromDate.before(caRequest.getToDate()) || newFromDate.equals(caRequest.getToDate()))
                        caRequest.setFromDate(newFromDate);
                    else {
                        ok = false;
                        errorStr = "From date cannot be after to date";
                    }
                    break;
                case TO_DATE:
                    Date newToDate;
                    if (value == null || "".equals(value)) {
                        value = "3000-01-01";
                    }
                    newToDate = formatDate.parse(value);
                    if (newToDate.after(caRequest.getFromDate()) || newToDate.equals(caRequest.getFromDate()))
                        caRequest.setToDate(newToDate);
                    else {
                        ok = false;
                        errorStr = "To date cannot be before from date";
                    }
                    break;
                default:
                    ok = false;
                    break;
            }

            //Sends update request to the service, that when the input is valid updates DB
            service.update(caRequest);
        } catch (ServiceException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (ParseException e) {
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
            Date newFromDate;
            Date newToDate;
            //Get the requested CAR object
            ChannelArchiveRequest caRequest = new ChannelArchiveRequest();
            caRequest.setsBChannelId(channel);
            Date newFromTime;
            fromTime = "1900-01-01" + fromTime;
            if (validate(fromTime) && formatTime.parse(fromTime) != null) {
                newFromTime = formatTime.parse(fromTime);
            } else {
                newFromTime = formatTime.parse("1900-01-01 00:00");
            }
            caRequest.setFromTime(newFromTime);
            Date newToTime;
            if (toTime.equals("00:00"))
                toTime = "1900-01-02" + toTime;
            else
                toTime = "1900-01-01" + toTime;

            if (validate(toTime) && formatTime.parse(toTime) != null) {
                newToTime = formatTime.parse(toTime);
            } else {
                newToTime = formatTime.parse("1900-01-02 00:00");
            }
            caRequest.setToTime(newToTime);

            if (fromDate == null || "".equals(fromDate)) {
                fromDate = "1900-01-01";
            }
            newFromDate = formatDate.parse(fromDate);
            caRequest.setFromDate(newFromDate);

            if (toDate == null || "".equals(toDate)) {
                toDate = "3000-01-01";
            }
            newToDate = formatDate.parse(toDate);
            caRequest.setToDate(newToDate);
            caRequest.setWeekdayCoverage(WeekdayCoverage.values()[Integer.parseInt(weekdayCoverage) - 1]);
            //Insert the object in db
            service.insert(caRequest);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ServiceException e) {
            return "Error ";
        }
        return "ok";
    }

}

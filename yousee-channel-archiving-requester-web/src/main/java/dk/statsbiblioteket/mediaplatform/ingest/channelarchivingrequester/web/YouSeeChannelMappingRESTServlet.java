package dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web;

import dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.ServiceException;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService;
import dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingServiceIF;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Path("/channelMapping")
public class YouSeeChannelMappingRESTServlet {
    private static YouSeeChannelMappingServiceIF service = null;
    private static final int CHANNEL = 0;
    private static final int YOUSEEID = 1;
    private static final int DISPLAYNAME = 2;
    private static final int FROM_DATE = 3;
    private static final int TO_DATE = 4;
    private SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ROOT);


    public static final List<String> COLUMN_LIST =
            Arrays.asList("Channel", "YouSee Id", "Display Name", "From", "To");
    private ZoneId localZone = ZoneId.of("Europe/Copenhagen");

    public YouSeeChannelMappingRESTServlet() {
        service = new YouSeeChannelMappingService();
        formatDate.setLenient(false);
    }

    @GET
    @Path("/getChannelList")
    @Produces(MediaType.APPLICATION_JSON)
    public List<YouSeeChannelMapping> getChannelList() throws ServiceException {
        return service.getAllMappings();
    }

    @POST
    @Path("/deleteChannel")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    ///Param ID is a unique number identifying the unique channel mapping object, which is a number
    public String deleteChannel(@FormParam("id") String id) throws ServiceException {

        try {
            if (id.length() < 3)
                return "Error";
            Long realId = Long.parseLong(id.substring(3));
            YouSeeChannelMapping ycm = service.getMappingByID(realId);
            if (ycm == null)
                return "Error";
            //Delete the CAR object
            service.delete(ycm);
        } catch (ServiceException ex) {
            return "Error";
        }
        return "ok";
    }

    @POST
    @Path("/updateChannel")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateChannel(@FormParam("id") String id,
                                  @FormParam("value") String value,
                                  @FormParam("columnName") String columnName,
                                  @FormParam("columnPosition") String columnPosition,
                                  @FormParam("columnId") String columnId,
                                  @FormParam("rowId") String rowId) throws ServiceException {
        //Indicates whether the result is ok or not
        boolean ok = true;
        String errorStr = "";
        try {
            if (id.length() < 3)
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Invalid id").build();
            Long realId = Long.parseLong(id.substring(3));
            YouSeeChannelMapping ycm = service.getMappingByID(realId);
            if (ycm == null)
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Id not found").build();
            //Find the column by the column name
            int indexOfColumn = (COLUMN_LIST.indexOf(columnName));
            //Update the channel mapping object
            switch (indexOfColumn) {
                case CHANNEL:
                    ycm.setSbChannelId(value);
                    break;
                case YOUSEEID:
                    ycm.setYouSeeChannelId(value);
                    break;
                case DISPLAYNAME:
                    ycm.setDisplayName(value);
                    break;
                case FROM_DATE:
                    LocalDate newFromDate;
                    if (value == null || "".equals(value)) {
                        value = "1900-01-01";
                    }
                    newFromDate = LocalDate.parse(value, dateFormatter.withZone(localZone));
                    if (newFromDate.isBefore(LocalDate.ofInstant(ycm.getToDate().toInstant(), localZone)) || newFromDate.equals(ycm.getToDate()))
                        ycm.setFromDate(Date.valueOf(newFromDate));
                    else {
                        ok = false;
                        errorStr = "From date cannot be after to date";
                    }
                    break;
                case TO_DATE:
                    LocalDate newToDate;
                    if (value == null || "".equals(value)) {
                        value = "3000-01-01";
                    }
                    newToDate = LocalDate.parse(value, dateFormatter.withZone(localZone));
                    if (newToDate.isAfter(LocalDate.ofInstant(ycm.getFromDate().toInstant(), localZone)) || newToDate.equals(ycm.getFromDate()))
                        ycm.setToDate(Date.valueOf(newToDate));
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
            service.update(ycm);
        } catch (ServiceException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        if (ok)
            return Response.ok(value).build();
        else
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorStr).build();

    }

    @POST
    @Path("/addChannel")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String addChannel(@FormParam("ycm_channel") String channel,
                             @FormParam("ycm_id") String youseeId,
                             @FormParam("ycm_ch_name") String displayName,
                             @FormParam("ycm_ch_datefrom") String fromDate,
                             @FormParam("ycm_ch_dateto") String toDate) throws ServiceException {

        boolean ok = true;
        try {
            ZonedDateTime newFromDate;
            ZonedDateTime newToDate;
            //Get the requested CAR object
            YouSeeChannelMapping ycm = new YouSeeChannelMapping();
            ycm.setSbChannelId(channel);
            ycm.setYouSeeChannelId(youseeId);
            ycm.setDisplayName(displayName);

            if (fromDate == null || "".equals(fromDate)) {
                fromDate = "1900-01-01";
            }
            LocalDate localFromDate = LocalDate.parse(fromDate, dateFormatter);
            newFromDate = localFromDate.atStartOfDay(localZone);
            ycm.setFromDate(Date.from(newFromDate.toInstant()));

            if (toDate == null || "".equals(toDate)) {
                toDate = "3000-01-01";
            }
            LocalDate localToDate = LocalDate.parse(toDate, dateFormatter);
            newToDate = localToDate.atStartOfDay(localZone);

            if (newToDate.isAfter(ZonedDateTime.ofInstant(ycm.getFromDate().toInstant(), localZone)) || newToDate.equals(newFromDate))
                ycm.setToDate(Date.from(newToDate.toInstant()));
            else {
                ok = false;
            }
            ycm.setToDate(Date.from(newToDate.toInstant()));
            //Insert the object in db
            service.create(ycm);
        } catch (ServiceException e) {
            return "Error ";
        }
        if (ok)
            return "ok";
        else
            return "Error";
    }
}

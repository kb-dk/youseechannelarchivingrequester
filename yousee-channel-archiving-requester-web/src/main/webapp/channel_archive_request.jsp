<%@ page import="java.util.List" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchiveRequestDAO" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.WeekdayCoverage" %>
<%@ page import="java.util.Date" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingServiceIF" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.WeekdayCoverageTime" %>
<%@ page
        import="static dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web.ChannelArchiveRequestCRUDServlet.*" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchivingRequesterHibernateUtil" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.ChannelArchiveRequestService" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.validator.ChannelArchivingRequesterValidator" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.validator.ValidationFailure" %>
<%
    List<ChannelArchiveRequest> requests = (new ChannelArchiveRequestService()).getAllRequests();
    ChannelArchivingRequesterValidator validator = new ChannelArchivingRequesterValidator();
    final List<ValidationFailure> failures = validator.getFailures();
    ChannelArchivingRequesterValidator.markFailuresAsDisabled(requests, failures);
    YouSeeChannelMappingServiceIF ucService = new YouSeeChannelMappingService();
%>
<table>
    <tr>
        <th>Channel</th>
        <th>Coverage</th>
        <th>Start Time</th>
        <th>End Time</th>
        <th>From</th>
        <th>To</th>
        <th><!--Submit Buttons in this column--></th>
    </tr>
    <%
        for (ChannelArchiveRequest caRequest : requests) {
            Long id = caRequest.getId();
            String sbChannel = caRequest.getsBChannelId();
            WeekdayCoverage coverage = caRequest.getWeekdayCoverage();
            Date fromDate = caRequest.getFromDate();
            Date toDate = caRequest.getToDate();
            Date fromTime = caRequest.getFromTime();
            Date toTime = caRequest.getToTime();
            WeekdayCoverageTime toTimeWct = new WeekdayCoverageTime(toTime);
            WeekdayCoverageTime fromTimeWct = new WeekdayCoverageTime(fromTime);
            NumberFormat formatter = new DecimalFormat("00");
            String classType;
            if (caRequest.isEnabled()) {
                classType = "";
            } else {
                classType = "class=\"disabled\"";
            }
    %>  <!--Each row is an html form -->
    <form action="./ChannelArchiveRequestCRUDServlet" method="post">
        <input type="hidden" name="<%=Id%>" value="<%=id%>"/>
        <tr <%=classType%> >
            <td><input name="<%=CHANNEL%>" value="<%=caRequest.getsBChannelId()%>" size="8"/></td>
            <td><%=WeekdayCoverage.getHtmlSelect(COVERAGE, null, null, coverage)%>
            </td>
            <td><input name="<%=FROM_TIME_HOURS%>" value="<%=formatter.format(fromTimeWct.getHours())%>" size="2"
                       maxlength="2"/>:<input name="<%=FROM_TIME_MINUTES%>"
                                              value="<%=formatter.format(fromTimeWct.getMinutes())%>" size="2"
                                              maxlength="2"/></td>
            <td><input name="<%=TO_TIME_HOURS%>" value="<%=formatter.format(toTimeWct.getHours())%>" size="2"
                       maxlength="2"/>:<input name="<%=TO_TIME_MINUTES%>"
                                              value="<%=formatter.format(toTimeWct.getMinutes())%>" size="2"
                                              maxlength="2"/></td>
            <td><input name="<%=FROM_DATE%>" value="<%=JAVA_DATE_FORMAT.format(fromDate)%>" size="10" maxlength="10"/>
            </td>
            <td><input name="<%=TO_DATE%>" value="<%=JAVA_DATE_FORMAT.format(toDate)%>" size="10" maxlength="10"/></td>
            <td>
                <button type="submit" name="<%=SUBMIT_ACTION%>" value="<%=UPDATE%>">Update</button>
                <button type="submit" name="<%=SUBMIT_ACTION%>" value="<%=DELETE%>">Delete</button>
            </td>
        </tr>
    </form>
    <%
        }
    %>
    <form action="./ChannelArchiveRequestCRUDServlet" method="post">
        <td><input name="<%=CHANNEL%>" value="" size="8"/></td>
        <td><%=WeekdayCoverage.getHtmlSelect(COVERAGE, null, null, null)%>
        </td>
        <td><input name="<%=FROM_TIME_HOURS%>" value="00" size="2" maxlength="2"/>:<input name="<%=FROM_TIME_MINUTES%>"
                                                                                          value="00" size="2"
                                                                                          maxlength="2"/></td>
        <td><input name="<%=TO_TIME_HOURS%>" value="00" size="2" maxlength="2"/>:<input name="<%=TO_TIME_MINUTES%>"
                                                                                        value="00" size="2"
                                                                                        maxlength="2"/></td>
        <td><input id="start_create" name="<%=FROM_DATE%>" value="" size="10" maxlength="10"/></td>
        <td><input id="end_create" name="<%=TO_DATE%>" value="" size="10" maxlength="10"/></td>
        <td>
            <button type="submit" name="<%=SUBMIT_ACTION%>" value="<%=CREATE%>">Create</button>
        </td>
    </form>
</table>



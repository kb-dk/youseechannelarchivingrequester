<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.persistence.HibernateUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchiveRequestDAO" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.WeekdayCoverage" %>
<%@ page import="java.util.Date" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingServiceIF" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.WeekdayCoverageTime" %>
<%
    List<ChannelArchiveRequest> requests = (new ChannelArchiveRequestDAO()).getAllRequests();
    YouSeeChannelMappingServiceIF ucService = new YouSeeChannelMappingService();
%>
<table>
    <tr>
        <th>Channel</th><th>Coverage</th><th>Start Time</th><th>End Time</th><th>From</th><th>To</th><th><!--Submit Buttons in this column--></th>
    </tr>
<%
    for (ChannelArchiveRequest caRequest: requests) {
        Long id = caRequest.getId();
        String sbChannel = caRequest.getsBChannelId();
        WeekdayCoverage coverage = caRequest.getWeekdayCoverage();
        Date fromDate = caRequest.getFromDate();
        Date toDate = caRequest.getToDate();
        Date fromTime = caRequest.getFromTime();
        Date toTime = caRequest.getToTime();
        WeekdayCoverageTime toTimeWct = new WeekdayCoverageTime(toTime);
        WeekdayCoverageTime fromTimeWct = new WeekdayCoverageTime(fromTime);

%>  <!--Each row is an html form -->
    <form>
    <tr>
        <td><input value="<%=caRequest.getsBChannelId()%>"/></td>
        <td><input value="<%=caRequest.getWeekdayCoverage().getDescription()%>" /></td>
        <td><input value="<%=fromTimeWct.getHours()%>" size="2"/>:<input value="<%=fromTimeWct.getMinutes()%>" size="2"/></td>
        <td><input value="<%=toTimeWct.getHours()%>" size="2"/>:<input value="<%=toTimeWct.getMinutes()%>" size="2"/></td>
        <td><input value="<%=fromDate%>" /></td>
        <td><input value="<%=toDate%>" /></td>
        <td><button>Update</button><button>Delete</button></td>
    </tr>
    </form>
<%
    }
%>
    <form action="./ChannelArchiveRequestCRUDServlet" method="post" >
       <td><input value=""/></td>
        <td><input value="" /></td>
        <td><input value="00" size="2"/>:<input value="00" size="2"/></td>
        <td><input value="00" size="2"/>:<input value="00" size="2"/></td>
        <td><input value="<%=new Date()%>" /></td>
        <td><input value="<%=new Date(3000,0,1)%>" /></td>
        <td><button type="submit">Create</button></td>
    </form>
</table>


<%@ page import="java.util.List" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService" %>
<%@page import="static dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web.YouSeeChannelMappingCRUDServlet.*" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping" %>
<%@ page import="java.util.Date" %>
<%
    List<YouSeeChannelMapping> mappings = (new YouSeeChannelMappingService()).getAllMappings();
%>

<table>
    <tr>
        <th>Channel</th><th>YouSee Id</th><th>Name</th><th>From</th><th>To</th><th></th>
    </tr>
    <%
        for (YouSeeChannelMapping mapping: mappings) {
            Long id = mapping.getId();
            String sbId = mapping.getSbChannelId();
            String youSeeId = mapping.getYouSeeChannelId();
            String displayName = mapping.getDisplayName();
            Date fromDate = mapping.getFromDate();
            Date toDate =mapping.getToDate();
    %>
    <form action="./YouSeeChannelMappingCRUDServlet" method="post">
        <input type="hidden" name="<%=ID%>" value="<%=id%>"/>
        <td><input name="<%=SB_ID%>" value="<%=sbId%>"/></td>
        <td><input name="<%=UC_ID%>" value="<%=youSeeId%>"/></td>
        <td><input name="<%=DISPLAY_NAME%>" value="<%=displayName%>"/> </td>
        <td><input name="<%=FROM_DATE%>" value="<%=JAVA_DATE_FORMAT.format(fromDate)%>"/> </td>
        <td><input name="<%=TO_DATE%>" value="<%=JAVA_DATE_FORMAT.format(toDate)%>"/> </td>
        <td>
            <button>Update</button>
            <button>Delete</button>
        </td>
    </form>
    <%
        }
    %>
    <form action="./YouSeeChannelMappingCRUDServlet" method="post">
        <td><input name="<%=SB_ID%>" value=""/></td>
        <td><input name="<%=UC_ID%>" value=""/></td>
        <td><input name="<%=DISPLAY_NAME%>" value=""/> </td>
        <td><input name="<%=FROM_DATE%>" value=""/> </td>
        <td><input name="<%=TO_DATE%>" value=""/> </td>
        <td>
            <button type="submit" name="<%=SUBMIT_ACTION%>" value="<%=CREATE%>">Create</button>
        </td>
    </form>
</table>
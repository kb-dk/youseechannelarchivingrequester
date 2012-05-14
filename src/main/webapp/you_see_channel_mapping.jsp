<%@ page import="java.util.List" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService" %>
<%@page import="static dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web.YouSeeChannelMappingCRUDServlet.*" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping" %><%
    List<YouSeeChannelMapping> mappings = (new YouSeeChannelMappingService()).getAllMappings();
%>

<table>
    <tr>
        <th>Channel</th><th>You See Id</th><th>Name</th><th>From</th><th>To</th><th></th>
    </tr>
    <%
    for (YouSeeChannelMapping mapping: mappings) {
        Long id = mapping.getId();

    %>
<form action="./YouSeeChannelMappingCRUDServlet" method="post">
    <input type="hidden" name="<%=ID%>" value="<%=id%>"/>
</form>
    <%
    }
    %>
</table>
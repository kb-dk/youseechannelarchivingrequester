<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.persistence.HibernateUtil" %>
<%@ page import="java.io.File" %>
<%@ page import="dk.statsbiblioteket.digitaltv.access.model.persistence.ChannelMappingDAO" %>
<h3>Heloow Wordl</h3>

<%
    String cfgPath= session.getServletContext().getInitParameter("hibernate_cfg");
    HibernateUtil util = HibernateUtil.initialiseFactory(new File(cfgPath));
    ChannelMappingDAO dao = new ChannelMappingDAO();
    int cm_size = dao.getAll().size();
%>

Read <%=cfgPath%> and found <%=cm_size%> Channel Mappings.

<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchivingRequesterHibernateUtil" %>
<%@ page import="java.io.File" %>
<%@ page import="dk.statsbiblioteket.digitaltv.access.model.persistence.ChannelMappingDAO" %>
<%@ page import="dk.statsbiblioteket.digitaltv.web.ControllerServlet" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.validator.ValidationFailure" %>
<%@ page import="java.util.List" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.validator.ChannelArchivingRequesterValidator" %>
<%@ page pageEncoding="UTF-8"
%><%
    ControllerServlet.setUTF8(request);
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Statsbiblioteket's Radio/TV Channel Archive Request Interface</title>
    <link rel="stylesheet" type="text/css" media="all"
          href="style_all.css" />
    <link rel="stylesheet" type="text/css" media="print"
          href="style_print.css" />
    <link rel="stylesheet" type="text/css" media="screen"
          href="style_screen.css" />


    <script type="text/javascript" xml:space="preserve">
        function gotopage(page){
            document.getElementById("page_name").setAttribute("value", page);
            document.nav_form.submit();
        }
        function addLoadEvent(func) {
            var oldonload = window.onload;
            if (typeof window.onload != 'function') {
                window.onload = func;
            } else {
                window.onload = function() {
                    oldonload();
                    func();
                }
            }
        }
    </script>
</head>

<%

%>
 <body>
     <h1>Statsbiblioteket's Radio/TV Channel Archive Request Interface</h1>
     <div id="navigation">
        <form action="simple_dispatcher.jsp" name="nav_form" method="get">
            <!--<a href="#" class="main_nav" onclick="gotopage('');">Start</a>-->
            <a href="#" class="main_nav" onclick="gotopage('you_see_channel_mapping.jsp');">Channels</a>
            <a href="#" class="main_nav" onclick="gotopage('channel_archive_request.jsp');">Requests</a>
            <input id="page_name" type="hidden" name="page"/>
        </form >
     </div>

     <div id="content">
         <%
             String content_page  = (String) request.getAttribute("page_attribute");
         %>
         <% if (content_page != null && !"".equals(content_page)) {%>
         <jsp:include page="<%=content_page%>"/>
         <%} else {%>
         <jsp:include page="channel_archive_request.jsp"/>
         <%}%>

     </div>

     <%
         ChannelArchivingRequesterValidator validator = new ChannelArchivingRequesterValidator();
         final List<ValidationFailure> failures = validator.getFailures();
         if (!failures.isEmpty()) {
     %>
     <div id="failure">
         <h2>WARNING! There are data inconsistencies and some requests have been disabled</h2>
         <%
             for (ValidationFailure failure: failures) {
         %>
         Channel <%=failure.getAffectedSBChannel()%> will not be downloaded because <%=failure.getCause()%>.<br/>
         <%
             }
         %>
     </div>

     <%
         }
     %>

     <%
         if (request.getAttribute("error") != null) {
             Object error =  request.getAttribute("error");
             String error_text;
             if (error instanceof Throwable) {
                    error_text = ((Throwable) request.getAttribute("error")).getMessage();
             } else {
                 error_text = error.toString();
             }
     %>
     <div class="data_error">
         <h2>Input Error</h2>
         <%=error_text%>
     </div>
     <%
         }
     %>

 </body>
</html>
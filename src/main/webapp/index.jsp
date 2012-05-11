<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.persistence.HibernateUtil" %>
<%@ page import="java.io.File" %>
<%@ page import="dk.statsbiblioteket.digitaltv.access.model.persistence.ChannelMappingDAO" %>
<%@ page import="dk.statsbiblioteket.digitaltv.web.ControllerServlet" %>
<%@ page pageEncoding="UTF-8"
%><%
    ControllerServlet.setUTF8(request);
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Statsbiblioteket's Radio/TV Channel Archive Request Interface</title>
    <link rel="stylesheet" type="text/css" media="all"
          href="./style_all.css" />
    <link rel="stylesheet" type="text/css" media="print"
          href="./style_print.css" />
    <link rel="stylesheet" type="text/css" media="screen"
          href="./style_screen.css" />

    <!--Import styling for the javascript calendars -->
    <link rel="stylesheet" type="text/css" href="./JSCal2/src/css/jscal2.css" />
    <link rel="stylesheet" type="text/css" href="./JSCal2/src/css/border-radius.css" />
    <link rel="stylesheet" type="text/css" href="./JSCal2/src/css/gold/gold.css" />
    <script type="text/javascript" src="./JSCal2/src/js/jscal2.js"></script>
    <script type="text/javascript" src="./JSCal2/src/js/lang/en.js"></script>

    <script type="text/javascript">
         function createCalendar(field) {
            Calendar.setup({
                trigger    : field,
                inputField : field,
                fdow       : "1",
                weekNumbers: "true",
                onSelect   : function () {
                    this.hide();
                }
            });
        }

    </script>

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
            <a href="#" class="main_nav" onclick="gotopage('archiving_requests.jsp');">Requests</a>
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

 <div class="data_error">Here we print any warnings if there are data inconsistencies</div>

</body>
</html>
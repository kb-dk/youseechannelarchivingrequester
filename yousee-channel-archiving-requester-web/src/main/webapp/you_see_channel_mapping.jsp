<%@ page import="java.util.List" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService" %>
<%@page import="static dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web.YouSeeChannelMappingCRUDServlet.*" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.YouSeeChannelMapping" %>
<%@ page import="java.util.Date" %>
<%
    List<YouSeeChannelMapping> mappings = (new YouSeeChannelMappingService()).getAllMappings();
%>

<!-- Bootstrap beautification -->
<div class="center-text">
    <table class="table table-bordered table-striped" style="table-layout: fixed;">
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
        <tr>
        <form action="./YouSeeChannelMappingCRUDServlet" method="post">
            <input type="hidden" name="<%=ID%>" value="<%=id%>"/>
            <td><input name="<%=SB_ID%>" value="<%=sbId%>"/></td>
            <td><input name="<%=UC_ID%>" value="<%=youSeeId%>"/></td>
            <td><input name="<%=DISPLAY_NAME%>" value="<%=displayName%>"/> </td>
            <td class="col-md-1" colspan="1">
                <div class="input-group date" data-date-format="yyyy-mm-dd">
                    <input class="input-small" id="dp3" size="10" type="text" name="<%=FROM_DATE%>" value="<%=JAVA_DATE_FORMAT.format(fromDate)%>"/>
                </div>
            </td>
            <td class="col-md-1" colspan="1">
                <div class="input-group date" data-date-format="yyyy-mm-dd">
                    <input class="input-small" size="10" id="dp4" type="text" name="<%=TO_DATE%>" value="<%=JAVA_DATE_FORMAT.format(toDate)%>"/>
                </div>
            </td>
            <td>
                <button type="submit" name="<%=SUBMIT_ACTION%>" value="<%=UPDATE%>">Update</button>
                <button type="submit" name="<%=SUBMIT_ACTION%>" value="<%=DELETE%>">Delete</button>
            </td>
        </form>
        </tr>
        <%
            }
        %>
        <tr>
        <form action="./YouSeeChannelMappingCRUDServlet" method="post">
            <td><input name="<%=SB_ID%>" value=""/></td>
            <td><input name="<%=UC_ID%>" value=""/></td>
            <td><input name="<%=DISPLAY_NAME%>" value=""/> </td>
            <td class="col-md-1" colspan="1">
                <div class="input-group date" data-date-format="yyyy-mm-dd">
                    <input class="input-small" id="dp1" size="10" type="text" name="<%=FROM_DATE%>" value=""/>
                </div>
            </td>
            <td class="col-md-1" colspan="1">
                <div class="input-group date" data-date-format="yyyy-mm-dd">
                    <input class="input-small" size="10" id="dp2" type="text" name="<%=TO_DATE%>" value=""/>
                </div>
            </td>
            <td>
                <button type="submit" name="<%=SUBMIT_ACTION%>" value="<%=CREATE%>">Create</button>
            </td>
        </form>
        </tr>
        <script>

            function updateLink() {
                var url = myBaseUrl +  "?fromDate=" + $("#fromDate").val() + "&toDate=" + $("#toDate").val();
                $("#getStatistics").attr("href", url);
            }

            $(document).ready(function(){
                $("#dp1").datepicker({format: "yyyy-mm-dd"});
                $("#dp2").datepicker({format: "yyyy-mm-dd"});
                $("#dp3").datepicker({format: "yyyy-mm-dd"});
                $("#dp4").datepicker({format: "yyyy-mm-dd"});
            });
        </script>
    </table>
</div>
<%@ page import="java.util.List" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.ChannelArchiveRequest" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.persistence.ChannelArchiveRequestDAO" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.WeekdayCoverage" %>
<%@ page import="java.util.Date" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingServiceIF" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.service.YouSeeChannelMappingService" %>
<%@ page import="dk.statsbiblioteket.mediaplatform.ingest.model.WeekdayCoverageTime" %>
<%@ page import="static dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web.ChannelArchiveRequestCRUDServlet.*" %>
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
<!-- Bootstrap beautification -->
<div class="center-text">
    <table class="table table-bordered active" style="table-layout: fixed;">
        <thead class="active">
        <tr class="active">
            <th class="col-md-2 text-center" colspan="2">Channel</th>
            <th class="col-md-2 text-center" colspan="2">Coverage</th>
            <th class="col-md-2 text-center" colspan="2">Start Time</th>
            <th class="col-md-2 text-center" colspan="2">End Time</th>
            <th class="col-md-1 text-center" colspan="1">From</th>
            <th class="col-md-1 text-center" colspan="1">To</th>
            <th class="col-md-2" colspan="2"><!--Submit Buttons in this column--></th>
        </tr>
        <tr class="active">
            <th class="col-md-2 text-center" colspan="2"></th>
            <th class="col-md-2 text-center" colspan="2"></th>
            <th class="col-md-1 text-center" colspan="1">Hour</th>
            <th class="col-md-1 text-center" colspan="1">Minute</th>
            <th class="col-md-1 text-center" colspan="1">Hour</th>
            <th class="col-md-1 text-center" colspan="1">Minute</th>
            <th class="col-md-1 text-center" colspan="1"></th>
            <th class="col-md-1 text-center" colspan="1"></th>
            <th class="col-md-2" colspan="2"><!--Submit Buttons in this column--></th>
        </tr>
        </thead>
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
                NumberFormat formatter = new DecimalFormat("00");
                String classType;
                if (caRequest.isEnabled()) {
                    classType = "";
                } else {
                    classType = "class=\"disabled\"";
                }
        %>  <!--Each row is an html form -->
        <form action="./ChannelArchiveRequestCRUDServlet" method="post" >
            <input type="hidden" name="<%=Id%>" value="<%=id%>" />
            <tr <%=classType%> >
                <td class="col-md-2" colspan="2"><input class="form-control col-md-2" name="<%=CHANNEL%>" value="<%=caRequest.getsBChannelId()%>" size="8"/></td>
                <td class="col-md-2 text-center" colspan="2"><%=WeekdayCoverage.getHtmlSelect(COVERAGE, null, null, coverage)%></td>
                <td class="col-md-1 text-right" colspan="1"><input class="form-control col-md-1 text-right" name="<%=FROM_TIME_HOURS%>" value="<%=formatter.format(fromTimeWct.getHours())%>" size="2" maxlength="2"/></td>
                <td class="col-md-1 text-left" colspan="1"><input class="form-control col-md-1 text-left" name="<%=FROM_TIME_MINUTES%>" value="<%=formatter.format(fromTimeWct.getMinutes())%>" size="2" maxlength="2"/></td>
                <td class="col-md-1 text-right" colspan="1"><input  class="form-control col-md-1 text-right" name="<%=TO_TIME_HOURS%>" value="<%=formatter.format(toTimeWct.getHours())%>" size="2" maxlength="2"/></td>
                <td class="col-md-1 text-left" colspan="1"><input class="form-control col-md-1 text-left" name="<%=TO_TIME_MINUTES%>" value="<%=formatter.format(toTimeWct.getMinutes())%>" size="2" maxlength="2"/></td>
                <td class="col-md-1" colspan="1">
                    <div class="input-append date" id="dp3" data-date-format="yyyy-mm-dd">
                        <input class="input-small" type="text" name="<%=FROM_DATE%>" value="<%=JAVA_DATE_FORMAT.format(fromDate)%>" size="10" maxlength="10" />
                    </div>
                </td>
                <td class="col-md-1" colspan="1">
                    <div class="input-append date" id="dp4" data-date-format="yyyy-mm-dd">
                        <input class="input-small" size="10" type="text" name="<%=TO_DATE%>" value="<%=JAVA_DATE_FORMAT.format(toDate)%>" maxlength="10" />
                    </div>
                </td>
                <td class="col-md-2" colspan="2">
                    <button type="submit" name="<%=SUBMIT_ACTION%>" value="<%=UPDATE%>">Update</button>
                    <button type="submit" name="<%=SUBMIT_ACTION%>" value="<%=DELETE%>">Delete</button>
                </td>
            </tr>
        </form>
        <%
            }
        %>
        <form action="./ChannelArchiveRequestCRUDServlet" method="post" >
            <td class="col-md-2" colspan="2"><input class="form-control col-md-2" name="<%=CHANNEL%>" value="" size="8"/></td>
            <td class="col-md-2 text-center" colspan="2"> <%=WeekdayCoverage.getHtmlSelect(COVERAGE, null, null, null)%></td>
            <td class="col-md-1 text-right" colspan="1">
                <div class="input-group">
                    <input class="form-control input-sm" size="10" name="<%=FROM_TIME_HOURS%>" value="00" size="2" maxlength="2" placeholder="input-sm">
                </div>
            </td>

            <td class="col-md-1 text-left" colspan="1"><input class="form-control col-md-1 text-left" name="<%=FROM_TIME_MINUTES%>" value="00" size="2" maxlength="2"/></td>
            <td class="col-md-1 text-right" colspan="1"><input class="form-control col-md-1 text-right" name="<%=TO_TIME_HOURS%>" value="00" size="2" maxlength="2"/></td>
            <td class="col-md-1 text-left" colspan="1"><input class="form-control col-md-1 text-left" name="<%=TO_TIME_MINUTES%>" value="00" size="2" maxlength="2"/></td>
            <td class="col-md-1" colspan="1">
                <div class="input-group">
                    <input class="input-small" id="start_create" name="<%=FROM_DATE%>" value=""  size="10" maxlength="10" type="text"/>
                    <!--id="dp1" size="10" type="text">-->
                </div>
            </td>
            <td class="col-md-1" colspan="1">
                <div class="input-group">
                    <input class="input-small" id="end_create" name="<%=TO_DATE%>" value=""  size="10" maxlength="10" type="text"/>
                    <!--id="dp2" size="10" type="text">-->
                </div>
            </td>
            <td class="col-md-2" colspan="2">
                <button type="submit" name="<%=SUBMIT_ACTION%>" value="<%=CREATE%>">Create</button>
            </td>
        </form>
        <script>

            function updateLink() {
                var url = myBaseUrl +  "?fromDate=" + $("#fromDate").val() + "&toDate=" + $("#toDate").val();
                $("#getStatistics").attr("href", url);
            }

            $(document).ready(function(){
                $("#dp1").datepicker({format: "yyyy-mm-dd"});
                $("#dp2").datepicker({format: "yyyy-mm-dd"});
                $("#start_create").datepicker({format: "yyyy-mm-dd"});
                $("#end_create").datepicker({format: "yyyy-mm-dd"});

            });
        </script>
    </table>
</div>


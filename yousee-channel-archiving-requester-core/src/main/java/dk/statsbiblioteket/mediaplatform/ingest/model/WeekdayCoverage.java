package dk.statsbiblioteket.mediaplatform.ingest.model;

/**
 *
 */
public enum WeekdayCoverage {
    DAILY("Daily"), MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"), THURSDAY("Thursday"), FRIDAY("Friday"),
    SATURDAY("Saturday"), SUNDAY("Sunday"), MONDAY_TO_THURSDAY("Monday-Thursday"), MONDAY_TO_FRIDAY("Monday-Friday"), SATURDAY_AND_SUNDAY("Saturday-Sunday"),  ;

    String description;

    WeekdayCoverage(String name) {
       this.description = name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Generate a representation of this enum as an html drop down select.
     * @param name The web-parameter name with which this is to be submitted
     * @param id The id with which this drop down is identified (may be null)
     * @param htmlClass the css class with which this drop down is identified (may be null)
     * @param selected The element to be selected (may be null)
     * @return An html select element which can be dropped into a jsp page
     */
    public static String getHtmlSelect(String name, String id, String htmlClass, WeekdayCoverage selected) {
        StringBuilder sb = new StringBuilder();
        sb.append("<select name=\"").append(name).append("\" ");
        for (WeekdayCoverage coverage: WeekdayCoverage.values()) {
            boolean thisOneSelected = false;
            if (selected != null && coverage.equals(selected)) {
                thisOneSelected = true;
            }
            sb.append("><option value=\"").append(coverage.name()).append("\" ");
            if (thisOneSelected) {
                sb.append(" selected=\"selected\" ");
            }
            sb.append(">").append(coverage.description).append("</option>");
        }
        sb.append("</select>");
        return sb.toString();
    }
}

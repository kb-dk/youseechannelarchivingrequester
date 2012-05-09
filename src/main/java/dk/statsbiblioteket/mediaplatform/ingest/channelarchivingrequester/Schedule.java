package dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester;

/**
 * Created by IntelliJ IDEA.
 * User: csr
 * Date: 5/8/12
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Schedule {
    DAILY("daily"), MONDAY("monday"), MONDAY_TO_FRIDAY("Monday-Friday") ;

    String description;

    Schedule(String name) {
       this.description = name;
    }

    public String getDescription() {
        return description;
    }
}

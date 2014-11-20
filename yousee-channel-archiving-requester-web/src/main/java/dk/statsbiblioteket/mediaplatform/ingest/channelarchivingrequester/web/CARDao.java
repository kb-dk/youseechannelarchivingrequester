package dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web;

import java.util.HashMap;
import java.util.Map;

import dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web.CAR;

public enum CARDao {
    instance;

    private Map<String, CAR> contentProvider = new HashMap<String, CAR>();

    private CARDao() {

/*        CAR car = new CAR("1", "Learn REST");
        car.setDescription("Read http://www.vogella.com/tutorials/REST/article.html");
        contentProvider.put("1", car);
        car = new CAR("2", "Do something");
        car.setDescription("Read complete http://www.vogella.com");
        contentProvider.put("2", car);
*/
    }

    public Map<String, CAR> getModel() {
        return contentProvider;
    }

}
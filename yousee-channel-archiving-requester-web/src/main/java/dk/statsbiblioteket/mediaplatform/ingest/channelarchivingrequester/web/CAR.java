package dk.statsbiblioteket.mediaplatform.ingest.channelarchivingrequester.web;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
// Isn't that cool?
public class CAR {
    private String summary;
    private String description;
    private int id;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CAR() {
    }

    public CAR(int id, String summary, String description) {
        this.id = id;
        this.summary = summary;
        this.description = description;
    }

    @Override
    public String toString() {
        return new StringBuffer(" Summary : ").append(this.summary)
                .append(" Description : ").append(this.description)
                .append(" ID : ").append(this.id).append(" ID : ").toString();
    }
}
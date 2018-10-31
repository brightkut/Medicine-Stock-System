package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Lotte {
    private  final StringProperty purchaseid;
    private final StringProperty numberlotte;
    private final StringProperty id;
    private final StringProperty qualityofsend;
    private final StringProperty date;

    public Lotte(String purchaseid, String numberlotte, String id, String qualityofsend , String date) {
        this.purchaseid = new SimpleStringProperty(purchaseid);
        this.numberlotte = new SimpleStringProperty(numberlotte);
        this.id = new SimpleStringProperty(id);
        this.qualityofsend = new SimpleStringProperty(qualityofsend);
        this.date = new SimpleStringProperty(date);

    }


    public String getId() {
        return id.get();
    }



    public void setId(String id) {
        this.id.set(id);
    }




}

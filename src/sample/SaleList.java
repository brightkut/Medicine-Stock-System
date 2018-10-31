package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SaleList {
    private  final StringProperty saleid;
    private final StringProperty name;
    private final StringProperty numberlotte;
    private final StringProperty qualitysale;
    private final StringProperty date;


    public SaleList(String saleid,String name,String numberlotte,String qualitysale ,String date) {
        this.saleid = new SimpleStringProperty(saleid);
        this.name = new SimpleStringProperty(name);
        this.numberlotte = new SimpleStringProperty(numberlotte);
        this.qualitysale = new SimpleStringProperty(qualitysale);
        this.date = new SimpleStringProperty(date);

    }

    public String getSaleid() {
        return saleid.get();
    }

    public StringProperty saleidProperty() {
        return saleid;
    }

    public void setSaleid(String saleid) {
        this.saleid.set(saleid);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getNumberlotte() {
        return numberlotte.get();
    }

    public StringProperty numberlotteProperty() {
        return numberlotte;
    }

    public void setNumberlotte(String numberlotte) {
        this.numberlotte.set(numberlotte);
    }

    public String getQualitysale() {
        return qualitysale.get();
    }

    public StringProperty qualitysaleProperty() {
        return qualitysale;
    }

    public void setQualitysale(String qualitysale) {
        this.qualitysale.set(qualitysale);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}

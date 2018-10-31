package sample;
import javafx.beans.property.IntegerProperty;
import  javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Medicine {
    private  final StringProperty id;
    private final StringProperty name;
    private final StringProperty qualitylotte;
    private final StringProperty qualityofkeep;
    private final StringProperty unitofkeep;

    public Medicine(String id,String name,String qualitylotte,String qualityofkeep,String unitofkeep) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.qualitylotte = new SimpleStringProperty(qualitylotte);
        this.qualityofkeep = new SimpleStringProperty(qualityofkeep);
        this.unitofkeep = new SimpleStringProperty(unitofkeep);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
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

    public String getQualitylotte() {
        return qualitylotte.get();
    }

    public StringProperty qualitylotteProperty() {
        return qualitylotte;
    }

    public void setQualitylotte(String qualitylotte) {
        this.qualitylotte.set(qualitylotte);
    }

    public String getQualityofkeep() {
        return qualityofkeep.get();
    }

    public StringProperty qualityofkeepProperty() {
        return qualityofkeep;
    }

    public void setQualityofkeep(String qualityofkeep) {
        this.qualityofkeep.set(qualityofkeep);
    }

    public String getUnitofkeep() {
        return unitofkeep.get();
    }

    public StringProperty unitofkeepProperty() {
        return unitofkeep;
    }

    public void setUnitofkeep(String unitofkeep) {
        this.unitofkeep.set(unitofkeep);
    }
}

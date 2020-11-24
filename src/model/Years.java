package model;

public class Years {
    private String Year;

    public Years() {
    }

    public Years(String year) {
        Year = year;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    @Override
    public String toString() {
        return Year;
    }
}

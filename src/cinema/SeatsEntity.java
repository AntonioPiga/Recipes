package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SeatsEntity {

    int row = 0;
    int column = 0;
    int price = 0;

    @JsonIgnore
    boolean isAvailable = true;

    public SeatsEntity() {

    }

    public SeatsEntity(int row, int column, boolean isAvailable) {
        this.row = row;
        this.column = column;
        this.isAvailable = isAvailable;
        if(row <= 4) {
            this.price = 10;
        } else this.price = 8;
    }
    public SeatsEntity(int row, int column) {
        this.row = row;
        this.column = column;
        if(row <= 4) {
            this.price = 10;
        } else this.price = 8;

    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPriceByRow(int row) {
        if(row <= 4) {
            price = 10;
        }
        else price = 8;
    }

    @JsonIgnore
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
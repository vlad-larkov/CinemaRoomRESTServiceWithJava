package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.UUID;

//POJO
public class Seat {
    //private int id;
    private int row;
    private int column;
    private int price;
    @JsonIgnore
    private boolean isAvailable;
    @JsonIgnore
    private UUID uuid;

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

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @JsonIgnore
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public UUID getUuid() {
        return uuid;
    }

    @JsonIgnore
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Seat() {
    }

    public Seat(int row, int column, int price, boolean isAvailable) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row == seat.row && column == seat.column;
    }

    //rewrite according to Effective Java
    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}

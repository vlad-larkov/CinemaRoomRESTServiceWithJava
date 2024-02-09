package cinema;

import java.util.List;

public class Cinema {

    private int rows;
    private int columns;
    private List<Seat> seats;


    public Cinema() {
    }

    public Cinema(int rows, int columns, List<Seat> seats) {
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

}

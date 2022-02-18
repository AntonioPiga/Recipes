package cinema;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class CinemaRoom {

    @JsonProperty
    int totalRows = 9;
    int totalColumns = 9;
    List<SeatsEntity> availableSeats = new ArrayList<>();

    public CinemaRoom(List<SeatsEntity> seatsList,int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = seatsList;
    }

    public CinemaRoom() {

    }

    public List<SeatsEntity> createAllSeats() {
        List<SeatsEntity> seatsList = new ArrayList<>();
        for(int i = 0; i < totalRows; i++) {
            for(int j = 0; j < totalColumns; j++) {
                seatsList.add(new SeatsEntity(i+1,j+1, true));
            }
        }
        return seatsList;
    }

    public List<SeatsEntity> takeAllSeats() {
        availableSeats = createAllSeats()
                .stream()
                .filter(seat -> seat.isAvailable)
                .collect(Collectors.toList());
        return availableSeats;
    }

    public ResponseEntity<SeatsEntity> takeATicket(List<SeatsEntity> seatsList, int row, int column) {

        boolean isSeatAvailable = isSeatAvailable(row, column);
        if(isSeatAvailable) {
                seatsList.stream()
                        .filter(s -> s.row == row && s.column == column)
                        .collect(Collectors.toList()).get(0)
                        .setAvailable(false);

                return ResponseEntity.ok(new SeatsEntity(row, column));
        }
        return new ResponseEntity(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
    }

    public boolean isSeatAvailable(int row, int column) {

        return availableSeats.stream()
                .filter(seat -> seat.row == row && seat.column==column)
                .filter(seat -> seat.isAvailable)
                .count() == 1;
    }
}
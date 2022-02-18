package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController("")
public class CinemaController {

    CinemaRoom cinemaRoom = new CinemaRoom();

    List<SeatsEntity> seatsEntityList = cinemaRoom.takeAllSeats();

    Map<UUID, SeatsEntity> mappaTokenBiglietti = new HashMap<UUID, SeatsEntity>();

    Statistic statistic = new Statistic();


    @GetMapping("/seats")
    public ResponseEntity<CinemaRoom> getInfo() {
        return ResponseEntity.ok(new CinemaRoom(seatsEntityList.stream().filter(s -> s.isAvailable).collect(Collectors.toList()), 9, 9));
    }

    @RequestMapping(value = "/purchase2", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<SeatsEntity> takeTicket(@RequestBody SeatsEntity seatBody) {
        if (seatBody.row > 9 || seatBody.column > 9 || seatBody.row < 1 || seatBody.column < 1)
            return new ResponseEntity(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        return cinemaRoom.takeATicket(seatsEntityList, seatBody.row, seatBody.column);
    }

    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    public Object bigliettoAcquistato(@RequestBody SeatsEntity seatBody) throws Exception {
       if(seatBody.row > 9 || seatBody.column > 9 || seatBody.row < 1 || seatBody.column < 1) return new ResponseEntity(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
       ResponseEntity<SeatsEntity> seat = cinemaRoom.takeATicket(seatsEntityList, seatBody.row, seatBody.column);
        if (seat.getStatusCodeValue() == 200)
        {
            UUID token = UUID.randomUUID();
            BigliettiCinema bigliettiCinema = new BigliettiCinema(token, seat);
            mappaTokenBiglietti.put(token, seat.getBody());
            return bigliettiCinema;
        }
        return new ResponseEntity(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value ="/return", method = RequestMethod.POST)
    public Object bigliettoReso(@RequestBody BigliettiCinema tokenReso) {

        if(mappaTokenBiglietti.containsKey(tokenReso.getToken())) {
            SeatsEntity bigliettoReso = mappaTokenBiglietti.get(tokenReso.getToken());
            seatsEntityList.stream()
                    .filter(seat -> seat.row == bigliettoReso.row && seat.column == bigliettoReso.column)
                    .collect(Collectors.toList()).get(0)
                    .setAvailable(true);
            ReturnedTicket returnedTicket = new ReturnedTicket(bigliettoReso);
            mappaTokenBiglietti.remove(tokenReso.getToken());
            return returnedTicket;
        } else return new ResponseEntity(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/stats", method = RequestMethod.POST)
    public ResponseEntity<Statistic> getStatistic(@RequestParam(value = "password", required = false) String password) {
        statistic.setNumberOfAvailableSeats(
                seatsEntityList.stream()
                        .filter(seat -> seat.isAvailable)
                        .count());

        List<SeatsEntity> postiVenduti = seatsEntityList.stream()
                .filter(seat -> !seat.isAvailable).collect(Collectors.toList());
        int guadagno = 0;
        for (SeatsEntity seatsEntity : postiVenduti) {
            guadagno += seatsEntity.getPrice();
        }
        statistic.setCurrentIncome(guadagno);
        statistic.setNumberOfPurchasedTickets((int) seatsEntityList.stream().filter(seat -> !seat.isAvailable).count());

        return password == null || !password.equals("super_secret") ? new ResponseEntity(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED)
                : ResponseEntity.ok(statistic);
    }

    static class BigliettiCinema {

        @JsonProperty
        UUID token = UUID.randomUUID();

        @JsonProperty
        SeatsEntity ticket;

        public BigliettiCinema(UUID token, SeatsEntity ticket) {
            this.token = token;
            this.ticket = ticket;
        }

        public BigliettiCinema() {
        }

        public BigliettiCinema(UUID token, ResponseEntity<SeatsEntity> takeATicket) throws Exception{
            this.token = token;
            this.ticket = takeATicket.getBody();
        }

        public UUID getToken() {
            return token;
        }

        public void setToken(UUID token) {
            this.token = token;
        }

        @JsonIgnore
        public SeatsEntity getSeats() {
            return ticket;
        }
        @JsonIgnore
        public void setSeats(SeatsEntity ticket) {
            this.ticket = ticket;
        }

    }

}



package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistic {

    @JsonProperty
    public int currentIncome;
    public long numberOfAvailableSeats;
    public long numberOfPurchasedTickets;

    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    public long getNumberOfAvailableSeats() {
        return numberOfAvailableSeats;
    }

    public void setNumberOfAvailableSeats(long numberOfAvailableSeats) {
        this.numberOfAvailableSeats = numberOfAvailableSeats;
    }

    public long getNumberOfPurchasedTickets(long count) {
        return numberOfPurchasedTickets;
    }

    public void setNumberOfPurchasedTickets(int numberOfPurchasedTickets) {
        this.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }
}

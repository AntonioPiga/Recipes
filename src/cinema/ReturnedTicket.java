package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ReturnedTicket {

    @JsonProperty
    SeatsEntity returnedTicket;

    public ReturnedTicket(SeatsEntity returnedTicket) {
        this.returnedTicket = returnedTicket;
    }

    public SeatsEntity getReturnedTicket() {
        return returnedTicket;
    }

    public void setReturnedTicket(SeatsEntity returnedTicket) {
        this.returnedTicket = returnedTicket;
    }
}

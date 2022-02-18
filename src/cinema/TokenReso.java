package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class TokenReso {

    @JsonProperty
    UUID token;

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public TokenReso(UUID token) {
        this.token = token;
    }
}

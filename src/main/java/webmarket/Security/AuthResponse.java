package webmarket.Security;

import java.io.Serializable;

public class AuthResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

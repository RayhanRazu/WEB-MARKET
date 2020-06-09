package webmarket.Security;

import java.io.Serializable;

public class SignUpRequest implements Serializable {

    private static final long serialVersionUID = -6081024151398556807L;

    private String username;
    private String password;
    private String role;

    public SignUpRequest(){
        super();
    }

    public SignUpRequest(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
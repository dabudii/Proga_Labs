package general.interaction;

import java.io.Serializable;

public class Profile implements Serializable {
    private String username;
    private String password;

    public Profile(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * @return Username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return Password.
     */
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return username + ":" + password;
    }

    @Override
    public int hashCode() {
        return username.hashCode() + password.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Profile) {
            Profile profileObj = (Profile) obj;
            return username.equals(profileObj.getUsername()) && password.equals(profileObj.getPassword());
        }
        return false;
    }
}

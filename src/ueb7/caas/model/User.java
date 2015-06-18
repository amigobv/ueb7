package ueb7.caas.model;

import java.io.Serializable;


/**
 * User class to save the user information
 * 
 * @author s1310307036
 *
 */
public class User implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 6536928265897637136L;

    public static class InvalidUsernameException extends Exception {
        /**
         * 
         */
        private static final long serialVersionUID = 4076832930203271201L;

        @Override
        public String toString() {
            return "Invalid user name!";
        }    
    }
    
    public static class InvalidPasswordException extends Exception { 
        /**
         * 
         */
        private static final long serialVersionUID = 6660069990643281016L;

        @Override
        public String toString() {
            return "Invalid password!";
        }
    }
    
    private String username;
    private String password;
    private boolean isLocked;

    public User(String name, String pwd) {
        this.username = name;
        this.password = pwd;
        this.isLocked = false;
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pwd) {
        password = pwd;
    }

    public void lock() {
        isLocked = true;
    }

    public boolean isLocked() {
        return isLocked;
    }
    
    @Override
    public boolean equals(Object o) {
        return username.equals(((User)o).username);
    }
}

package com.bounswe.bounswe2017group3.Model;

import org.hibernate.validator.constraints.NotEmpty;


import javax.persistence.*;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Table(name = "users")
@Entity
public class User implements Serializable {
    /**
     * Unique serial identifier for the current version of the class.
     */
    private static final long serialVersionUID = 6910328600884284596L;

    /**
     * Auto-generated id of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Username of the user.
     */
    @NotEmpty
    @Column(unique = true)
    private String username;

    /**
     * Encrypted password of the user in hexadecimal form.
     */
    @NotEmpty
    private String password;

    /**
     * Email of the user.
     */
    @Column(unique = true)
    private String email;

    /**
     * Name and surname of the user.
     */
    private String fullname;

    /**
     * Deletion Date of the user.
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date deletedAt;

    /**
     * Constructs an empty User object.
     */
    public User(){  }

    /**
     * Constructs an User object with the given details.
     * @param username Username of the user
     * @param fullname Full name of the user
     * @param email Email of the user
     * @param password Password of the user
     */
    public User( String username, String fullname, String email, String password) {
       setUsername(username);
       setFullname(fullname);
       setEmail(email);
       setPassword(password);
    }

    /**
     * Returns the id of the user.
     *
     * @return ID of the user.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the username of the user.
     *
     * @return Username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username Username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the user's password to the encrypted version of the given password.
     *
     * @param password Password of the user.
     */
    public void setPassword(String password) {
        this.password = encryptPassword(password);
    }

    /**
     * Returns the email of the user.
     *
     * @return Email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email Email of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the full name of the user.
     *
     * @return Full name of the user.
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Sets the full name of the user.
     *
     * @param fullname Full name of the user.
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Returns the deletion date of the user.
     *
     * @return Date of deletion of the user.
     */
    public Date getDeletedAt() { return deletedAt;}

    /**
     * Sets the deletion date of the user.
     *
     * @param deletedAt Date of deletion of the user.
     */
    public void setDeletedAt(Date deletedAt) {this.deletedAt = deletedAt;}

    /**
     * Returns the string representation of the user object.
     *
     * @return String representation of the user object.
     */
    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", password='" + password + '\'' +
               ", email='" + email + '\'' +
               ", fullname='" + fullname + '\'' +
               '}';
    }

    /**
     * Encrypts a password using MD5 algorithm and returns it in hexadecimal
     * format.
     *
     * @param password Password to encrypt.
     * @return MD5 encrypted password in hexadecimal format.
     */
    private String encryptPassword(String password) {
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(password.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // This bytes[] has bytes in decimal format.
            // Convert it to hexadecimal format.
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16)
                                 .substring(1));
            }

            // Return complete encrypted password in hexadecimal format
            return sb.toString();
        } catch (NoSuchAlgorithmException ignored) {}

        return null;
    }

    /**
     * Checks whether the given password is this user's password or not.
     *
     * @param password Password to check.
     * @return True if given password the same as user's password, false
     * otherwise.
     */
    public boolean checkPassword(String password) {
        return this.password.equals(encryptPassword(password));
    }
}

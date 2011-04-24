package edu.rit.rdi.as.datalayer;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * @date Apr 24, 2011
 * @author Eric Kisner
 */
public class Patient extends AbstractDatabasePOJO {

    private int patientId;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String phone;
    private String username;
    private String password;

    public Patient() {
        super();
    }

    public Patient( int patientId, String firstName, String lastName, String gender, String email, String phone,
                   String username, String password ) {
        this();
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }


    public Patient( int patientId ) {
        this();
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGender() {
        return gender;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Patient{" + "patientId=" + patientId
               + "\tfirstName=" + firstName
               + "\tlastName=" + lastName
               + "\tgender=" + gender
               + "\temail=" + email
               + "\tphone=" + phone
               + "\tusername=" + username
               + "\tpassword=" + password + '}';
    }

    public boolean put() throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public Object fetch( int primaryKeyId ) throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public Object fetch() throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean post() throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean delete() throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public boolean fullDelete() throws SQLException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    public HashMap asMap( boolean includePrimaryKey ) {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
}

package com.prestaging.fulvila.model;

import javax.persistence.*;

@Entity
@Table(name = "fulvila_team",uniqueConstraints = @UniqueConstraint( name = "un_team_email", columnNames = {"email"}))
public class Team {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @OneToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Admin admin;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "primary_contact")
    private String primaryContact;
    @Column(name = "secondary_contact")
    private String seondaryContact;
    @Column(name = "designation")
    private String designation;
    @Column(name = "permission_type", nullable = false)
    private String permissionType;
    @Column(name = "timestamp")
    private String timeStamp;
    @Column(name = "last_update_time")
    private String lastUpdateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrimaryContact() {
        return primaryContact;
    }

    public void setPrimaryContact(String primaryContact) {
        this.primaryContact = primaryContact;
    }

    public String getSeondaryContact() {
        return seondaryContact;
    }

    public void setSeondaryContact(String seondaryContact) {
        this.seondaryContact = seondaryContact;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", admin=" + admin +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", primaryContact='" + primaryContact + '\'' +
                ", seondaryContact='" + seondaryContact + '\'' +
                ", designation='" + designation + '\'' +
                ", permissionType='" + permissionType + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                '}';
    }
}

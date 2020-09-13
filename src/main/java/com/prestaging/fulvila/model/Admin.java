package com.prestaging.fulvila.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@Table(name ="fulvila_admins",uniqueConstraints = @UniqueConstraint( name = "un_admin_email", columnNames = {"email"}))
public class Admin {
	private static final Logger LOGGER = LoggerFactory.getLogger(Admin.class);

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="name",nullable = false)
	private String adminName;
	@Column(name = "email",nullable = false)
	private String adminEmail;
	@Column(name = "password",nullable = false)
	private String adminPassword;
	@Column(name="contact_no")
	private String adminContact;
	@Column(name="designation")
	private String designation;
	@Column(name = "linkedin_url")
	private String linkdinProfileURL;
	@Column(name = "registration_type", nullable = false)
	private String registrationType;
	@Column(name = "timestamp")
	private String timeStamp;
	@Column(name = "last_update_time")
	private String lastUpdateTime;
	@Transient
	@OneToOne(mappedBy = "admin", cascade = CascadeType.REMOVE)
	private BusinessDetails businessDetails;
	@Transient
	@OneToOne(mappedBy = "admin", cascade = CascadeType.REMOVE)
	private Team teamMember;

	public int getId() {
		return id;
	}

	public void setAdminID(int id) {
		this.id = id;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getAdminContact() {
		return adminContact;
	}

	public void setAdminContact(String adminContact) {
		this.adminContact = adminContact;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getLinkdinProfileURL() {
		return linkdinProfileURL;
	}

	public void setLinkdinProfileURL(String linkdinProfileURL) {
		this.linkdinProfileURL = linkdinProfileURL;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
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

	public BusinessDetails getBusinessDetails() {
		return businessDetails;
	}

	public void setBusinessDetails(BusinessDetails businessDetails) {
		this.businessDetails = businessDetails;
	}

	public Team getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(Team teamMember) {
		this.teamMember = teamMember;
	}

	@Override
	public String toString() {
		return "Admin{" +
				"id=" + id +
				", adminName='" + adminName + '\'' +
				", adminEmail='" + adminEmail + '\'' +
				", adminPassword='" + adminPassword + '\'' +
				", adminContact='" + adminContact + '\'' +
				", designation='" + designation + '\'' +
				", linkdinProfileURL='" + linkdinProfileURL + '\'' +
				", registrationType='" + registrationType + '\'' +
				", timeStamp='" + timeStamp + '\'' +
				", lastUpdateTime='" + lastUpdateTime + '\'' +
				", businessDetails=" + businessDetails +
				", teamMember=" + teamMember +
				'}';
	}
}

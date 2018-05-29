package com.excilys.formation.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.excilys.formation.cdb.model.constants.DbFields;

@Entity
@Table(name = DbFields.USER)
public class UserInfo {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = DbFields.USERNAME)
    private String username;

    @Column(name = DbFields.PASSWORD)
    private String password;
    
    @Column(name = DbFields.ROLE)
	private String role;
    
    private String token;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	
}

package com.mydhaba.model;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String username;
	private String email;
	private String password;
	private Date dob;
	private String role;
	
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private Set<Order> orders;
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", dob="
				+ dob + ", role=" + role + "]";
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(dob, email, id, orders, password, role, username);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(dob, other.dob) && Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(orders, other.orders) && Objects.equals(password, other.password)
				&& Objects.equals(role, other.role) && Objects.equals(username, other.username);
	}
	
	public User(String username, String email, String password, Date dob, String role) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.role = role;
	}
	
	public User(Integer id, String username, String email, String password, Date dob, String role) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.role = role;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	
	
	
}

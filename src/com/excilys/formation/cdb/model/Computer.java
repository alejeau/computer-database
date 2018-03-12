package com.excilys.formation.cdb.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Computer {
	
	private Integer id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private int companyId;
	
	public Computer() {
		
	}
	
	public Computer(int id, String name, LocalDate introduced, LocalDate discontinued, int companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder("Computer: ");
        sb.append(this.name).append(", id: ").append(id).append(", company ID: ").append(companyId).append("\n");
        if (introduced != null)
            sb.append("introduced in: ").append(introduced.format(formatter));
        if (discontinued != null)
            sb.append(", discontinued in: ").append(discontinued.format(formatter));
        return sb.toString();
	}

}

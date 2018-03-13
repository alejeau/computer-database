package com.excilys.formation.cdb.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Computer {
	
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Long companyId;
	
	public Computer() {
		
	}
	
	public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

    public static class Builder {
        private Long nestedId = null;
        private String nestedName = null;
        private LocalDate nestedIntroduced = null;
        private LocalDate nestedDiscontinued = null;
        private Long nestedCompanyId = null;

        public Builder() {
        }

        public Builder id(final Long id) {
            this.nestedId = id;
            return this;
        }

        public Builder name(final String name) {
            this.nestedName = name;
            return this;
        }

        public Builder introduced(final LocalDate intro) {
            this.nestedIntroduced = intro;
            return this;
        }

        public Builder introduced(final String intro) {
            if ((intro == null) || (intro.equals(""))) {
                this.nestedIntroduced = null;
            } else {
                String tmp = intro.split(" ")[0];
                this.nestedIntroduced = LocalDate.parse(tmp);
            }
            return this;
        }

        public Builder discontinued(final LocalDate discontinued) {
            this.nestedDiscontinued = discontinued;
            return this;
        }

        public Builder discontinued(final String discontinued) {
            if ((discontinued == null) || (discontinued.equals(""))) {
                this.nestedDiscontinued = null;
            } else {
                String tmp = discontinued.split(" ")[0];
                this.nestedDiscontinued = LocalDate.parse(tmp);
            }
            return this;
        }

        public Builder companyId(final Long companyId) {
            this.nestedCompanyId = companyId;
            return this;
        }

        public Computer build() {
            return new Computer(nestedId, nestedName, nestedIntroduced, nestedDiscontinued, nestedCompanyId);
        }
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

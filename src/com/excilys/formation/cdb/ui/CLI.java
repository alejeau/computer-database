package com.excilys.formation.cdb.ui;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistance.dao.impl.CompanyDAOImpl;
import com.excilys.formation.cdb.persistance.dao.impl.ComputerDAOImpl;

import java.time.LocalDate;

public class CLI {

	public static void main(String[] args) {
		ComputerDAOImpl crdi = new ComputerDAOImpl();
		CompanyDAOImpl cydi = new CompanyDAOImpl();

		Computer c1 = crdi.getComputer(1l);
        Company apple = cydi.getCompany(1l);

        System.out.println(c1);

        LocalDate introduced = LocalDate.of(2000, 01, 01);
		LocalDate discontinued = LocalDate.now();
		Computer c2 = new Computer.Builder()
				.name("Testouille1")
				.introduced(introduced)
				.discontinued(discontinued)
				.companyId(apple.getId())
                .build();
        System.out.println("c2: " + c2);

		Long c2Id = crdi.persistComputer(c2);
        System.out.println("c2Id: " + c2Id);

        Computer c2Bis = crdi.getComputer(c2Id);
        System.out.println("c2Bis: " + c2Bis);

        crdi.deleteComputer(c2Id);
        Computer c2Ter = crdi.getComputer(c2Id);
        System.out.println(c2Ter);
	}
}

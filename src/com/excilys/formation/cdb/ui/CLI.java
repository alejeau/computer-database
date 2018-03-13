package com.excilys.formation.cdb.ui;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistance.dao.impl.CompanyDAOImpl;
import com.excilys.formation.cdb.persistance.dao.impl.ComputerDAOImpl;

import java.time.LocalDate;
import java.util.Scanner;

public class CLI {

    private Scanner sc = new Scanner(System.in);

    private int mainMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1) View the list of computers");
        System.out.println("2) View the list of companies");
        System.out.println("3) Check a computer by ID");
        System.out.println("4) Check a computer by name");
        System.out.println("5) Add a computer to the list");
        System.out.println("6) Update a computer in the list (by id)");
        System.out.println("7) Delete a computer in the list (by id)");
        System.out.println("8) Exit this program");

        System.out.println("Enter your choice: ");
        int entry = sc.nextInt();

        return entry;
    }

    public void mainLoop() {
        int code = -1;
        do {
            code = mainMenu();
        }
        while (code != 8);
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 1:
                viewList(0, 1);
                break;
            case 2:
                viewList(1, 1);
                break;
            case 3:
                checkComputerById();
                break;
            case 4:
                checkComputerByName();
                break;
            case 5:
                addComputer();
                break;
            case 6:
                updateComputer();
                break;
            case 7:
                deleteComputer();
                break;
            default:
                break;
        }
    }

    private void viewList(int code, int page) {

    }

    private void checkComputerById() {

    }

    private void checkComputerByName() {

    }

    private void addComputer() {

    }

    private void updateComputer() {

    }

    private void deleteComputer() {

    }

	public static void main(String[] args) {
//		ComputerDAOImpl crdi = new ComputerDAOImpl();
//		CompanyDAOImpl cydi = new CompanyDAOImpl();
//
//		Computer c1 = crdi.getComputer(1l);
//        Company apple = cydi.getCompany(1l);
//
//        System.out.println(c1);
//
//        LocalDate introduced = LocalDate.of(2000, 01, 01);
//		LocalDate discontinued = LocalDate.now();
//		Computer c2 = new Computer.Builder()
//				.name("Testouille1")
//				.introduced(introduced)
//				.discontinued(discontinued)
//				.companyId(apple.getId())
//                .build();
//        System.out.println("c2: " + c2);
//
//		Long c2Id = crdi.persistComputer(c2);
//        System.out.println("c2Id: " + c2Id);
//
//        Computer c2Bis = crdi.getComputer(c2Id);
//        System.out.println("c2Bis:\n" + c2Bis);
//
//        crdi.deleteComputer(c2Id);
//        Computer c2Ter = crdi.getComputer(c2Id);
//        System.out.println(c2Ter);
        CLI cli = new CLI();
        cli.mainLoop();
	}
}

package com.excilys.formation.cdb.ui;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Model;
import com.excilys.formation.cdb.persistance.dao.impl.CompanyDAOImpl;
import com.excilys.formation.cdb.persistance.dao.impl.ComputerDAOImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CLI {

    private static final int CODE_COMPUTER = 0;
    private static final int CODE_COMPANY = 1;
    private static final int PAGE_OFFSET = 10;

    private static Scanner sc = new Scanner(System.in);

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
            executeChoice(code);
        }
        while (code != 8);
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 1:
                viewList(CODE_COMPUTER, 1);
                break;
            case 2:
                viewList(CODE_COMPANY, 1);
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

    private void viewList(int code, int pageNumber) {
        List<? extends Model> page = null;
        if (code == CODE_COMPUTER) {
            page = ComputerDAOImpl.INSTANCE.getComputers(pageNumber, PAGE_OFFSET);
        }
        else if (code == CODE_COMPANY) {
            page = CompanyDAOImpl.INSTANCE.getCompanies(pageNumber, PAGE_OFFSET);
        }

        if (page != null)
            page.forEach(e -> System.out.println(e.shortToString()));
        System.out.println();
    }

    private void checkComputerById() {
        Long id = null;
        Computer c = null;

        System.out.println("Please enter the computer's ID: ");
        id = sc.nextLong();
        sc.nextLine();

        c = ComputerDAOImpl.INSTANCE.getComputer(id);

        if (c != null)
            System.out.println(c);
        else
            System.out.println("No computer registered with the ID " + id);
    }

    private void checkComputerByName() {
        String name = null;
        List<Computer> l = null;

        System.out.println("Please enter the computer's name: ");
        name = sc.next();
        sc.nextLine();

        l = ComputerDAOImpl.INSTANCE.getComputer(name, 0, PAGE_OFFSET);

        if (l == null || l.size() == 0)
            System.out.println("No computer registered under the name: " + name);
        else
            l.forEach(e -> System.out.println(e.shortToString()));
    }

    private void addComputer() {
        String name = null;
        System.out.println("Please enter the computer's name:");
        name = sc.next();
        sc.nextLine();

        //TODO: Finish the method

    }

    private LocalDate getDate() {
        int day = -1, month = -1, year = -1;
        while (day < 1 || day > 31) {
            System.out.println("Please enter the day (1-31):");
            day = sc.nextInt();
            sc.nextLine();
        }

        while (month < 1 || month > 12) {
            System.out.println("Please enter the month (1-12):");
            month = sc.nextInt();
            sc.nextLine();
        }

        while (year < -9999 || year > 9999) {
            System.out.println("Please enter the year (XXXX):");
            year = sc.nextInt();
            sc.nextLine();
        }
        return LocalDate.of(year, month, day);
    }

    private void updateComputer() {
        //TODO: Finish the method
    }

    private void deleteComputer() {
        Long id = null;

        System.out.println("Please enter the computer's ID you wish to delete: ");
        id = sc.nextLong();
        sc.nextLine();

        Computer c = ComputerDAOImpl.INSTANCE.getComputer(id);
        if (c != null)
            ComputerDAOImpl.INSTANCE.deleteComputer(id);
        else
            System.out.println("There is no computer with the ID: " + id);
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
//				.company(apple.getId())
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

package com.excilys.formation.cdb.ui;

import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Model;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public enum CLI {
    INSTANCE;

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
                editComputer(new Computer());
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
            page = ComputerService.INSTANCE.getComputers(pageNumber, PAGE_OFFSET);
        }
        else if (code == CODE_COMPANY) {
            page = CompanyService.INSTANCE.getCompanies(pageNumber, PAGE_OFFSET);
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

        c = ComputerService.INSTANCE.getComputer(id);

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

        l = ComputerService.INSTANCE.getComputer(name, 0, PAGE_OFFSET);

        if (l == null || l.size() == 0)
            System.out.println("No computer registered under the name: " + name);
        else
            l.forEach(e -> System.out.println(e.shortToString()));
    }

    private void editComputer(Computer c) {
        String name = null;
        LocalDate introduced = null, discontinued = null;
        Company company = null;

        System.out.println("Please enter the computer's name:");
        name = sc.next();
        sc.nextLine();

        introduced = getDate("introduction");
        discontinued = getDate("discontinuation");

        company = getCompany();

        c.setName(name);
        c.setIntroduced(introduced);
        c.setDiscontinued(discontinued);
        c.setCompany(company);

        try {
            if (c.getId() == null)
                ComputerService.INSTANCE.persistComputer(c);
            else
                ComputerService.INSTANCE.updateComputer(c);
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateComputer() {
        System.out.println("Which computer would you like to update (ID)?");
        Long id = sc.nextLong();
        sc.nextLine();
        Computer c = ComputerService.INSTANCE.getComputer(id);
        editComputer(c);
    }

    private LocalDate getDate(String event) {
        LocalDate date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean ok = false;

        while (!ok) {
            System.out.println("Please enter the computer's " + event + " date (yyyy-MM-dd or 'Enter'):");
            String d1 = sc.next();
            sc.nextLine();
            if (d1.matches("\\d{4}-\\d{2}-\\d{2}") || d1.equals("")) {
                if (!d1.equals(""))
                    date = LocalDate.parse(d1, formatter);
                ok = true;
            }
        }

        return date;
    }

    private Company getCompany() {
        Company c = null;

        while (c == null) {
            System.out.println("Please enter the computer's manufacturer ID:");
            Long companyId = sc.nextLong();
            sc.nextLine();
            c = CompanyService.INSTANCE.getCompany(companyId);
        }

        return c;
    }

    private void deleteComputer() {
        Long id = null;

        System.out.println("Please enter the computer's ID you wish to delete: ");
        id = sc.nextLong();
        sc.nextLine();

        Computer c = ComputerService.INSTANCE.getComputer(id);
        if (c != null)
            ComputerService.INSTANCE.deleteComputer(id);
        else
            System.out.println("There is no computer with the ID: " + id);
    }

	public static void main(String[] args) {
        CLI.INSTANCE.mainLoop();
	}
}

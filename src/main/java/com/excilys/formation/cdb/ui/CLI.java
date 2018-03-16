package com.excilys.formation.cdb.ui;

import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.paginator.CompanyPage;
import com.excilys.formation.cdb.paginator.ComputerPage;
import com.excilys.formation.cdb.paginator.ComputerSearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.function.Consumer;

public enum CLI {
    INSTANCE;

    private static final LimitValue NUMBER_OF_ELEMENTS_PER_PAGE = LimitValue.TEN;
    private static Consumer<Computer> displayShortToString = c -> System.out.println(c.shortToString());

    private Scanner sc = new Scanner(System.in);

    CLI() {
    }

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
        sc.nextLine();

        return entry;
    }

    public void mainLoop() {
        int code;
        do {
            code = mainMenu();
            executeChoice(code);
            System.out.println();
        }
        while (code != 8);
    }

    private void executeChoice(int choice) {
        switch (choice) {
            case 1:
                ComputerPage computerPage = ComputerService.INSTANCE.getComputers(NUMBER_OF_ELEMENTS_PER_PAGE);
                viewPage(computerPage, displayShortToString);
                break;
            case 2:
                CompanyPage companyPage = CompanyService.INSTANCE.getCompanyPage(NUMBER_OF_ELEMENTS_PER_PAGE);
                viewPage(companyPage, System.out::println);
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

    private <T extends Page> void viewPage(T page, Consumer printer) {
        boolean exit = false;
        String choice;

        while (!exit) {
            System.out.println("Which page would you like to view (current page: " + page.getPageNumber() + ")?");
            System.out.println("n for next, p for previous, f for first, l for last and q to quit");
            choice = sc.nextLine();

            if (choice.isEmpty() || choice.equals("f")) {
                page.first().forEach(printer);
            } else if (choice.equals("q")) {
                exit = true;
            } else if (choice.equals("p")) {
                page.previous().forEach(printer);
            } else if (choice.equals("n")) {
                page.next().forEach(printer);
            } else if (choice.equals("l")) {
                page.last().forEach(printer);
            } else if (choice.matches("[0-9]+")) {
                page.goToPage(Long.decode(choice)).forEach(printer);
            }
            System.out.println();
        }
    }

    private void checkComputerById() {
        Long id;
        Computer c = null;

        System.out.println("Please enter the computer's ID: ");
        id = sc.nextLong();
        sc.nextLine();

        c = ComputerService.INSTANCE.getComputer(id);

        if (c != null) {
            System.out.println(c);
        } else {
            System.out.println("No computer registered with the ID " + id);
        }
    }

    private void checkComputerByName() {
        String name;

        System.out.println("Please enter the computer's name: ");
        name = sc.next();
        sc.nextLine();

        ComputerSearchPage computerSearchPage = new ComputerSearchPage(name, NUMBER_OF_ELEMENTS_PER_PAGE);
        viewPage(computerSearchPage, displayShortToString);
    }

    private void editComputer(Computer c) {
        String name;
        LocalDate introduced, discontinued;
        Company company;

        System.out.println("Please enter the computer's name:");
        name = sc.nextLine();

        introduced = getDate("introduction");
        discontinued = getDate("discontinuation");

        company = getCompanyId();

        c.setName(name);
        c.setIntroduced(introduced);
        c.setDiscontinued(discontinued);
        c.setCompany(company);

        try {
            if (c.getId() == null) {
                Long id = ComputerService.INSTANCE.persistComputer(c);
                System.out.println("The computer has the ID: " + id);
            } else {
                ComputerService.INSTANCE.updateComputer(c);
            }
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

    /**
     * Asks the user for a date with the pattern "yyyy-MM-dd", or nothing if 'Enter'is hit.
     *
     * @param event the name of the event you want a date for.
     * @return a LocalDate corresponding to the input user's date.
     */
    private LocalDate getDate(String event) {
        LocalDate date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean ok = false;

        while (!ok) {
            System.out.println("Please enter the computer's " + event + " date (yyyy-MM-dd or 'Enter' for no date):");
            String d1 = sc.nextLine();
            if (d1.isEmpty() || d1.matches("\\d{4}-\\d{2}-\\d{2}")) {
                if (!d1.isEmpty()) {
                    date = LocalDate.parse(d1, formatter);
                }
                ok = true;
            }
        }

        return date;
    }

    private Company getCompanyId() {
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
        Long id;

        System.out.println("Please enter the computer's ID you wish to delete: ");
        id = sc.nextLong();
        sc.nextLine();

        Computer c = ComputerService.INSTANCE.getComputer(id);
        if (c != null) {
            ComputerService.INSTANCE.deleteComputer(id);
        } else {
            System.out.println("There is no computer with the ID: " + id + "\n");
        }
    }

    public static void main(String[] args) {
        CLI.INSTANCE.mainLoop();
    }
}

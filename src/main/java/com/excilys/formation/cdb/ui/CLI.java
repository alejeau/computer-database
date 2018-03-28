package com.excilys.formation.cdb.ui;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.DatePattern;
import com.excilys.formation.cdb.model.Model;
import com.excilys.formation.cdb.paginator.CompanyPage;
import com.excilys.formation.cdb.paginator.ComputerPage;
import com.excilys.formation.cdb.paginator.ComputerSearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.service.impl.CompanyServiceImpl;
import com.excilys.formation.cdb.service.impl.ComputerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Scanner;

public enum CLI {
    INSTANCE;
    private static final Logger LOG = LoggerFactory.getLogger(CLI.class);

    private static final LimitValue NUMBER_OF_ELEMENTS_PER_PAGE = LimitValue.TEN;

    private Scanner sc = new Scanner(System.in);

    CLI() {
    }

    private int mainMenu() {

        System.out.println("What would you like to do?");
        System.out.println(CliActions.VIEW_COMPUTER_LIST.getValue());
        System.out.println(CliActions.VIEW_COMPANY_LIST.getValue());
        System.out.println(CliActions.CHECK_COMPUTER_BY_ID.getValue());
        System.out.println(CliActions.CHECK_COMPUTERS_BY_NAME.getValue());
        System.out.println(CliActions.ADD_COMPUTER.getValue());
        System.out.println(CliActions.UPDATE_COMPUTER.getValue());
        System.out.println(CliActions.DELETE_COMPUTER.getValue());
        System.out.println(CliActions.EXIT.getValue());

        System.out.println("Enter your choice: ");
        int entry = sc.nextInt();
        sc.nextLine();

        return entry;
    }

    public void mainLoop() {
        int code;
        do {
            code = mainMenu();
            try {
                executeChoice(CliActions.map(code));
            } catch (ServiceException e) {
                LOG.error("{}", e);
                System.out.println("Couldn't execute the chosen action!");
            }
            System.out.println();
        }
        while (code != 8);
    }

    private void executeChoice(CliActions choice) throws ServiceException {
        switch (choice) {
            case VIEW_COMPUTER_LIST:
                ComputerPage computerPage = ComputerServiceImpl.INSTANCE.getComputers(NUMBER_OF_ELEMENTS_PER_PAGE);
                viewPage(computerPage);
                break;
            case VIEW_COMPANY_LIST:
                CompanyPage companyPage = CompanyServiceImpl.INSTANCE.getCompanyPage(NUMBER_OF_ELEMENTS_PER_PAGE);
                viewPage(companyPage);
                break;
            case CHECK_COMPUTER_BY_ID:
                checkComputerById();
                break;
            case CHECK_COMPUTERS_BY_NAME:
                checkComputerByName();
                break;
            case ADD_COMPUTER:
                editComputer(new Computer());
                break;
            case UPDATE_COMPUTER:
                updateComputer();
                break;
            case DELETE_COMPUTER:
                deleteComputer();
                break;
            default:
                break;
        }
    }

    private <T extends Page<U>, U extends Model> void viewPage(T page) throws ServiceException {
        boolean exit = false;
        String choice;

        while (!exit) {
            System.out.println("Which page would you like to view (current page: " + page.getPageNumber() + ")?");
            System.out.println("n for next, p for previous, f for first, l for last and q to quit");
            choice = sc.nextLine();

            if (choice.isEmpty() || choice.equals("f")) {
                page.first().forEach(c -> System.out.println(c.shortToString()));
            } else if (choice.equals("q")) {
                exit = true;
            } else if (choice.equals("p")) {
                page.previous().forEach(c -> System.out.println(c.shortToString()));
            } else if (choice.equals("n")) {
                page.next().forEach(c -> System.out.println(c.shortToString()));
            } else if (choice.equals("l")) {
                page.last().forEach(c -> System.out.println(c.shortToString()));
            } else if (choice.matches("[0-9]+")) {
                page.goToPage(Long.decode(choice)).forEach(c -> System.out.println(c.shortToString()));
            }
            System.out.println();
        }
    }

    private void checkComputerById() throws ServiceException {
        Long id;
        Computer c;

        System.out.println("Please enter the computer's ID: ");
        id = sc.nextLong();
        sc.nextLine();

        c = ComputerServiceImpl.INSTANCE.getComputer(id);

        if (c != null) {
            System.out.println(c);
        } else {
            System.out.println("No computer registered with the ID " + id);
        }
    }

    private void checkComputerByName() throws ServiceException {
        String name;

        System.out.println("Please enter the computer's name: ");
        name = sc.next();
        sc.nextLine();

        ComputerSearchPage computerSearchPage = new ComputerSearchPage(name, NUMBER_OF_ELEMENTS_PER_PAGE);
        viewPage(computerSearchPage);
    }

    private void editComputer(Computer c) throws ServiceException {
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
                Long id = ComputerServiceImpl.INSTANCE.persistComputer(c);
                System.out.println("The computer has the ID: " + id);
            } else {
                ComputerServiceImpl.INSTANCE.updateComputer(c);
            }
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateComputer() throws ServiceException {
        System.out.println("Which computer would you like to update (ID)?");
        Long id = sc.nextLong();
        sc.nextLine();
        Computer c = ComputerServiceImpl.INSTANCE.getComputer(id);
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
        boolean ok = false;

        while (!ok) {
            System.out.println("Please enter the computer's " + event + " date (yyyy-MM-dd or 'Enter' for no date):");
            String d1 = sc.nextLine();
            if (d1.isEmpty() || d1.matches("\\d{4}-\\d{2}-\\d{2}")) {
                if (!d1.isEmpty()) {
                    date = LocalDate.parse(d1, DatePattern.FORMATTER);
                }
                ok = true;
            }
        }

        return date;
    }

    private Company getCompanyId() throws ServiceException {
        Company c = null;

        while (c == null) {
            System.out.println("Please enter the computer's manufacturer ID:");
            Long companyId = sc.nextLong();
            sc.nextLine();
            c = CompanyServiceImpl.INSTANCE.getCompany(companyId);
        }

        return c;
    }

    private void deleteComputer() throws ServiceException {
        Long id;

        System.out.println("Please enter the computer's ID you wish to delete: ");
        id = sc.nextLong();
        sc.nextLine();

        Computer c = ComputerServiceImpl.INSTANCE.getComputer(id);
        if (c != null) {
            ComputerServiceImpl.INSTANCE.deleteComputer(id);
        } else {
            System.out.println("There is no computer with the ID: " + id + "\n");
        }
    }

    public static void main(String[] args) {
        CLI.INSTANCE.mainLoop();
    }
}

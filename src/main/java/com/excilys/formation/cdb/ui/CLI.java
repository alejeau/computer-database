package com.excilys.formation.cdb.ui;

import com.excilys.formation.cdb.exceptions.ServiceException;
import com.excilys.formation.cdb.exceptions.ValidationException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.DatePattern;
import com.excilys.formation.cdb.model.Model;
import com.excilys.formation.cdb.paginator.pager.CompanyPage;
import com.excilys.formation.cdb.paginator.pager.ComputerPage;
import com.excilys.formation.cdb.paginator.pager.ComputerSearchPage;
import com.excilys.formation.cdb.paginator.core.LimitValue;
import com.excilys.formation.cdb.paginator.core.Page;
import com.excilys.formation.cdb.paginator.pager.PageFactory;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Scanner;

@Controller
public class CLI {
    private static final Logger LOG = LoggerFactory.getLogger(CLI.class);
    private static final LimitValue NUMBER_OF_ELEMENTS_PER_PAGE = LimitValue.TEN;

    private CompanyService companyService;
    private ComputerService computerService;
    private PageFactory pageFactory;

    private Scanner sc = new Scanner(System.in);

    @Autowired
    public CLI(CompanyService companyService, ComputerService computerService, PageFactory pageFactory) {
        this.companyService = companyService;
        this.computerService = computerService;
        this.pageFactory = pageFactory;
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
        System.out.println(CliActions.DELETE_COMPANY.getValue());
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
        while (code != CliActions.values().length);
    }

    private void executeChoice(CliActions choice) throws ServiceException {
        switch (choice) {
            case VIEW_COMPUTER_LIST:
                ComputerPage computerPage = pageFactory.createComputerPage(NUMBER_OF_ELEMENTS_PER_PAGE);
                viewPage(computerPage);
                break;
            case VIEW_COMPANY_LIST:
                CompanyPage companyPage = pageFactory.createCompanyPage(NUMBER_OF_ELEMENTS_PER_PAGE);
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
            case DELETE_COMPANY:
                deleteCompany();
                break;
            default:
                break;
        }
    }

    private <T extends Page<U>, U extends Model> void viewPage(T page) throws ServiceException {
        boolean exit = false;
        String choice;

        while (!exit) {
            System.out.println("Which list would you like to view (current list: " + page.getPageNumber() + ")?");
            System.out.println("n for next, p for previous, f for first, l for last and q to quit");
            choice = sc.nextLine();

            if (choice.isEmpty() || choice.equals("f")) {
                page.first()
                        .stream()
                        .map(Model::shortToString)
                        .forEach(System.out::println);
            } else if (choice.equals("q")) {
                exit = true;
            } else if (choice.equals("p")) {
                page.previous()
                        .stream()
                        .map(Model::shortToString)
                        .forEach(System.out::println);
            } else if (choice.equals("n")) {
                page.next()
                        .stream()
                        .map(Model::shortToString)
                        .forEach(System.out::println);
            } else if (choice.equals("l")) {
                page.last()
                        .stream()
                        .map(Model::shortToString)
                        .forEach(System.out::println);
            } else if (choice.matches("[0-9]+")) {
                page.goToPage(Long.decode(choice))
                        .stream()
                        .map(Model::shortToString)
                        .forEach(System.out::println);
            }
            System.out.println();
        }
    }

    private void checkComputerById() throws ServiceException {
        Computer c;
        Long id = getLong("Please enter the computer's ID: ");

        c = computerService.getComputer(id);

        if (c != null) {
            System.out.println(c);
        } else {
            System.out.println("No computer registered with the ID " + id);
        }
    }

    private void checkComputerByName() throws ServiceException {
        String name;

        System.out.println("Please enter the computer's name: ");
        name = sc.nextLine();

        ComputerSearchPage computerSearchPage = pageFactory.createComputerSearchPage(name, NUMBER_OF_ELEMENTS_PER_PAGE);
        viewPage(computerSearchPage);
    }

    private void editComputer(Computer c) throws ServiceException {
        String name;
        LocalDate introduced;
        LocalDate discontinued;
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
                Long id = computerService.persistComputer(c);
                System.out.println("The computer has the ID: " + id);
            } else {
                computerService.updateComputer(c);
            }
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateComputer() throws ServiceException {
        Long id = getLong("Which computer would you like to update (ID)?");

        Computer c = computerService.getComputer(id);
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
            System.out.println();
            Long companyId = getLong("Please enter the computer's manufacturer ID:");
            c = companyService.getCompany(companyId);
        }

        return c;
    }

    private void deleteComputer() throws ServiceException {
        Long id = getLong("Please enter the computer's ID you wish to delete: ");

        Computer c = computerService.getComputer(id);
        if (c != null) {
            computerService.deleteComputer(id);
        } else {
            System.out.println("There is no computer with the ID: " + id + "\n");
        }
    }

    private void deleteCompany() throws ServiceException {
        Long id = getLong("Please enter the company's ID you wish to delete: ");

        Company c = companyService.getCompany(id);
        if (c != null) {
            companyService.deleteCompany(id);
        } else {
            System.out.println("There is no company with the ID: " + id + "\n");
        }
    }

    private Long getLong(final String message) {
        Long id;
        System.out.println(message);
        id = sc.nextLong();
        sc.nextLine();
        return id;
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/contexts/cli-context.xml");
        CLI cli = context.getBean(CLI.class);
        cli.mainLoop();
        context.close();
    }
}

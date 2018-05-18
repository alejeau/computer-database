package com.excilys.formation.cdb.ui;

import com.excilys.formation.cdb.config.HibernatePersistenceConfigCLI;
import com.excilys.formation.cdb.dto.ModelDTO;
import com.excilys.formation.cdb.dto.model.CompanyDTO;
import com.excilys.formation.cdb.dto.model.ComputerDTO;
import com.excilys.formation.cdb.dto.paginator.PageDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Scanner;

@Controller
public class CLI {
    private static final String BASE_REST_URL = "http://localhost:8080/computer-database-webservice";
    private static final String COMPUTER_REST_URL = "/computer";
    private static final String COMPANY_REST_URL = "/company";

    private static final long FIRST_PAGE = 0L;
    private static final long NUMBER_OF_ELEMENTS_PER_PAGE = 10L;


    private Client client = ClientBuilder.newClient();
    private Scanner sc = new Scanner(System.in);


    public CLI() {
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HibernatePersistenceConfigCLI.class);
        CLI cli = context.getBean(CLI.class);
        cli.mainLoop();
        context.close();
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
            executeChoice(CliActions.map(code));
            System.out.println();
        }
        while (code != CliActions.values().length);
    }

    private void executeChoice(CliActions choice) {
        switch (choice) {
            case VIEW_COMPUTER_LIST:
                Long numberOfComputer = client.target(BASE_REST_URL)
                        .path(COMPUTER_REST_URL)
                        .path("/total")
                        .request(MediaType.APPLICATION_JSON)
                        .get(Long.class);
                Long maxPageNumberComputer = lastPageNumber(numberOfComputer, NUMBER_OF_ELEMENTS_PER_PAGE);
                PageDTO<ComputerDTO> computerDTOPage = new PageDTO<>();
                computerDTOPage.setCurrentPageNumber(FIRST_PAGE);
                computerDTOPage.setObjectsPerPage(NUMBER_OF_ELEMENTS_PER_PAGE);
                computerDTOPage.setMaxPageNumber(maxPageNumberComputer);
                viewPage(computerDTOPage, ComputerDTO.class, new GenericType<List<ComputerDTO>>() {
                }, null);
                break;
            case VIEW_COMPANY_LIST:
                Long numberOfCompany = client.target(BASE_REST_URL)
                        .path(COMPANY_REST_URL)
                        .path("/total")
                        .request(MediaType.APPLICATION_JSON)
                        .get(Long.class);
                Long maxPageNumberCompany = lastPageNumber(numberOfCompany, NUMBER_OF_ELEMENTS_PER_PAGE);
                PageDTO<CompanyDTO> companyDTOPage = new PageDTO<>();
                companyDTOPage.setCurrentPageNumber(FIRST_PAGE);
                companyDTOPage.setObjectsPerPage(NUMBER_OF_ELEMENTS_PER_PAGE);
                companyDTOPage.setMaxPageNumber(maxPageNumberCompany);
                viewPage(companyDTOPage, CompanyDTO.class, new GenericType<List<CompanyDTO>>() {
                }, null);
                break;
            case CHECK_COMPUTER_BY_ID:
                checkComputerById();
                break;
            case CHECK_COMPUTERS_BY_NAME:
                checkComputerByName();
                break;
            case ADD_COMPUTER:
                editComputer(new ComputerDTO());
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

    private <T extends PageDTO<U>, U extends ModelDTO> void viewPage(
            T page,
            Class itemClass,
            GenericType<List<U>> genericType,
            String search
    ) {
        boolean exit = false;
        String choice;

        while (!exit) {
            System.out.println("Which list would you like to view (current page: " + page.getCurrentPageNumber() + ")?");
            System.out.println("n for next, p for previous, f for first, l for last and q to quit");
            choice = sc.nextLine();

            if (choice.isEmpty() || choice.equals("f")) {
                page = PageDtoValidator.first(page);
            } else if (choice.equals("q")) {
                exit = true;
            } else if (choice.equals("p")) {
                page = PageDtoValidator.previous(page);
            } else if (choice.equals("n")) {
                page = PageDtoValidator.next(page);
            } else if (choice.equals("l")) {
                page = PageDtoValidator.last(page);
            } else if (choice.matches("[0-9]+")) {
                page = PageDtoValidator.goTo(page, Long.decode(choice));
            }

            getList(page, itemClass, genericType, search);
            System.out.println();
        }
    }

    private <T extends PageDTO<U>, U extends ModelDTO> void getList(
            T page,
            Class itemClass,
            GenericType<List<U>> genericType,
            String search
    ) {
        String target;
        System.out.println(itemClass);
        if (itemClass.equals(ComputerDTO.class)) {
            target = COMPUTER_REST_URL;
        } else {
            target = COMPANY_REST_URL;
        }

        System.out.println("Target: " + target);

        Response response;
        if (search != null) {
            response = client.target(BASE_REST_URL)
                    .path(target)
                    .path("/name/" + search)
                    .path("/index/" + page.getCurrentPageNumber() * page.getObjectsPerPage())
                    .path("/limit/" + page.getObjectsPerPage())
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);

        } else {
            response = client.target(BASE_REST_URL)
                    .path(target)
                    .path("/index/" + page.getCurrentPageNumber() * page.getObjectsPerPage())
                    .path("/limit/" + page.getObjectsPerPage())
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);

        }

        response.readEntity(genericType)
                .stream()
                .map(ModelDTO::shortToString)
                .forEach(System.out::println);
    }

    private void checkComputerById() {
        ComputerDTO computerDTO;
        Long id = getLong("Please enter the computer's ID: ");

        final String TARGET = String.format("/id/%d", id);
        computerDTO = client.target(BASE_REST_URL)
                .path(COMPUTER_REST_URL)
                .path(TARGET)
                .request(MediaType.APPLICATION_JSON)
                .get(ComputerDTO.class);

        if (computerDTO != null) {
            System.out.println(computerDTO);
        } else {
            System.out.println("No computer registered with the ID " + id);
        }
    }

    private void checkComputerByName() {
        String name;

        System.out.println("Please enter the computer's name: ");
        name = sc.nextLine();

        final String TARGET = String.format("/name/%s/total", name);

        Long numberOfComputer = client.target(BASE_REST_URL)
                .path(COMPUTER_REST_URL)
                .path(TARGET)
                .request(MediaType.APPLICATION_JSON)
                .get(Long.class);
        Long maxPageNumberComputer = lastPageNumber(numberOfComputer, NUMBER_OF_ELEMENTS_PER_PAGE);
        PageDTO<ComputerDTO> computerDTOPage = new PageDTO<>();
        computerDTOPage.setCurrentPageNumber(FIRST_PAGE);
        computerDTOPage.setObjectsPerPage(NUMBER_OF_ELEMENTS_PER_PAGE);
        computerDTOPage.setMaxPageNumber(maxPageNumberComputer);
        viewPage(computerDTOPage, ComputerDTO.class, new GenericType<List<ComputerDTO>>() {
        }, name);
    }

    private void editComputer(ComputerDTO computerDTO) {
        if (computerDTO.getId() != ComputerDTO.NO_ID) {
            System.out.println("Computer to update:");
            System.out.println(computerDTO);
        }

        String name;
        String introduced;
        String discontinued;
        CompanyDTO companyDTO;

        System.out.println("Please enter the computer's name:");
        name = sc.nextLine();

        introduced = getDate("introduction");
        discontinued = getDate("discontinuation");

        companyDTO = getCompanyId(true);

        computerDTO.setName(name);
        computerDTO.setIntroduced(introduced);
        computerDTO.setDiscontinued(discontinued);
        computerDTO.setCompanyId(companyDTO != null ? companyDTO.getId() : null);

        if (computerDTO.getId() == ComputerDTO.NO_ID) {
            Long id = client.target(BASE_REST_URL)
                    .path(COMPUTER_REST_URL)
                    .request()
                    .post(Entity.entity(computerDTO, MediaType.APPLICATION_JSON))
                    .readEntity(Long.class);

            System.out.println("The computer has the ID: " + id);
        } else {
            client.target(BASE_REST_URL)
                    .path(COMPUTER_REST_URL)
                    .request()
                    .put(Entity.entity(computerDTO, MediaType.APPLICATION_JSON));
        }

    }

    private void updateComputer() {
        Long id = getLong("Which computer would you like to update (ID)?");


        final String TARGET = String.format("/id/%d", id);
        ComputerDTO computerDTO = client.target(BASE_REST_URL)
                .path(COMPUTER_REST_URL)
                .path(TARGET)
                .request(MediaType.APPLICATION_JSON)
                .get(ComputerDTO.class);
        editComputer(computerDTO);
    }

    /**
     * Asks the user for a date with the pattern "yyyy-MM-dd", or nothing if 'Enter'is hit.
     *
     * @param event the name of the event you want a date for.
     * @return a LocalDate corresponding to the input user's date.
     */
    private String getDate(String event) {
        String d1 = null;
        boolean ok = false;

        while (!ok) {
            System.out.println("Please enter the computer's " + event + " date (yyyy-MM-dd or 'Enter' for no date):");
            d1 = sc.nextLine();
            if (d1.isEmpty() || d1.matches("\\d{4}-\\d{2}-\\d{2}")) {
                ok = true;
            }
        }

        return d1;
    }

    private CompanyDTO getCompanyId(boolean optional) {
        CompanyDTO companyDTO = null;

        do {
            System.out.println();
            Long companyId = getLong("Please enter the computer's manufacturer ID:");
            if (optional && companyId == null) {
                return null;
            }

            final String TARGET = String.format("/id/%d", companyId);
            companyDTO = client.target(BASE_REST_URL)
                    .path(COMPANY_REST_URL)
                    .path(TARGET)
                    .request(MediaType.APPLICATION_JSON)
                    .get(CompanyDTO.class);
        } while (companyDTO == null);

        return companyDTO;
    }

    private void deleteComputer() {
        Long id = getLong("Please enter the computer's ID you wish to delete: ");

        final String TARGET = String.format("/id/%d", id);
        ComputerDTO computerDTO = client.target(BASE_REST_URL)
                .path(COMPUTER_REST_URL)
                .path(TARGET)
                .request(MediaType.APPLICATION_JSON)
                .get(ComputerDTO.class);

        if (computerDTO != null) {
            client.target(BASE_REST_URL)
                    .path(COMPUTER_REST_URL)
                    .path(TARGET)
                    .request()
                    .delete();
        } else {
            System.out.println("There is no computer with the ID: " + id + "\n");
        }
    }

    private void deleteCompany() {
        Long companyId = getLong("Please enter the company's ID you wish to delete: ");

        final String COMPUTER_TARGET = String.format("/company/id/%d", companyId);
        final String COMPANY_TARGET = String.format("/id/%d", companyId);
        client.target(BASE_REST_URL)
                .path(COMPUTER_REST_URL)
                .path(COMPUTER_TARGET)
                .request()
                .delete();

        client.target(BASE_REST_URL)
                .path(COMPANY_REST_URL)
                .path(COMPANY_TARGET)
                .request()
                .delete();
    }

    private Long getLong(final String message) {
        Long id;
        System.out.println(message);
        id = sc.nextLong();
        sc.nextLine();
        return id;
    }

    private static Long lastPageNumber(Long numberOfItem, Long limit) {
        Long lastPageNumber = numberOfItem / limit;
        return (numberOfItem % 10 == 0) && (numberOfItem != 0L) ? lastPageNumber - 1L : lastPageNumber;
    }
}

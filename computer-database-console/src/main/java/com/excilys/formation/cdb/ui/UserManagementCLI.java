package com.excilys.formation.cdb.ui;

import com.excilys.formation.cdb.login.User;
import com.excilys.formation.cdb.login.UserRole;
import com.excilys.formation.cdb.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.Console;
import java.util.Arrays;
import java.util.Scanner;

@Controller
public class UserManagementCLI {
    private static final Logger LOG = LoggerFactory.getLogger(UserManagementCLI.class);

    private UserService userService;
    private Scanner sc = new Scanner(System.in);

    @Autowired
    public UserManagementCLI(UserService userService) {
        this.userService = userService;
    }

    public void mainLoop() {
        int code;
        do {
            code = mainMenu();
            executeChoice(UserManagementActions.map(code));
            System.out.println();
        }
        while (code != CliActions.values().length);
    }

    private int mainMenu() {
        System.out.println("What would you like to do?");
        System.out.println(UserManagementActions.CHECK_USER.getValue());
        System.out.println(UserManagementActions.ADD_USER.getValue());
        System.out.println(UserManagementActions.UPDATE_USER.getValue());
        System.out.println(UserManagementActions.DELETE_USER.getValue());
        System.out.println(UserManagementActions.EXIT.getValue());

        System.out.println("Enter your choice: ");
        int entry = sc.nextInt();
        sc.nextLine();

        return entry;
    }

    private void executeChoice(UserManagementActions choice) {
        switch (choice) {
            case CHECK_USER:
                checkUserByName();
                break;
            case ADD_USER:
                addUser();
                break;
            case UPDATE_USER:
                updateUser();
                break;
            case DELETE_USER:
                deleteUser();
                break;
            default:
                break;
        }
    }

    private void checkUserByName() {
        User user;
        String username = getUserName();
        user = userService.getUserWithName(username);

        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println(String.format("No user registered with the name %s", username));
        }
    }

    private void addUser() {
        String username = getUserName();
        String password = getEncodedPassword();
        UserRole role = getRole();
        userService.createUser(new User(username, password, role));
    }

    private void updateUser() {
        String username = getUserName();
        User user = userService.getUserWithName(username);
        if (user == null) {
            System.out.println(String.format("No users registered with the name %s", username));
            return;
        }
        user.setUsername(getEncodedPassword());
        user.setRole(getRole());
        userService.updateUser(user);
    }

    private void deleteUser() {
        userService.deleteUser(getUserName());
    }

    private String getUserName() {
        String string;
        do {
            System.out.println("Please enter the user's name: ");
            string = sc.nextLine();
            string = string.trim();
        } while (StringUtils.isBlank(string));

        return string;
    }

    private String getEncodedPassword() {
        String password;
        char pass1[], pass2[];
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        do {
            pass1 = console.readPassword("Please enter the user's new password:  ");
            pass2 = console.readPassword("Please retype the user's new password: ");

            if (!Arrays.equals(pass1, pass2)) {
                System.out.println("Passwords didn't match!");
            }
        } while (!Arrays.equals(pass1, pass2) || pass1.length == 0);

        password = new String(pass1);
        pass1 = null;
        pass2 = null;
        return encodePassword(password);
    }

    private boolean verifyPassword(User user) {
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }
        String currentPassword = encodePassword(
                new String(console.readPassword("Please enter the user's current password: "))
        );
        return user.getPassword().equals(currentPassword);
    }

    private UserRole getRole() {
        System.out.println("Enter the user's role (1: ADMIN, 2: USER):");
        Integer role;
        do {
            role = sc.nextInt();
            sc.nextLine();
        } while (role < 0 && role > 2);

        return role == 1 ? UserRole.ADMIN : UserRole.USER;
    }

    private String encodePassword(String password) {
        //TODO: encode password
        return password;
    }
}

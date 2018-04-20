package com.excilys.formation.cdb.controllers.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Paths {
    private static final Logger LOG = LoggerFactory.getLogger(Paths.class);
    private static Properties properties = new Properties();
    private static final String PROPERTIES_FILE = "/properties/url.properties";

    static {
        try {
            properties.load(Paths.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            LOG.error("Couldn't properties file \"{}\"!", PROPERTIES_FILE);
            Arrays.stream(e.getStackTrace())
                    .map(StackTraceElement::toString)
                    .forEach(LOG::error);
        }
    }

    public static final String LOCAL_PATH_DASHBOARD = properties.getProperty("url.dashboard");
    public static final String LOCAL_PATH_ADD_COMPUTER = properties.getProperty("url.add-computer");
    public static final String LOCAL_PATH_EDIT_COMPUTER = properties.getProperty("url.edit-computer");
    public static final String LOCAL_PATH_SEARCH_COMPUTER = properties.getProperty("url.search-computer");

    public static final String PREFIX = properties.getProperty("url.prefix");
    public static final String ABSOLUTE_PATH_DASHBOARD = String.format("%s%s", PREFIX, LOCAL_PATH_DASHBOARD);
    public static final String ABSOLUTE_PATH_ADD_COMPUTER = String.format("%s%s", PREFIX, LOCAL_PATH_ADD_COMPUTER);
    public static final String ABSOLUTE_PATH_EDIT_COMPUTER = String.format("%s%s", PREFIX, LOCAL_PATH_EDIT_COMPUTER);
    public static final String ABSOLUTE_PATH_SEARCH_COMPUTER = String.format("%s%s", PREFIX, LOCAL_PATH_SEARCH_COMPUTER);

    private Paths() {
    }
}

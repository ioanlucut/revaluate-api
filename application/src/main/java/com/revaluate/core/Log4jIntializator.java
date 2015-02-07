package com.revaluate.core;

import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Log4jIntializator implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(Log4jIntializator.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        LOGGER.info("Log4j initializator manager started!");
        ServletContext context = event.getServletContext();
        System.setProperty("rootPath", context.getRealPath("/"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }
}
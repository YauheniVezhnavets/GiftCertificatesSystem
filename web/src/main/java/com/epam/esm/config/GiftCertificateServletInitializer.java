package com.epam.esm.config;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Properties;


public class GiftCertificateServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final String SPRING_ACTIVE_PROPERTY_FILE_PATH = "/property/spring_active.properties";
    private static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";

    @Override
    protected Class<?>[] getRootConfigClasses(){
        return new Class[] { DatabaseConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { SpringConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(SPRING_ACTIVE_PROPERTY_FILE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        servletContext.setInitParameter(SPRING_PROFILE_ACTIVE, properties.getProperty(SPRING_PROFILE_ACTIVE));
    }
}

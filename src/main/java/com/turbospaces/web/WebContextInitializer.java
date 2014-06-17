package com.turbospaces.web;

import java.io.IOException;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.google.common.base.Throwables;

public class WebContextInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {
    @Override
    public void initialize(ConfigurableWebApplicationContext applicationContext) {
        MutablePropertySources ps = applicationContext.getEnvironment().getPropertySources();
        try {
            ps.addFirst( new ResourcePropertySource( "classpath:version.properties" ) );
            ps.addFirst( new ResourcePropertySource( "classpath:application.properties" ) );
        }
        catch ( IOException e ) {
            Throwables.propagate( e );
        }
    }
}

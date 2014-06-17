package com.turbospaces.mvc;

import java.util.UUID;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MvcController implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @RequestMapping("/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView( "index" );
        Environment env = applicationContext.getEnvironment();

        mv.addObject( "bootstrapVersion", env.getRequiredProperty( "bootstrap.version" ) );
        mv.addObject( "sockjsVersion", env.getRequiredProperty( "sockjs.version" ) );
        mv.addObject( "jqueryVersion", env.getRequiredProperty( "jquery.version" ) );
        mv.addObject( "wsPath", env.getRequiredProperty( "ws.path" ) );
        mv.addObject( "uuid", UUID.randomUUID().toString() );

        return mv;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

package com.eatOut.config.spring;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

			
    @RequestMapping("/")
    public String viewHomePage() {
        return "home";
    }
}
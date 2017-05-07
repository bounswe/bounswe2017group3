package com.bounswe.bounswe2017group3.Controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {
    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    Map<String, String> home() {
        return new HashMap<String, String>() {
            private static final long serialVersionUID = 7475539268535723836L;

            {
                put("version", "1.0.0");
                put("git", "https://github.com/bounswe/bounswe2017group3.git");
                put("baseUrl", "https://bounswe2017group3.herokuapp.com/");
            }
        };
    }
}

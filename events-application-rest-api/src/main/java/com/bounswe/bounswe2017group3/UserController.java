/*
 * Copyright 2015 Benedikt Ritter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bounswe.bounswe2017group3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "",
            params="username",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody User userByUsername(@RequestParam("username") String username){
        
        return repository.findByUsername(username);
    }
    
    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<User> user(ModelMap model) {
        return repository.findAll();
    }
     
    @RequestMapping(method=RequestMethod.PUT, value="{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
     public @ResponseBody User update(@PathVariable long id, ModelMap model,
        @ModelAttribute("updateUser") @Valid User user,
        BindingResult result) {
        if (!result.hasErrors()) {
            User update = repository.findById(id);
            update.setEmail(user.getEmail());
            update.setFullname(user.getFullname());
            update.setUsername(user.getUsername());
            return repository.save(update); 
        }
        return user;
     }
    
    @RequestMapping(method=RequestMethod.PUT,
            value="",
            params="username",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody User updateByUsername(@RequestParam MultiValueMap<String, String> params){
        
        String oldUsername = params.get("username").get(0);
        String newUsername = params.get("username").get(1);
        User updatedUser = repository.findByUsername(oldUsername);
        updatedUser.setEmail(params.get("email").get(0));
        updatedUser.setFullname(params.get("fullname").get(0));
        updatedUser.setUsername(newUsername);
        repository.save(updatedUser);
        return updatedUser;

    }
    
   

    @RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody User insertData(ModelMap model,
        @ModelAttribute("insertUser") @Valid User user,
        BindingResult result) {
        if (!result.hasErrors()) {
            repository.save(user);
        }
        // TODO: Show an error when the result has errors.
        return user;
    }
}

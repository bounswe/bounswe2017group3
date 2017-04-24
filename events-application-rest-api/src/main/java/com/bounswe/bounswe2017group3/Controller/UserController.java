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
package com.bounswe.bounswe2017group3.Controller;

import com.bounswe.bounswe2017group3.Exception.CustomException;
import com.bounswe.bounswe2017group3.ErrorResponse;
import com.bounswe.bounswe2017group3.Model.User;
import com.bounswe.bounswe2017group3.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public @ResponseBody
    User userByUsername(@RequestParam("username") String username){

        return repository.findByUsername(username);
    }

    @RequestMapping(method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody List<User> user(ModelMap model) {
        return repository.findAll();
    }


    @RequestMapping(method=RequestMethod.PUT, value="/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
      public @ResponseBody User update(@PathVariable("id") long id,
                                       @RequestParam MultiValueMap<String, String> params) {
        User update = repository.findById(id);

        String newUsername = params.get("username").get(0);
        String newEmail = params.get("email").get(0);
        String newFullname = params.get("fullname").get(0);

        int count = repository.numberOfUsersWithEmail(newEmail);

        if (count == 0 || newEmail.equalsIgnoreCase(update.getEmail())) {
            update.setEmail(newEmail);
        } else {
            throw new CustomException(1001, "Given email address belongs to someone else.");
        }
        update.setFullname(newFullname);
        count = repository.numberOfUsersWithUsername(newUsername);
        if (count == 0 || newUsername.equalsIgnoreCase(update.getUsername())) {

            update.setUsername(newUsername);
        } else {
            throw new CustomException(1002, "Given username belongs to someone else.");
        }

        return repository.save(update);
     }

    @RequestMapping(method=RequestMethod.PUT,
            value="",
            params="username",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody User updateByUsername(@RequestParam MultiValueMap<String, String> params){
        String oldUsername = params.get("username").get(0);
        String newUsername = params.get("username").get(1);
        String newEmail = params.get("email").get(0);
        User updatedUser = repository.findByUsername(oldUsername);

        if (repository.numberOfUsersWithEmail(newEmail) == 0 ||
            newEmail.equalsIgnoreCase(updatedUser.getEmail())) {
            updatedUser.setEmail(newEmail);
        } else {
            throw new CustomException(1001, "Given email address belongs to someone else.");
        }
        updatedUser.setFullname(params.get("fullname").get(0));
        if (repository.numberOfUsersWithUsername(newUsername) == 0 ||
            newUsername.equalsIgnoreCase(oldUsername)) {
            updatedUser.setUsername(newUsername);
        } else {
            throw new CustomException(1002, "Given username belongs to someone else.");
        }

        return repository.save(updatedUser);

    }

    @RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody User insertData(ModelMap model,
                                         @ModelAttribute("insertUser") @Valid User user,
                                         BindingResult result) {
        if (!result.hasErrors()) {
            repository.save(user);
        }
        return user;
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomException ex) {
            ErrorResponse error = new ErrorResponse();
            error.setErrorCode(ex.getErrorCode());
            error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.valueOf(ex.getErrorCode()));
    }
}
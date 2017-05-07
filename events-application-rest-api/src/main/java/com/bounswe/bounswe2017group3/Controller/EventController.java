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

import com.bounswe.bounswe2017group3.ErrorResponse;
import com.bounswe.bounswe2017group3.Exception.CustomException;
import com.bounswe.bounswe2017group3.Model.Event;
import com.bounswe.bounswe2017group3.Repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/event")
public class EventController {

    private EventRepository repository;

    @Autowired
    public EventController(EventRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "",
        params = "name",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    Event eventByName(@RequestParam("name") String name) {

        return repository.findByName(name);
    }

    //List events with respect to privacy option
    @RequestMapping(method = RequestMethod.GET, value = "",
        params = "privacy",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    List<Event> eventByPrivacy(@RequestParam("privacy") Boolean privacy) {

        return repository.findByPrivacy(privacy);
    }

    //Post code is revised.
    @RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    Event insertData(ModelMap model,
                     @ModelAttribute("insertEvent") @Valid Event event,
                     BindingResult result) {

        if (!result.hasErrors()) {
            repository.save(event);
        }

        return event;

    }

    //Delete method is implemented to delete an event.
    @RequestMapping(method = RequestMethod.DELETE, params = "id", produces =
        MediaType.APPLICATION_JSON_UTF8_VALUE)
    public
    @ResponseBody
    ResponseEntity<Void> deleteEvent(@RequestParam("id") long id) {

        Event update = repository.findById(id);

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        update.setDeletedAt(date);
        repository.save(update);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        //error.setErrorCode(101);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }
}
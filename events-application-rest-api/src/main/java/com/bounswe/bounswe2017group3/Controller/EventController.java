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
import com.bounswe.bounswe2017group3.Model.Event;
import com.bounswe.bounswe2017group3.Model.User;
import com.bounswe.bounswe2017group3.Repository.EventRepository;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

@Controller
@RequestMapping("/event")
public class EventController {

    private static final Date NULL = null;
	private EventRepository repository;

    @Autowired
    public EventController(EventRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "",
            params="name",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Event eventByName(@RequestParam("name") String name){

        return repository.findByName(name);
    }
    
    //List events with respect to privacy option
    @RequestMapping(method = RequestMethod.GET, value = "",
            params="privacy",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    List<Event> eventByPrivacy(@RequestParam("privacy") Boolean privacy){

        return repository.findByPrivacy(privacy);
    }

    //Post code is revised.
    @RequestMapping(method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody Event insertData(ModelMap model,
                                          @ModelAttribute("insertEvent") @Valid Event event,
                                          BindingResult result) {

        if(!result.hasErrors()){
            repository.save(event);
        }

        return event;

    }
    
    @RequestMapping(method=RequestMethod.DELETE, params="id", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody void deleteEvent(@RequestParam("id") long id,
                                     @RequestParam MultiValueMap<String, String> params) {
    	
      Event update = repository.findById(id);

      Calendar cal = Calendar.getInstance();
      Date date = cal.getTime();
      update.setDeletedAt(date);
      repository.save(update);
   }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        //error.setErrorCode(101);
        error.setMessage(ex.getMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);
    }
}
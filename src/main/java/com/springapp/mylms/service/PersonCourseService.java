package com.springapp.mylms.service;

import com.springapp.mylms.repository.PersonCourseRepository;
import com.springapp.mylms.entity.CourseEntity;
import com.springapp.mylms.entity.PersonCourseEntity;
import com.springapp.mylms.modal.PersonCourseObject;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by bezdj on 04/02/2017.
 */

@Service
public class PersonCourseService {

    private final PersonCourseRepository personCourseRepository;

    public PersonCourseService(
            PersonCourseRepository personCourseRepository) {
        this.personCourseRepository = personCourseRepository;
    }

    public void insertPersonCourse(PersonCourseEntity personCourseEntity) {
        personCourseRepository.save(personCourseEntity);
    }

    public CourseEntity getPersonCourseByPersonId(Long id) {
        //TODO: map to course entity
        //return personCourseRepository.findPersonCourseEntitiesByPersonId(id);
        return null;
    }

    public List<PersonCourseObject> getAllPersonCourses() {
        // return personCourseRepository.findAll();
        return Collections.emptyList();
    }

    public void enrolUserToCourse(int courseid, int userid) {
        // personCourseRepository.enrolUserToCourse(courseid, userid);
    }
}

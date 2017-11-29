package com.springapp.mvc.service;

import com.springapp.mvc.dao.PersonCourseDao;
import com.springapp.mvc.domain.CourseEntity;
import com.springapp.mvc.domain.PersonCourseEntity;
import com.springapp.mvc.domain.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bezdj on 04/02/2017.
 */

@Service
public class PersonCourseServiceImpl implements PersonCourseService {

    @Autowired
    private PersonCourseDao personCourseDao;

    @Override
    public void insertPersonCourse(PersonCourseEntity personCourseEntity) {
        personCourseDao.insertPersonCourse(personCourseEntity);
    }

    @Override
    public CourseEntity getPersonCourseByPersonId(int id) {
        return personCourseDao.getPersonCourseByPersonId(id);
    }

    @Override
    public List<CourseEntity> getAllPersonCourses(int personid) {
        return personCourseDao.getAllPersonCourses(personid);
    }

    @Override
    public void enrolUserToCourse(int courseid, int userid) {
        personCourseDao.enrolUserToCourse(courseid, userid);
    }
}

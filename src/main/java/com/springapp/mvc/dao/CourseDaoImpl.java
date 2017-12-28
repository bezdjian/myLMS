package com.springapp.mvc.dao;

import com.springapp.mvc.domain.CourseEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by bezdj on 15/08/2017.
 */

@Repository
public class CourseDaoImpl implements CourseDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    @Transactional
    public void insertCourse(CourseEntity course) {
        sessionFactory.getCurrentSession().saveOrUpdate(course);
    }

    @Override
    public List<CourseEntity> getAllCourses() {
        String sql = "SELECT * FROM course";
        List<CourseEntity> course = namedParameterJdbcTemplate.query(sql,
                (rs, rowNum)-> new CourseEntity(rs.getInt("id"), rs.getString("coursename"), rs.getString("description"),
                        rs.getString("idnumber"), rs.getInt("categoryid"), rs.getString("courseimage")));
        return course.size() > 0 ? course : null;
    }

    @Transactional
    @Override
    public CourseEntity getCourseById(int id){
        String sql = "SELECT * FROM course WHERE id = " + id;

        List<CourseEntity> course = namedParameterJdbcTemplate.query(sql,
                (rs, rowNum)-> new CourseEntity(rs.getInt("id"), rs.getString("coursename"), rs.getString("description"),
                        rs.getString("idnumber"), rs.getInt("categoryid"), rs.getString("courseimage")));
        return course.size() > 0 ? course.get(0) : new CourseEntity();
    }


    @Override
    public void removeCourse(int courseid) {
        String sql = "DELETE FROM course WHERE id = :courseId";
        SqlParameterSource params = new MapSqlParameterSource("courseId", courseid);
        namedParameterJdbcTemplate.update(sql, params);
    }
}


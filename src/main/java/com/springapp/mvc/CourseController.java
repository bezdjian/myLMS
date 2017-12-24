package com.springapp.mvc;

import com.springapp.mvc.dao.CourseDao;
import com.springapp.mvc.dao.PersonCourseDao;
import com.springapp.mvc.dao.PersonDao;
import com.springapp.mvc.domain.CourseEntity;
import com.springapp.mvc.domain.PersonEntity;
import com.springapp.mvc.domain.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@Scope("session")
@RequestMapping("/")
public class CourseController {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private PersonCourseDao personCourseDao;

	@Autowired
	private PersonDao personDao;

	@RequestMapping("/allcourses")
	public String allCourses(HttpServletRequest request, Model m){
		m.addAttribute("person", request.getSession().getAttribute("person"));
		m.addAttribute("course", courseDao.getAllCourses());
		return "allcourses";
	}

	@RequestMapping(value = "/viewpersoncourses/{personid}", method = RequestMethod.GET)
	public String viewPersonCourses(@PathVariable("personid") int personid, Model m, HttpServletRequest request){
		// Send Strings from .properties maybe?
		m.addAttribute("person", request.getSession().getAttribute("person"));
		m.addAttribute("personcourses", personCourseDao.getAllPersonCourses(personid));
		return "viewpersoncourses";
	}

	@RequestMapping(value = "/view_editcourse/{courseid}/{action}", method = RequestMethod.GET)
	public String viewEditCourse(Model m, HttpServletRequest request,
								 @PathVariable("courseid") int courseid,
								 @PathVariable("action") String action){
		boolean isView = false;
		if(action.equals("view")){
			isView = true;
		}

		m.addAttribute("view", isView);
		m.addAttribute("person", request.getSession().getAttribute("person"));
		m.addAttribute("course", courseDao.getCourseById(courseid));
		return "view_editcourse";
	}

	@RequestMapping("/editcourse/{courseid}")
	public String editCourse(@PathVariable("courseid") int courseid,
							 @RequestParam("course_image") MultipartFile image,
							 Model m, HttpServletRequest request){

		m.addAttribute("person", request.getSession().getAttribute("person"));
		CourseEntity course = new CourseEntity();

		//Course image
		String orgName;
		boolean imageUpdated = false;
		if (!image.isEmpty()) {
			imageUpdated = true;
			try {
				String uploadsDir = File.separator + "resources" + File.separator + "uploads";
				String realPathtoUploads = request.getServletContext().getRealPath(uploadsDir);
				if (!new File(realPathtoUploads).exists()) {
					new File(realPathtoUploads).mkdir();
				}

				orgName = image.getOriginalFilename();
				String filePath = realPathtoUploads + File.separator + orgName;
				image.transferTo(new File(filePath));

				System.out.println("filePath:--------------------- " + filePath);
				course.setCourseimage(orgName); // orgName
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		if(courseid > 0){ //updating
			CourseEntity currentCourse = courseDao.getCourseById(courseid);
			course.setId(courseid);
			if(!imageUpdated){
				course.setCourseimage(currentCourse.getCourseimage()); //For now we are not updating the image.
			}
		}

		course.setCoursename(request.getParameter("course_name"));
		course.setDescription(request.getParameter("course_description"));
		course.setIdnumber(request.getParameter("course_idnumber"));
		course.setCategoryid(Integer.parseInt(request.getParameter("course_category")));

		courseDao.insertCourse(course);
		return "redirect:/allcourses";
	}

	@RequestMapping(value = "enroltocourse/{courseid}/{userid}")
	public String enrolToCourse(@PathVariable("courseid") int courseid, @PathVariable(value = "userid") String userid,
								Model m, HttpServletRequest request){
		//If userid is 'enrol', means go to enroltocourse page to choose user.
		if(userid.contains("enrol")){
			//Send list of users who are enrolled and NOT already enrolled to this course.
			List<PersonEntity> allUnerolledUsers = personDao.getAllUnerolledUsers(courseid);
			List<PersonEntity> allEnrolledUsers = personDao.getAllEnrolledUsers(courseid);
			CourseEntity course = courseDao.getCourseById(courseid);
			m.addAttribute("allUnerolledUsers", allUnerolledUsers);
			m.addAttribute("allEnrolledUsers", allEnrolledUsers);
			m.addAttribute("course", course);
			m.addAttribute("person", request.getSession().getAttribute("person"));
			return "enroltocourse";
		}else{
			//Enrol user to the course.
			personCourseDao.enrolUserToCourse(courseid, Integer.parseInt(userid));
			//return "redirect:/view_editcourse/"+courseid+"/view";
			return "redirect:/enroltocourse/"+courseid+"/enrol";
		}

	}


	@RequestMapping(value = "/delete/course/{courseid}")
	public String removeCourse(@PathVariable("courseid") int courseid){
		courseDao.removeCourse(courseid);
		return "redirect:/allcourses";
	}
}
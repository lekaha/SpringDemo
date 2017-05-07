package com.lekaha.test.spring.web;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;

import com.lekaha.test.spring.HelloWorld;
import com.lekaha.test.spring.StudentJDBCTemplate;
import com.lekaha.test.spring.dao.StudentDAO;
import com.lekaha.test.spring.model.Student;

@Controller
@RequestMapping("/")
public class WelcomeController {
	private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	
	@Autowired
	HelloWorld helloWorld;

	@Autowired
	StudentDAO studentDao;
	
//	@Autowired
//	private HttpServletRequest request;
	
	private int currentMode = Mode.LIST;
	
	@RequestMapping(method = RequestMethod.GET)
	public String welcome(ModelMap model) {
		model.addAttribute("hello", helloWorld.getMessage());
		return "index";
	}
	
	@RequestMapping(value="content", method=RequestMethod.GET)
	public String content(ModelMap model, HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
//		logger.debug("lekaha");
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		
		if (Mode.CREATE == currentMode) {
			System.out.println("Students: create");
			Student s = studentDao.getStudent(1);
			studentDao.create(s.getName() + r.nextInt(), s.getAge() + r.nextInt());
		}
		else if (Mode.DELETE == currentMode) {
			int size = studentDao.listStudents().size();
			if (size > 0) {
				studentDao.delete(size - 1);
				System.out.println("Students: delete " + (size - 1));
			}
		}
		else if (Mode.UPDATE == currentMode) {
			try {
				Student s = studentDao.getStudent(1);
				studentDao.updateCauseSQLException(1, s.getAge() + 10);
				System.out.println("Students: update " + s.getName() + " add 10 age = " + studentDao.getStudent(1).getAge());
			}
			catch(RuntimeException e) {
				Student s = studentDao.getStudent(1);
				System.out.println("Students: caught exception " + s.getName() + " age = " + s.getAge());
				throw new RuntimeException();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				Student s = studentDao.getStudent(1);
				System.out.println("Students 1: caught exception " + s.getName() + " age = " + s.getAge());
				Student st = studentDao.getStudent(2);
				System.out.println("Students 2: caught exception " + st.getName() + " age = " + st.getAge());
				e.printStackTrace();
			}
		}
		
		List<Student> users = studentDao.listStudents();
		System.out.println("Students: " + users.size());
		model.addAttribute("msg", " You have " + users.size() + " students");
		if (null != users && users.size() > 0) {
			model.addAttribute("students", users);
		}
		return "content";
	}
	
	@RequestMapping(value="list", method = RequestMethod.GET)
	public String list(ModelMap model, HttpServletRequest request) {
		model.addAttribute("hello", "List ");
		currentMode = Mode.LIST;
		return "index";
	}
	
	@RequestMapping(value="create", method = RequestMethod.GET)
	public String create(ModelMap model) {
		model.addAttribute("hello", "Create ");
		currentMode = Mode.CREATE;
		return "index";
	}
	
	@RequestMapping(value="update", method = RequestMethod.GET)
	public String update(ModelMap model) {
		model.addAttribute("hello", "Update ");
		currentMode = Mode.UPDATE;
		return "index";
	}
	
	@RequestMapping(value="delete", method = RequestMethod.GET)
	public String delete(ModelMap model) {
		model.addAttribute("hello", "Delete ");
		currentMode = Mode.DELETE;
		return "index";
	}
	
	class Mode {
		public final static int UPDATE = 0x01;
		public final static int LIST = 0x02;
		public final static int DELETE = 0x03;
		public final static int CREATE = 0x04;
	}
}

package com.lekaha.test.spring;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lekaha.test.spring.dao.StudentDAO;
import com.lekaha.test.spring.model.Student;
import com.lekaha.test.spring.model.StudentMapper;

public class StudentJDBCTemplate implements StudentDAO {
   private DataSource dataSource;
   private JdbcTemplate jdbcTemplateObject;
   
   public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
   }
   public void create(String name, Integer age) {
      String SQL = "insert into Student (name, age) values (?, ?)";
      jdbcTemplateObject.update( SQL, name, age);
      System.out.println("Created Record Name = " + name + " Age = " + age);
      return;
   }
   public Student getStudent(final Integer id) {
      String SQL = "select * from Student where id = ?";
      PreparedStatementSetter pss = new PreparedStatementSetter() {
          public void setValues(PreparedStatement ps) throws SQLException {
              ps.setInt(1, id);
          }
      };
      
      // Error: if only pass one parameter that is SQL
//      jdbcTemplateObject.query(SQL);
      
      // sql + RowMapper + targetId
//      List<Student> students = jdbcTemplateObject.query(SQL, new StudentMapper(), id);
      
   // sql + RowMapper + PreparedStatementSetter
   // But execute error
//      List<Student> students = jdbcTemplateObject.query(SQL, new StudentMapper(), pss);
      
   // sql + PreparedStatementSetter + RowMapper
      List<Student> students = jdbcTemplateObject.query(SQL, pss, new StudentMapper());
      
//      Student student = jdbcTemplateObject.queryForObject(SQL, 
//         new Object[]{id}, new StudentMapper());
      
      return students.get(0);
   }
   public List<Student> listStudents() {
      String SQL = "select * from Student";
      List <Student> students = jdbcTemplateObject.query(SQL, new StudentMapper());
      return students;
   }
   public void delete(Integer id) {
      String SQL = "delete from Student where id = ?";
      jdbcTemplateObject.update(SQL, id);
      System.out.println("Deleted Record with ID = " + id );
      return;
   }
   
   @Transactional(propagation=Propagation.REQUIRED, readOnly=true)
   public void update(Integer id, Integer age) throws SQLException{
      String SQL = "update Student set age = ? where id = ?";
      jdbcTemplateObject.update(SQL, age, id);
      System.out.println("Updated Record with ID = " + id );
      if (id == 1) {
    	  throw new SQLException();
      }
   }
   
   @Transactional
   public void updateCauseRuntimeException(Integer id, Integer age) {
//	   update(id, age);
	   throw new RuntimeException();
   }
   
   @Transactional
   public void updateCauseSQLException(Integer id, Integer age) throws SQLException {
	   update(id, age);
	   update(id + 1, age);
   }
   
   @Transactional(rollbackFor=Exception.class)
   public void updateCauseSQLExceptionWithRollback(Integer id, Integer age) throws SQLException {
//	   try {
		   update(id, age);
		   update(id + 1, age);
//	   }
//	   catch(SQLException e) {
//		   e.printStackTrace();
//	   }
   }
}

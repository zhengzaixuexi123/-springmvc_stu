package com.example.dao;

import com.example.model.Page;
import com.example.model.Student;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    private JdbcTemplate jdbcTemplate;

    // 添加新方法实现
    @Override
    public int getTotalCount() {
        String sql = "select count(no) from student";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Student> queryAll() {
        String sql = "select * from student_grade_view";
        List<Student> list = this.jdbcTemplate.query(sql, new RowMapperImplGrade());
        return list;
    }

    @Override
    public Student queryByNo(String no) {
        String sql = "select * from student where no = ?";
        Student student = this.jdbcTemplate.queryForObject(sql, new RowMapperImpl(), no);
        return student;
    }

    @Override
    public int insert(Student student) {
        String sql = "insert into student(no, name, age) values(?,?,?)";
        String sql2 = "insert into grade(stu_no,grade) values(?,?)";
        int update = this.jdbcTemplate.update(sql,
                student.getNo(), student.getName(), student.getAge());
        this.jdbcTemplate.update(sql2, student.getNo(), student.getGender());
        return update;
    }

    @Override
    public int deleteByNo(String no) {
        String sql2 = "delete from grade where stu_no = ?";
        String sql = "delete from student where no = ?";
        this.jdbcTemplate.update(sql2, no);
        int delete = this.jdbcTemplate.update(sql, no);
        return delete;
    }

    @Override
    public int updateByNo(Student student) {
        String sql = "update student set name=?, age=? where no=?";
        int update = this.jdbcTemplate.update(sql, student.getName(), student.getAge(), student.getNo());
        return update;
    }

    @Override
    public List<Student> queryByPage(Page page) {
        String sql = "select no,name,age from(select ROWNUM r,no,name,age from" +
                "(select no,name,age from student order by to_number(no))" +
                "where ROWNUM<?)" +
                "where r>?";
        int pageSize = page.getPageSize();
        int currentPage = page.getCurrentPage();
        List<Student> list = this.jdbcTemplate.query(sql, new RowMapperImpl(), pageSize * currentPage + 1, pageSize * (currentPage - 1));
        return list;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*结果集*/
    class RowMapperImpl implements RowMapper<Student> {

        //
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setNo(rs.getString("no"));
            student.setName(rs.getString("name"));
            student.setAge(rs.getInt("age"));
            return student;
        }
    }

    class RowMapperImplGrade implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setNo(rs.getString("no"));
            student.setName(rs.getString("name"));
            student.setAge(rs.getInt("age"));
            student.setGender(rs.getInt("grade")); // 使用gender字段存储成绩
            return student;
        }

    }
}

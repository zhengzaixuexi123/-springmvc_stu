package com.example.dao;

import com.example.model.Page;
import com.example.model.Student;

import java.util.List;

public interface StudentDao {
    int getTotalCount();
    List<Student> queryAll();
    Student queryByNo(String no);
    int insert(Student student);
    int deleteByNo(String no);
    int updateByNo(Student student);
    List<Student> queryByPage(Page page);
}

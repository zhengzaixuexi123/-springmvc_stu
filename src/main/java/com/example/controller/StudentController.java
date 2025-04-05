package com.example.controller;

import com.example.dao.StudentDao;
import com.example.dao.StudentDaoImpl;
import com.example.model.Page;
import com.example.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentDao studentDao;
    @RequestMapping("/list")
    public String list(Model model) {
        List<Student> list = studentDao.queryAll();
        model.addAttribute("students", list);
        return "student/list";
    }
    /*
    重定向到add.html页面，用于添加学生信息
    */
    @RequestMapping("/addInput")
    public String addInput() {
        return "student/add";
    }
    /*
    * 接收添加学生的表单操作,类型的属性与表单的name保持一致
    * 重定向
    */
    @RequestMapping("/add")
    public String add(Student student) {
        System.out.println(student);
        studentDao.insert(student);
        return "redirect:/student/list";
    }

    @RequestMapping("/delete")
    public String delete(String no) {
        studentDao.deleteByNo(no);
        return "redirect:/student/list";
    }

    /*
    * 接收更新链表的请求，同时获取参数学号
    * 根据学号到数据库中查询对应的学生信息
    * 更新到html页面上*/
    @RequestMapping("/updateInput")
    public String updateInput(String no,Model model) {
        Student student = studentDao.queryByNo(no);
        model.addAttribute("student", student);
        return "student/update";
    }
    /*
    *
    * 实现学生更新*/
    @RequestMapping("/update")
    public String update(Student student) {
        studentDao.updateByNo(student);
        return "redirect:/student/list";
    }

    @RequestMapping("/redirectPageNumber")
    public String redirectPageNumber() {
        return "student/PageNumber";
    }

    @RequestMapping("/listPage")
    public String listPage(Page page, Model model) {
        // 设置默认值
        if (page.getCurrentPage() <= 0) {
            page.setCurrentPage(1);
        }
        if (page.getPageSize() <= 0) {
            page.setPageSize(10);  // 默认每页显示10条记录
        }

        // 获取总记录数
        int totalCount = studentDao.getTotalCount();
        page.setTotalCount(totalCount);

        // 计算总页数
        page.calculateTotalPage();

        // 确保当前页不超过总页数
        if (page.getCurrentPage() > page.getTotalPage()) {
            page.setCurrentPage(page.getTotalPage());
        }

        // 获取分页数据
        List<Student> listBypage = studentDao.queryByPage(page);

        // 将数据传递给视图
        model.addAttribute("students", listBypage);
        model.addAttribute("page", page);

        return "student/listBypage";
    }

}

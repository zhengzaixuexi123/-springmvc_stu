package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//提供get，set方法
@Data
//有参构造
@AllArgsConstructor
//无参构造
@NoArgsConstructor
public class Student {
    private String no;
    private String name;
    private Integer age;
    private Integer gender;
}

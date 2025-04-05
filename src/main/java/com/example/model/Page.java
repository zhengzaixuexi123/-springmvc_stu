package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private int currentPage; // 当前页码
    private int pageSize; // 每页显示记录数
    private int totalCount; // 总记录数
    private int totalPage; // 总页数
    
    // 计算总页数
    public void calculateTotalPage() {
        if (this.totalCount % this.pageSize == 0) {
            this.totalPage = this.totalCount / this.pageSize;
        } else {
            this.totalPage = this.totalCount / this.pageSize + 1;
        }
        // 确保至少有1页
        if (this.totalPage == 0) {
            this.totalPage = 1;
        }
    }
}

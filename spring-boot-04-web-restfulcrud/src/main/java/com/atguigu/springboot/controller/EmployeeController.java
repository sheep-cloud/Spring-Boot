package com.atguigu.springboot.controller;

import com.atguigu.springboot.dao.DepartmentDao;
import com.atguigu.springboot.dao.EmployeeDao;
import com.atguigu.springboot.entities.Department;
import com.atguigu.springboot.entities.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * 员工管理
 *
 * @author: colg
 */
@Controller
public class EmployeeController {

    public static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 查询所有员工返回列表页面
     *
     * @return
     */
    @GetMapping("/emps")
    public String list(Model model) {
        Collection<Employee> employees = employeeDao.getAll();

        // 放在请求域中
        model.addAttribute("emps", employees);
        log.info("employees size : {}", employees.size());

        // thymeleaf默认就会拼串
        // classpath:/templates/xxx.html
        return "emp/list";
    }

    /**
     * 员工添加页面
     *
     * @return
     */
    @GetMapping("/emp")
    public String toAddPage(Model model) {
        // 来到添加页面，查处所有的部门，在页面显示
        Collection<Department> departments = departmentDao.getDepartments();
        log.info("departments size : {}", departments.size());
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    /**
     * 员工添加 <br/>
     * <p>
     * SpirngMVC自动将请求参数和入参对象的属性进行一一绑定，要求请求参数的名字和javaBean入参的对象里面的属性名是一样的
     *
     * @param employee
     *
     * @return
     */
    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        // 来到员工列表页面
        log.info("employee : {}", employee);
        // 保存员工
        employeeDao.save(employee);
        // redirect：表示重定向到一个地址，/代表当前项目路径
        // forword：表示转发到一个地址
        return "redirect:emps";
    }

    /**
     * 来到修改页面，查出当前员工，在页面回显
     *
     * @param id
     *
     * @return
     */
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeDao.get(id);
        log.info("id : {}", id);
        model.addAttribute("emp", employee);

        // 页面要显式所有的部门列表
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);

        // 回到修改页面（add是一个修改添加二合一的页面）
        return "emp/add";
    }

    /**
     * 修改员工信息，需要提交员工id
     *
     * @param employee
     *
     * @return
     */
    @PutMapping("/emp")
    public String updateEmployee(Employee employee) {
        employeeDao.save(employee);
        log.info("employee : {}", employee);
        return "redirect:/emps";
    }

    /**
     * 删除员工
     *
     * @param id
     *
     * @return
     */
    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        log.info("id : {}", id);
        return "redirect:/emps";
    }
}

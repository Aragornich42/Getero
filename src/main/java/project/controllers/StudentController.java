package project.controllers;

import project.dao.StudentJdbc;
import project.model.Student;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class StudentController {

    private final StudentJdbc studentJdbc;

    public StudentController(StudentJdbc studentJdbc) {
        this.studentJdbc = studentJdbc;
    }

    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable int id) {
        return studentJdbc.Get(id);
    }

    @GetMapping("/student/all")
    public List<Student> getAllStudents() {
        return studentJdbc.GetAll();
    }

    @GetMapping("/student/group/{id}")
    public List<Student> getStudentsFromGroup(@PathVariable int id) {
        return studentJdbc.GetAllByGroup(id);
    }

    @PostMapping("/student/new")
    public int addNewStudent(@RequestBody Student stud) {
        return studentJdbc.Add(stud);
    }

    @PostMapping("/student/update")
    public int updateStudent(@RequestBody Student stud) {
        return studentJdbc.Update(stud);
    }

    @DeleteMapping("/student/delete/{id}")
    public int deleteStudent(@PathVariable int id) {
        return studentJdbc.Delete(id);
    }

}

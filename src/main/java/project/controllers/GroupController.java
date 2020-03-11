package project.controllers;

import project.dao.GroupJdbc;
import project.model.Group;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class GroupController {

    private final GroupJdbc studyGroupJdbc;

    public GroupController(GroupJdbc studyGroupJdbc) {
        this.studyGroupJdbc = studyGroupJdbc;
    }

    @GetMapping("/group/{id}")
    public Group getStudyGroup(@PathVariable int id) {
        return studyGroupJdbc.Get(id);
    }

    @GetMapping("/group/all")
    public List<Group> getAllStudents() {
        return studyGroupJdbc.GetAll();
    }

    @PostMapping("/group/new")
    public int addStudyGroup(@RequestBody Group sG) {
        return studyGroupJdbc.Add(sG);
    }

    @PostMapping("/group/update")
    public int updateStudyGroup(@RequestBody Group sG) {
        return studyGroupJdbc.Update(sG);
    }

    @DeleteMapping("/group/delete/{id}")
    public int deleteStudyGroup(@PathVariable int id) {
        return studyGroupJdbc.Delete(id);
    }

}

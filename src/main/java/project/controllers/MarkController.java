package project.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import project.dao.MarkJdbc;
import project.model.Mark;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarkController {

    private final MarkJdbc markJdbc;

    public MarkController(MarkJdbc markJdbc) {
        this.markJdbc = markJdbc;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/mark/{id}")
    public Mark GetMark(@PathVariable int id) {
        return markJdbc.Get(id);
    }

}

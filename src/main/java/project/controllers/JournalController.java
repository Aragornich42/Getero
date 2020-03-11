package project.controllers;

import project.dao.JournalJdbc;
import project.model.Journal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class JournalController {

    private final JournalJdbc journalRecordJdbc;

    public JournalController(JournalJdbc journalRecordJdbc) {
        this.journalRecordJdbc = journalRecordJdbc;
    }

    @GetMapping("/journal/{id}")
    public Journal getJournalRecord(@PathVariable int id) {
        return journalRecordJdbc.Get(id);
    }

    @GetMapping("/journal/all")
    public List<Journal> getAllJournalRecords() {
        return journalRecordJdbc.GetAll();
    }

    @GetMapping("/journal/student/{id}")
    public List<Journal> getJournalRecordsByStudent(@PathVariable int id) {
        return journalRecordJdbc.GetAllByStudent(id);
    }

    @GetMapping("/journal/study_plan/{id}")
    public List<Journal> getJournalRecordsByStudyPlan(@PathVariable int id) {
        return journalRecordJdbc.GetAllByStudyPlan(id);
    }

    @PostMapping("/journal/add")
    public int addNewRecord(@RequestBody Journal jR) {
        return journalRecordJdbc.AddJournalRecord(jR);
    }

    @PostMapping("/journal/update")
    public int updateRecord(@RequestBody Journal jR) {
        return journalRecordJdbc.UpdateJournalRecord(jR);
    }

    @DeleteMapping("/journal/delete/{id}")
    public int deleteRecordById(@PathVariable int id) {
        return journalRecordJdbc.DeleteJournalRecord(id);
    }

}

package project.dao;

import project.model.Journal;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JournalJdbc {

    private final JdbcTemplate jdbcTemplate;

    public JournalJdbc(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Journal Get(int id)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM journal WHERE id = ?",
                this::MapJournalRecord, id);
    }

    public List<Journal> GetAll()
    {
        return jdbcTemplate.queryForObject("SELECT * FROM journal", this::MapAllJournalRecords);
    }

    public List<Journal> GetAllByStudent(int id)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM journal WHERE student_id = ?",
                this::MapAllJournalRecords, id);
    }

    public List<Journal> GetAllByStudyPlan(int id)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM journal WHERE study_plan_id = ?",
                this::MapAllJournalRecords, id);
    }

    public int AddJournalRecord(@NotNull Journal jR)
    {
        return jdbcTemplate.update("INSERT INTO journal (student_id, study_plan_id, " +
                        "in_time, count, mark_id) VALUES (?, ?, ?, ?, ?)", jR.getStudentId(),
                jR.getStudyPlanId(), jR.isInTime(), jR.getCount(), jR.getMarkId());
    }

    public int UpdateJournalRecord(@NotNull Journal jR)
    {
        StringBuilder sqlQuery = new StringBuilder("UPDATE journal SET ");

        if (jR.getStudentId() != null) sqlQuery.append("student_id = '").append(jR.getStudentId())
                .append("', ");
        if (jR.getStudyPlanId() != null) sqlQuery.append("study_plan_id = '").append(jR.getStudyPlanId())
                .append("', ");
        if (jR.isInTime() != null) sqlQuery.append("in_time = '").append(jR.isInTime()).append("', ");
        if (jR.getCount() != null) sqlQuery.append("count = '").append(jR.getCount()).append("', ");
        if (jR.getMarkId() != null) sqlQuery.append("mark_id = ").append(jR.getMarkId()).append("' ");
        else if (sqlQuery.charAt(sqlQuery.length() - 2) == ',') sqlQuery.deleteCharAt(sqlQuery.length() - 2);
        sqlQuery.append("WHERE id = ?");

        return jdbcTemplate.update(sqlQuery.toString(), jR.getId());
    }

    public int DeleteJournalRecord(int id)
    {
        return jdbcTemplate.update("DELETE FROM journal WHERE id = ?", id);
    }

    private Journal MapJournalRecord(@NotNull ResultSet rs, int i) throws SQLException
    {
        return new Journal(
                rs.getInt("id"),
                rs.getInt("student_id"),
                rs.getInt("study_plan_id"),
                rs.getInt("in_time"),
                rs.getInt("count"),
                rs.getInt("mark_id")
        );
    }

    private List<Journal> MapAllJournalRecords(@NotNull ResultSet rs, int i) throws SQLException
    {
        List<Journal> journalRecordList = new ArrayList<>();

        do
        {
            journalRecordList.add(new Journal(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getInt("study_plan_id"),
                    rs.getInt("in_time"),
                    rs.getInt("count"),
                    rs.getInt("mark_id")));
        }
        while (rs.next());

        return journalRecordList;
    }

}

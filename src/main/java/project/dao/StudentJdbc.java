package project.dao;

import project.model.Student;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentJdbc {

    private final JdbcTemplate jdbcTemplate;

    public StudentJdbc(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Student Get(int id)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM student WHERE id = ?",
                this::MapStudent, id);
    }

    public List<Student> GetAll()
    {
        return jdbcTemplate.queryForObject("SELECT * FROM student", this::MapAllStudents);
    }

    public List<Student> GetAllByGroup(int id)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM student WHERE study_group_id = ?",
                this::MapAllStudents, id);
    }

    public int Add(@NotNull Student stud)
    {
        return jdbcTemplate.update("INSERT INTO student (sur_name, name, second_name, " +
                "study_group_id) VALUES (?, ?, ?, ?)", stud.getSurName(), stud.getName(),
                stud.getSecondName(), stud.getStudyGroupId());
    }

    public int Update(@NotNull Student stud)
    {
        StringBuilder sqlRequest = new StringBuilder("UPDATE student SET ");

        if (!StringUtils.isEmpty(stud.getSurName()))
            sqlRequest.append("sur_name = '").append(stud.getSurName()).append("', ");
        if (!StringUtils.isEmpty(stud.getName())) sqlRequest.append("name = '").append(stud.getName())
                .append("', ");
        if (!StringUtils.isEmpty(stud.getSecondName()))
            sqlRequest.append("second_name = '").append(stud.getSecondName()).append("', ");
        if (stud.getStudyGroupId() != null)
            sqlRequest.append("study_group_id = '").append(stud.getStudyGroupId()).append("' ");
        else if (sqlRequest.charAt(sqlRequest.length() - 2) == ',') sqlRequest
                .deleteCharAt(sqlRequest.length() - 2);
        sqlRequest.append("WHERE id = ?");

        return jdbcTemplate.update(sqlRequest.toString(), stud.getId());
    }

    public int Delete(int id)
    {
        return jdbcTemplate.update("DELETE FROM student WHERE id = ?", id);
    }

    public List<Student> GetAllLocal()
    {
        return jdbcTemplate.query("SELECT * FROM student_local", this::MapStudent);
    }

    private Student MapStudent(@NotNull ResultSet rs, int i) throws SQLException
    {
        return new Student(
                rs.getInt("id"),
                rs.getInt("study_group_id"),
                rs.getString("sur_name"),
                rs.getString("name"),
                rs.getString("second_name")
        );
    }

    private List<Student> MapAllStudents(@NotNull ResultSet rs, int i) throws SQLException
    {
        List<Student> studentList = new ArrayList<>();

        do
        {
            studentList.add(new Student(rs.getInt("id"),
                    rs.getInt("study_group_id"),
                    rs.getString("sur_name"),
                    rs.getString("name"),
                    rs.getString("second_name")));
        }
        while (rs.next());

        return studentList;
    }

}

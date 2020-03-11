package project.dao;

import project.model.Group;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupJdbc {

    private final JdbcTemplate jdbcTemplate;

    public GroupJdbc(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Group Get(int id)
    {
        return jdbcTemplate.queryForObject("SELECT * FROM study_group WHERE id = ?",
                this::MapStudyGroup, id);
    }

    public List<Group> GetAll()
    {
        return jdbcTemplate.queryForObject("SELECT * FROM study_group", this::MapAllStudyGroups);
    }

    public int Add(@NotNull Group sG)
    {
        return jdbcTemplate.update("INSERT INTO study_group (name) VALUES (?)", sG.getName());
    }

    public int Update(@NotNull Group sG)
    {
        return jdbcTemplate.update("UPDATE study_group SET name = ? WHERE id = ?",
                sG.getName(), sG.getId());
    }

    public int Delete(int id)
    {
        return jdbcTemplate.update("DELETE FROM study_group WHERE id = ?", id);
    }

    private Group MapStudyGroup(@NotNull ResultSet rs, int i) throws SQLException
    {
        return new Group(
                rs.getInt("id"),
                rs.getString("name")
        );
    }

    private List<Group> MapAllStudyGroups(@NotNull ResultSet rs, int i) throws SQLException
    {
        List<Group> groupList = new ArrayList<>();

        do
        {
            groupList.add(new Group(rs.getInt("id"),
                    rs.getString("name")));
        }
        while (rs.next());

        return groupList;
    }

}

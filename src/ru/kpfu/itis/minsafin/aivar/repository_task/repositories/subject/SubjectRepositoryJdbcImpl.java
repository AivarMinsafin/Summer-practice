package ru.kpfu.itis.minsafin.aivar.repository_task.repositories.subject;


import ru.kpfu.itis.minsafin.aivar.repository_task.models.Subject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static ru.kpfu.itis.minsafin.aivar.repository_task.repositories.subject.SubjectSqlQueries.*;

public class SubjectRepositoryJdbcImpl implements SubjectRepository{
    private Connection connection;

    public SubjectRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Subject> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL.getQuery());
            List<Subject> subjects = new ArrayList<>();
            while (resultSet.next()){
                subjects.add( new Subject(
                        resultSet.getLong("id"),
                        resultSet.getString("title")
                ));
            }
            if (!subjects.isEmpty()){
                return subjects;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
    }

    @Override
    public Subject findById(Long id) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(FIND_BY_ID.getQuery(), id));
            if (resultSet.next()){
                return new Subject(id, resultSet.getString("title").trim());
            } else return null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
    }

    @Override
    public void save(Subject entity) {
        //TODO realization
    }

    @Override
    public void update(Subject entity) {
        //TODO realization
    }
}

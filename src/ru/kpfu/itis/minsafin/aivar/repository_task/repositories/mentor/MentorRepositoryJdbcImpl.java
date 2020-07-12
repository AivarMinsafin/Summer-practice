package ru.kpfu.itis.minsafin.aivar.repository_task.repositories.mentor;

import ru.kpfu.itis.minsafin.aivar.repository_task.models.Mentor;
import ru.kpfu.itis.minsafin.aivar.repository_task.models.Student;
import ru.kpfu.itis.minsafin.aivar.repository_task.repositories.student.StudentsRepository;
import ru.kpfu.itis.minsafin.aivar.repository_task.repositories.subject.SubjectRepository;
import ru.kpfu.itis.minsafin.aivar.repository_task.repositories.subject.SubjectRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.minsafin.aivar.repository_task.repositories.mentor.MentorSqlQueries.*;

public class MentorRepositoryJdbcImpl implements MentorRepository {
    private Connection connection;
    private SubjectRepository subjectRepository;
    private StudentsRepository studentsRepository;

    public MentorRepositoryJdbcImpl(Connection connection, StudentsRepository studentsRepository) {
        this.connection = connection;
        this.subjectRepository = new SubjectRepositoryJdbcImpl(connection);
        this.studentsRepository = studentsRepository;
    }

    @Override
    public List<Mentor> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL.getQuery());
            List<Mentor> mentors = new ArrayList<>();
            while (resultSet.next()) {
                //TODO to finish findAll for MentorRepo
            }
            return null;
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
    public Mentor findById(Long id) {
        if (id == null) {
            return null;
        }
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(FIND_BY_ID.getQuery(), id));
            if (resultSet.next()) {
                Student student = new Student(
                        resultSet.getLong(6),
                        resultSet.getString(7).trim(),
                        resultSet.getString(8).trim(),
                        resultSet.getInt("age"),
                        resultSet.getInt("group_number"),
                        null  //TODO fix mentors list getting
                );
                return new Mentor(
                        id,
                        resultSet.getString(2).trim(),
                        resultSet.getString(3).trim(),
                        subjectRepository.findById(resultSet.getLong("subject_id")),
                        student
                );
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
    public void save(Mentor entity) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(
                    INSERT_MENTOR.getQuery(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getSubject().getId(),
                    entity.getStudent().getId()
            ));
            if (resultSet.next()){
                entity.setId(resultSet.getLong(1));
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
    public void update(Mentor entity) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(String.format(
                    UPDATE_MENTOR.getQuery(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getSubject().getId(),
                    entity.getStudent().getId(),
                    entity.getId()
            ));
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
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
    public boolean exists(Long id) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(FIND_BY_ID.getQuery(), id));
            if (resultSet.next()){
                return true;
            } else return false;
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
}

package ru.kpfu.itis.minsafin.aivar.repository_task.repositories.student;

import ru.kpfu.itis.minsafin.aivar.repository_task.models.Mentor;
import ru.kpfu.itis.minsafin.aivar.repository_task.models.Student;
import ru.kpfu.itis.minsafin.aivar.repository_task.repositories.mentor.MentorRepository;
import ru.kpfu.itis.minsafin.aivar.repository_task.repositories.mentor.MentorRepositoryJdbcImpl;
import ru.kpfu.itis.minsafin.aivar.repository_task.repositories.subject.SubjectRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static ru.kpfu.itis.minsafin.aivar.repository_task.repositories.student.StudentSqlQueries.*;


public class StudentsRepositoryJdbcImpl implements StudentsRepository {
    private Connection connection;
    private MentorRepository mentorRepository;

    public StudentsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
        this.mentorRepository = new MentorRepositoryJdbcImpl(connection, this); //?
    }

    @Override
    public List<Student> findAllByAge(int age) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(FIND_ALL_BY_AGE.getQuery(), age));
            List<Student> students = new ArrayList<>();
            Long t = null;
            Student student = null;
            List<Mentor> mentors = null;
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                if (!id.equals(t)) {
                    if (t != null) {
                        students.add(student);
                    }
                    student = new Student(
                            id,
                            resultSet.getString(2).trim(), //FirstName
                            resultSet.getString(3).trim(), //LastName
                            resultSet.getInt(4), //Age
                            resultSet.getInt(5), // Group
                            null
                    );
                    mentors = new ArrayList<>();
                }
                Long mentorId = resultSet.getLong(6);
                if (mentorId > 0 && mentors != null) {
                    mentors.add(new Mentor(
                            mentorId,
                            resultSet.getString(7).trim(),
                            resultSet.getString(8).trim(),
                            null,
                            null
                    ));
                }
                student.setMentors(mentors);
                t = id;
            }
            if (!students.isEmpty()){
                return students;
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
    public List<Student> findAll() {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(FIND_ALL.getQuery());
            List<Student> students = new ArrayList<>();
            Long t = null;
            Student student = null;
            List<Mentor> mentors = null;
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                if (!id.equals(t)) {
                    if (t != null) {
                        students.add(student);
                    }
                    student = new Student(
                            id,
                            resultSet.getString(2).trim(), //FirstName
                            resultSet.getString(3).trim(), //LastName
                            resultSet.getInt(4), //Age
                            resultSet.getInt(5), // Group
                            null
                    );
                    mentors = new ArrayList<>();
                }
                Long mentorId = resultSet.getLong(6);
                if (mentorId > 0 && mentors != null) {
                    mentors.add(new Mentor(
                            mentorId,
                            resultSet.getString(7).trim(),
                            resultSet.getString(8).trim(),
                            null,
                            null
                    ));
                }
                student.setMentors(mentors);
                t = id;
            }
            if (!students.isEmpty()){
                return students;
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
    public Student findById(Long id) {
        if (id == null) {
            return null;
        }
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(FIND_BY_ID_STUDENT.getQuery(), id));
            Student student = null;
            if (resultSet.next()) {
                student = new Student(
                        id,
                        resultSet.getString("first_name").trim(),
                        resultSet.getString("last_name").trim(),
                        resultSet.getInt("age"),
                        resultSet.getInt("group_number"),
                        null
                );
            } else return null;
            resultSet = statement.executeQuery(String.format(FIND_MENTOR_BY_STUDENT_ID.getQuery(), id));
            List<Mentor> mentors = new ArrayList<>();
            while (resultSet.next()) {
                mentors.add(mentorRepository.findById(resultSet.getLong("id")));
            }
            student.setMentors(mentors);
            return student;
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
    public void save(Student entity) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(
                    INSERT_STUDENT.getQuery(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getAge(),
                    entity.getGroupNumber()
            ));
            if (resultSet.next()) {
                entity.setId(resultSet.getLong("id"));
            }
            List<Mentor> mentors = entity.getMentors();
            if (!mentors.isEmpty()) {
                for (Mentor m : mentors) {
                    if (mentorRepository.exists(m.getId())) {
                        mentorRepository.update(m);
                    } else {
                        mentorRepository.save(m);
                    }
                }
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
    public void update(Student entity) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(String.format(
                    UPDATE_STUDENT.getQuery(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getAge(),
                    entity.getGroupNumber()
            ));
            List<Mentor> mentors = entity.getMentors();
            if (!mentors.isEmpty()) {
                for (Mentor m : mentors) {
                    if (mentorRepository.exists(m.getId())) {
                        mentorRepository.update(m);
                    } else {
                        mentorRepository.save(m);
                    }
                }
            }
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
}

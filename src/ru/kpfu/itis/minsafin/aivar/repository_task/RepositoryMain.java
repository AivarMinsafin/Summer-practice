package ru.kpfu.itis.minsafin.aivar.repository_task;

import ru.kpfu.itis.minsafin.aivar.jdbc_task.SimpleDataSource;
import ru.kpfu.itis.minsafin.aivar.repository_task.models.Mentor;
import ru.kpfu.itis.minsafin.aivar.repository_task.models.Student;
import ru.kpfu.itis.minsafin.aivar.repository_task.repositories.mentor.MentorRepositoryJdbcImpl;
import ru.kpfu.itis.minsafin.aivar.repository_task.repositories.student.StudentsRepositoryJdbcImpl;
import ru.kpfu.itis.minsafin.aivar.repository_task.repositories.subject.SubjectRepositoryJdbcImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class RepositoryMain {
    private static final String URL = "jdbc:postgresql://localhost:5432/javapractice";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        SimpleDataSource source = new SimpleDataSource();
        Connection connection = source.openConnection(URL, USER, PASSWORD);
        StudentsRepositoryJdbcImpl studentsRepository = new StudentsRepositoryJdbcImpl(connection);
        System.out.println(studentsRepository.findById(2L));
        //Mentor mentor = new Mentor(2L, "TestUpdate", "test", new SubjectRepositoryJdbcImpl(connection).findById(2L), studentsRepository.findById(3L));
        //MentorRepositoryJdbcImpl mentorRepositoryJdbc = new MentorRepositoryJdbcImpl(connection, studentsRepository);
        //mentorRepositoryJdbc.update(mentor);
        System.out.println(studentsRepository.findAll());
        System.out.println(studentsRepository.findAllByAge(19));
        try {
            connection.close();
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables);
        }
    }
}

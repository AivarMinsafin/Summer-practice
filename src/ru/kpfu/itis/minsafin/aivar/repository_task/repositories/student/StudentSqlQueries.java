package ru.kpfu.itis.minsafin.aivar.repository_task.repositories.student;

public enum StudentSqlQueries {
    INSERT_STUDENT("INSERT INTO student (first_name, last_name, age, group_number) VALUES ('%s', '%s', %d, %d) RETURNING id;"),
    FIND_BY_ID_STUDENT("SELECT * FROM student WHERE id = %d"),
    FIND_MENTOR_BY_STUDENT_ID("SELECT * FROM mentor m JOIN subject s ON m.subject_id = s.id WHERE student_id = %d;"),
    UPDATE_STUDENT("UPDATE student SET first_name = '%s', last_name = '%s', age = %d, group_number = %d WHERE id = %d;"),
    FIND_ALL("SELECT * FROM student s LEFT JOIN mentor m ON s.id = m.student_id ORDER BY s.id;"),
    FIND_ALL_BY_AGE("SELECT * FROM student s LEFT JOIN mentor m ON s.id = m.student_id WHERE s.age = %d ORDER BY s.id;")
    ;

    private String query;

    StudentSqlQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}

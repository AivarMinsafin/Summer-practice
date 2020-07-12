package ru.kpfu.itis.minsafin.aivar.repository_task.repositories.mentor;

public enum MentorSqlQueries {
    SELECT_ALL("SELECT * FROM mentor"),
    FIND_BY_ID("SELECT * FROM mentor m JOIN student s ON m.student_id = s.id WHERE m.id = %d;"),
    INSERT_MENTOR("INSERT INTO mentor (first_name, last_name, subject_id, student_id) VALUES ('%s', '%s', %d, %d) RETURNING id;"),
    UPDATE_MENTOR("UPDATE mentor SET first_name ='%s', last_name ='%s', subject_id =%d, student_id =%d WHERE id =%d;")
    ;

    private String query;

    MentorSqlQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}

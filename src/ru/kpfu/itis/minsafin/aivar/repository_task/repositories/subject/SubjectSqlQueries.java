package ru.kpfu.itis.minsafin.aivar.repository_task.repositories.subject;

public enum SubjectSqlQueries {
    SELECT_ALL("SELECT * FROM subject;"),
    FIND_BY_ID("SELECT * FROM subject WHERE id = %d;")
    ;
    private String query;

    SubjectSqlQueries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}

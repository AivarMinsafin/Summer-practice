package ru.kpfu.itis.minsafin.aivar.repository_task.repositories.student;

import ru.kpfu.itis.minsafin.aivar.repository_task.models.Student;
import ru.kpfu.itis.minsafin.aivar.repository_task.repositories.CrudRepository;

import java.util.List;

public interface StudentsRepository  extends CrudRepository<Student> {
    List<Student> findAllByAge(int age);
}

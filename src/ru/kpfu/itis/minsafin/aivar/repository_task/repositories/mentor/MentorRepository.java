package ru.kpfu.itis.minsafin.aivar.repository_task.repositories.mentor;

import ru.kpfu.itis.minsafin.aivar.repository_task.models.Mentor;
import ru.kpfu.itis.minsafin.aivar.repository_task.repositories.CrudRepository;

public interface MentorRepository extends CrudRepository<Mentor> {
    boolean exists(Long id);
}

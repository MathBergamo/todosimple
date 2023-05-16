package com.matheus.todosimple.repositories;

import com.matheus.todosimple.models.Task;
import com.matheus.todosimple.models.projection.TaskProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<TaskProjection> findByUser_Id(Long id);

    /*FORMA DE COLOCAR POR MEIO DA JPQ, mais adaptável para quem tem costume com quem usa SQL/Queries diretamente.

   @Query(value = "SELECT t FROM Task t WHERE t.user.id = : user_id")
    List<Task> findByUser_Id(@Param("id") Long user_id);
     */
}

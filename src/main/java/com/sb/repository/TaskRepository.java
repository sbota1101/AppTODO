package com.sb.repository;

import com.sb.model.Task;

import javax.persistence.EntityManager;
import java.util.List;

public class TaskRepository implements CrudRepository<Task,Integer> {
    private EntityManager entityManager;

    public TaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Task> findAll() {
          return entityManager.createQuery("SELECT t FROM Task t").getResultList();
    }

    public void save(Task task) {
        entityManager.getTransaction().begin();
        entityManager.persist(task);
        entityManager.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        Task task = findById(id);
        if (task != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(task);
            entityManager.getTransaction().commit();
        }
    }

    public Task findById(Integer id) {
        try {
            Task task= entityManager.find(Task.class, id);
            return task;
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return null;

    }
    public Task Description(String taskName) {
        try {
            Task task = (Task) entityManager
                    .createQuery("SELECT t  FROM Task t WHERE t.taskName =: taskName")
                    .setParameter("taskName", taskName)
                    .getResultList().get(0);
            return task;
        } catch (Exception e) {
            System.out.println("Hello");
            return null;
        }
    }
}

package com.sb.repository;

import com.sb.model.SubTask;

import javax.persistence.EntityManager;
import java.util.List;

public class SubTaskRepository implements CrudRepository<SubTask,Integer>{
    private EntityManager entityManager;

    public SubTaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<SubTask> findAll() {
        return entityManager.createQuery("SELECT st FROM SubTask st").getResultList();
    }

    public void save(SubTask subTask) {
        entityManager.getTransaction().begin();
        entityManager.persist(subTask);
        entityManager.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        SubTask subTask = findById(id);
        if (subTask != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(subTask);
            entityManager.getTransaction().commit();
        }
    }

    public SubTask findById(Integer id) {
        try {
            SubTask subTask = entityManager.find(SubTask.class, id);
            return subTask;
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return null;
          }

    public SubTask findBytaskName(String subTaskName) {
        try {
            SubTask subTask = (SubTask) entityManager
                    .createQuery("SELECT st  FROM SubTask st WHERE st.subTaskName =: subTaskName")
                    .setParameter("subTaskName", subTaskName)
                    .getResultList().get(0);
            return subTask;
        } catch (Exception e) {
            System.out.println("Hello");
            return null;
        }
    }
}

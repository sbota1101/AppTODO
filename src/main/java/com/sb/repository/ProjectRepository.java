package com.sb.repository;

import com.sb.model.Project;

import javax.persistence.EntityManager;
import java.util.List;

public class ProjectRepository implements CrudRepository<Project,Integer> {

    private EntityManager entityManager;
    public ProjectRepository(EntityManager entityManager) {
        this.entityManager=entityManager;
    }

    @Override
    public List<Project> findAll() {
        return entityManager.createQuery("SELECT p FROM Project p").getResultList();
    }

    @Override
    public void save(Project project) {
        entityManager.getTransaction().begin();
        entityManager.persist(project);
        entityManager.getTransaction().commit();

    }

    @Override
    public void deleteById(Integer id) {
        Project project = findById(id);
        if (project != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(project);
            entityManager.getTransaction().commit();
        }

    }

    @Override
    public Project findById(Integer id) {
        return null;
    }
}

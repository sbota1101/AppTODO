package com.sb.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="project")
public class Project {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int project_id;
    private String name;
    @Column(name = "created_at")
    private Date createdAt;
    @ManyToMany(mappedBy = "projects")
    private List<User> users;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private List<Task>tasks;

    public Project(){}

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

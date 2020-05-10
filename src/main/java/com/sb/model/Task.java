package com.sb.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private int id;
    private String description;
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<SubTask> subTasks;

    @ManyToOne
    private Project project;


   // private Boolean TaskDone;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "in_progress")
    private boolean inProgress;

    public Task() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean isInProgress() {

        return inProgress;

    }

    public void setInProgress(boolean inProgress) {

        this.inProgress = inProgress;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(Date date) {
        this.createdAt = date;
    }


    @Override
    public String toString() {
        return this.description;
    }
}

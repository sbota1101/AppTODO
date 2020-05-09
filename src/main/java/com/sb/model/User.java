package com.sb.model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Auto-increment identity-creste cu unu,auto-la fel dar dep de implementari.
    private int user_id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToMany
    @JoinTable(name = "working_project",
            joinColumns = @JoinColumn(name = "user_id"), //current entity->user
            inverseJoinColumns = @JoinColumn(name = "project_id")) //foreign key->project
    private List<Project> projects;

    @OneToOne(mappedBy = "user")
    private PendingUser pendingUser;


    public User() {

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Task> getTask() {
        return tasks;
    }

    public void setTask(List<Task> task) {
        this.tasks = task;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public PendingUser getPendingUser() {
        return pendingUser;
    }

    public void setPendingUser(PendingUser pendingUser) {
        this.pendingUser = pendingUser;
    }


    @Override
    public String toString() {
        return this.username;
    }
}

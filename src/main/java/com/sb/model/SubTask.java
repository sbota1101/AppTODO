package com.sb.model;

import javax.persistence.*;

@Entity
@Table(name="subtask")
public class SubTask {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="subtask_id")
    private int id;
    private String description;

    @ManyToOne
    private Task task;

    public SubTask(){

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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", task=" + task +
                '}';
    }
}

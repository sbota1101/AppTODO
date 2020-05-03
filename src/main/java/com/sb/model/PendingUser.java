package com.sb.model;

import javax.persistence.*;

@Entity
@Table(name="pending_user")
public class PendingUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

}

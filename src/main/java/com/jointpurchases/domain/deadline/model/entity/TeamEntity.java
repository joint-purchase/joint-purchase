package com.jointpurchases.domain.deadline.model.entity;

import com.jointpurchases.domain.auth.model.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "TEAM")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "DEADLINE_ID")
    private DeadlineEntity deadline;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<User> user = new ArrayList<>();

    @Builder
    public TeamEntity(DeadlineEntity deadline, User user){
        this.deadline = deadline;
        this.user.add(user);
    }

    public void addTeamMember(User user){
        this.user.add(user);
    }
}

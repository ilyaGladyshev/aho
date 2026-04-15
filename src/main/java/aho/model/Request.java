package aho.model;

import jakarta.persistence.Id;
import jakarta.persistence.FetchType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDateTime;


@Entity
@Table(name = "`request`")
@Setter
@Getter
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "area")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Area area;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Equipment category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "equipment")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Equipment equipment;

    @Column(name = "comment")
    private String comment;

    @Column(name = "normativeCount")
    private int normativeCount;

    @Column(name = "factCount")
    private int factCount;

    @Column(name = "spisCount")
    private int spisCount;

    @Column(name = "planedCount")
    private int planedCount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "status")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Status status;

    @Column(name = "date_status")
    private LocalDateTime dateStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_status")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User userStatus;

    @Column(name = "comment_controller")
    private String commentController;

    @Column(name = "isJoinedFiles")
    private Boolean isJoinedFiles = false;
}

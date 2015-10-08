package com.revaluate.reminder.persistence;

import com.revaluate.account.persistence.User;
import com.revaluate.domain.reminder.ReminderType;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "reminders")
@EntityListeners({AuditingEntityListener.class})
public class Reminder implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;
    public static final String USER_ID = "user_id";
    protected static final String SEQ_NAME = "reminders_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "reminders_seq_generator";
    protected static final int ALLOCATION_SIZE = 1;

    @Id
    @Column(name = "reminder_id", updatable = false)
    @SequenceGenerator(name = Reminder.SEQ_GENERATOR_NAME,
            sequenceName = Reminder.SEQ_NAME,
            allocationSize = Reminder.ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = USER_ID, nullable = false)
    private User user;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dueOnDate;

    @NotEmpty
    @Column(nullable = false)
    private String recurringRule;

    private LocalDateTime sentDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime recurringStartDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @NotNull
    @Column(nullable = false)
    private int sentCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReminderType reminderType;

    @PrePersist
    void createdAt() {
        this.createdDate = this.modifiedDate = this.recurringStartDate = LocalDateTime.now();
    }

    @PreUpdate
    void updatedAt() {
        this.modifiedDate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDueOnDate() {
        return dueOnDate;
    }

    public void setDueOnDate(LocalDateTime dueOnDate) {
        this.dueOnDate = dueOnDate;
    }

    public String getRecurringRule() {
        return recurringRule;
    }

    public void setRecurringRule(String recurringRule) {
        this.recurringRule = recurringRule;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDateTime sentDate) {
        this.sentDate = sentDate;
    }

    public LocalDateTime getRecurringStartDate() {
        return recurringStartDate;
    }

    public void setRecurringStartDate(LocalDateTime recurringStartDate) {
        this.recurringStartDate = recurringStartDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getSentCount() {
        return sentCount;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    public ReminderType getReminderType() {
        return reminderType;
    }

    public void setReminderType(ReminderType reminderType) {
        this.reminderType = reminderType;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", user=" + user +
                ", dueOnDate=" + dueOnDate +
                ", recurringRule='" + recurringRule + '\'' +
                ", sentDate=" + sentDate +
                ", recurringStartDate=" + recurringStartDate +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", sentCount=" + sentCount +
                ", reminderType=" + reminderType +
                '}';
    }
}
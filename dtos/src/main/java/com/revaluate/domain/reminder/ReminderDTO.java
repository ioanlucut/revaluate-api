package com.revaluate.domain.reminder;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@GeneratePojoBuilder
public class ReminderDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    private int id;
    @NotNull
    private LocalDateTime dueOnDate;
    @NotEmpty
    private String recurringRule;
    private LocalDateTime sentDate;
    private LocalDateTime recurringStartDate;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int sentCount;
    @NotNull
    private ReminderType reminderType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "ReminderDTO{" +
                "id=" + id +
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
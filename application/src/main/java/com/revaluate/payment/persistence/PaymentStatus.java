package com.revaluate.payment.persistence;

import com.revaluate.account.persistence.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@SequenceGenerator(name = PaymentStatus.SEQ_GENERATOR_NAME,
        sequenceName = PaymentStatus.SEQ_NAME,
        initialValue = PaymentStatus.SEQ_INITIAL_VALUE,
        allocationSize = PaymentStatus.ALLOCATION_SIZE)
@Table(name = "payment_status",
        indexes = {
                @Index(name = PaymentStatus.IX_PAYMENT_STATUS_USER_ID, columnList = PaymentStatus.USER_ID)
        }
)
public class PaymentStatus implements Serializable {

    public static final String USER_ID = "user_id";
    public static final String IX_PAYMENT_STATUS_USER_ID = "IX_PAYMENT_STATUS_USER_ID";
    protected static final String SEQ_NAME = "payment_status_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "payment_status_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 1;
    protected static final int ALLOCATION_SIZE = 1;
    private static final long serialVersionUID = -1799428438852023627L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String customerId;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String paymentMethodToken;

    @OneToOne(optional = false)
    @JoinColumn(name = USER_ID, nullable = false, unique = true)
    private User user;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @PrePersist
    void createdAt() {
        this.createdDate = this.modifiedDate = LocalDateTime.now();
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPaymentMethodToken() {
        return paymentMethodToken;
    }

    public void setPaymentMethodToken(String paymentMethodToken) {
        this.paymentMethodToken = paymentMethodToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "PaymentStatus{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", paymentMethodToken='" + paymentMethodToken + '\'' +
                ", user=" + user +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
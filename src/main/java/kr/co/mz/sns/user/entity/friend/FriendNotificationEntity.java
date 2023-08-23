package kr.co.mz.sns.entity.friend;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "friend_notification")
@Data
@NoArgsConstructor
public class FriendNotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    @CreatedBy
    @Column(nullable = false)
    private Long userSeq;
    @Column(nullable = false)
    private Long targetSeq;
    @Column
    private Boolean readStatus;
    @CreatedDate
    private LocalDateTime requestedAt;
}
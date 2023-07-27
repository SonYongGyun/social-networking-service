package kr.co.mz.sns.entity.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import kr.co.mz.sns.auditing.AuditorAwareImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post_like")
@Data
@NoArgsConstructor
public class PostLikeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private Long seq;
  @CreatedBy
  @Column(name = "user_seq")
  private Long userSeq;
  @Column(name = "post_seq")
  private Long postSeq;
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
}


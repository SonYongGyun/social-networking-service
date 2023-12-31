package kr.co.mz.sns.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_profile")
@Data
@NoArgsConstructor
public class UserProfileEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private Long seq;

  @Column(name = "uuid", nullable = false)
  private String uuid;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "path", nullable = false)
  private String path;
  @Column(name = "size", nullable = false)
  private Long size;
  @Column(name = "extension", nullable = false)
  private String extension;
  @CreatedDate
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
  @LastModifiedDate
  @Column(name = "modified_at", nullable = false)
  private LocalDateTime modifiedAt;

  @ManyToOne
  @JoinColumn(name = "user_seq")
  private UserDetailEntity userDetailEntity;

  public UserProfileEntity userDetailEntity(UserDetailEntity userDetail) {
    this.userDetailEntity = userDetail;
    return this;
  }
}

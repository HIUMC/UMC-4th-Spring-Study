package study.datajpa.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 이 속성을 공유해서 다른 엔티티에서 같이 쓸수있게 한다.
 * 진짜 상속이 아니라 속성만 상속한다.
 */
@MappedSuperclass
@Getter
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdDate;
    /**
     * 업데이트 불가. 실수로 바꿔도 업데이트되지 않는다.
     */
    private LocalDateTime updatedDate;

    @PrePersist // JPA 제공 기능
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.updatedDate = now;
        /**
         * 첫 등록일지라도 updatedDate 에 값을 세팅해주는 것이 좋다.
         */
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedDate = LocalDateTime.now();
    }
}

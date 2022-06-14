package me.gogosing.jpa.file.entity;

import java.io.Serializable;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@ToString
@Audited
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseJpaEntity implements Serializable {

}

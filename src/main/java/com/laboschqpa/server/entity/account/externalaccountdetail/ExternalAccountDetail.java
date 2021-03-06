package com.laboschqpa.server.entity.account.externalaccountdetail;

import com.laboschqpa.server.entity.account.UserAcc;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(indexes = {@Index(columnList = "user_id")})
public abstract class ExternalAccountDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAcc userAcc;

    public abstract String getDetailString();

    public abstract void fillFromDetailString(String detailsString);
}

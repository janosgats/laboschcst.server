package com.laboschcst.server.repo;

import com.laboschcst.server.entity.account.UserAcc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface UserAccRepository extends JpaRepository<UserAcc, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select userAcc from UserAcc userAcc where userAcc.id = :id and userAcc.enabled = true")
    Optional<UserAcc> findByIdAndEnabledIsTrue_WithPessimisticWriteLock(long id);
}

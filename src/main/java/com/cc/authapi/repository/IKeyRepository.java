package com.cc.authapi.repository;

import com.cc.authapi.domain.Key;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface IKeyRepository extends JpaRepository<Key, UUID> {
}

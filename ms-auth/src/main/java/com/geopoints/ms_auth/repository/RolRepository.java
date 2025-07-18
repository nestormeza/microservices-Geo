package com.geopoints.ms_auth.repository;

import com.geopoints.ms_auth.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<RolRepository,Long> {
    Optional<RolEntity> findByName(String rol);
}

package com.services.template.repository;

import com.services.template.entities.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
public interface ParcelRepository extends JpaRepository<Parcel, UUID> {

    @Query(value = "SELECT * FROM PARCELS p where p.oid = :oid", nativeQuery = true)
    List<Parcel> findParcelByOrderId(@Param("oid") String oid);
}

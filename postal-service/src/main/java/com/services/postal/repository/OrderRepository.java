package com.services.postal.repository;

import com.services.postal.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface OrderRepository extends JpaRepository<Order, UUID> {

}

package com.services.template.repository;

import com.services.template.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface OrderRepository extends JpaRepository<Order, UUID> {

}

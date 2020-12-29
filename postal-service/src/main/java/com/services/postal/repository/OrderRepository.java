package com.services.postal.repository;

import com.services.postal.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query(value = "SELECT * FROM ORDERS AS p WHERE p.DATE_DELIVERED IS NULL", nativeQuery = true)
    List<Order> getActiveOrders();

}

package com.chris.allinone.solution.pay.timeout.db;

import com.chris.allinone.solution.pay.timeout.db.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Order, Long> {

    @Query(value = "select * from t_order where create_time <= :timeoutDateTime and order_status = 0", nativeQuery = true)
    List<Order> findTimeoutOrderList(@Param("timeoutDateTime") String timeoutDateTime);

    @Modifying
    @Query(value = "update t_order set order_status = :#{#order.orderStatus} where id = :#{#order.id}", nativeQuery = true)
    void saveOrderStatusById(@Param("order") Order order);
}

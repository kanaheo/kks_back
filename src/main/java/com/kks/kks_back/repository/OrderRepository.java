package com.kks.kks_back.repository;

import com.kks.kks_back.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 주문 조회나 유저별 검색 등 커스텀 메서드는 나중에 추가 가능
}

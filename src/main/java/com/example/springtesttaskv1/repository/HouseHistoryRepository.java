package com.example.springtesttaskv1.repository;

import com.example.springtesttaskv1.entity.model.HouseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseHistoryRepository extends JpaRepository<HouseHistory, Long> {

}

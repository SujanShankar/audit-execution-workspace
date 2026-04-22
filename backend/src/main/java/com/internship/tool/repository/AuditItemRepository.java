package com.internship.tool.repository;

import com.internship.tool.entity.AuditItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface AuditItemRepository extends JpaRepository<AuditItem, Long> {

    // 🔍 Search with pagination
    @Query("SELECT a FROM AuditItem a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<AuditItem> search(String keyword, Pageable pageable);

    // 📊 Filter by status (with pagination)
    Page<AuditItem> findByStatus(String status, Pageable pageable);

    // 📅 Date range filter (with pagination)
    Page<AuditItem> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    // 🧩 Combined filter (status + date range)
    @Query("SELECT a FROM AuditItem a WHERE a.status = :status AND a.createdAt BETWEEN :start AND :end")
    Page<AuditItem> findByStatusAndDateRange(String status, LocalDateTime start, LocalDateTime end, Pageable pageable);

    // ⚡ Soft delete filter
    Page<AuditItem> findByIsDeletedFalse(Pageable pageable);
}
package org.example.repository;

import org.example.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
    @Transactional
    @Modifying
    @Query("delete from History h where h.videoName = :video_name")
    void deleteByVideo_name(String video_name);

    Page<History> findAll(Pageable pageable);
}

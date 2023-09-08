package com.dmj.dmzdbtest.keyword.repository;

import com.dmj.dmzdbtest.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findByName(String name);
}

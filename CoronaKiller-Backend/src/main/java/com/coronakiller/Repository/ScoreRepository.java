package com.coronakiller.Repository;

import com.coronakiller.Entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

	List<Score> findByLastModifiedAtGreaterThanEqual(Date lastModifiedAt);
}

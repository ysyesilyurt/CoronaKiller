package com.coronakiller.Repository;

import com.coronakiller.Dto.ScoreDTO;
import com.coronakiller.Entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

	List<Score> findByLastModifiedAtGreaterThanEqual(Date lastModifiedAt);

	@Query(value ="SELECT tempTable.score, p.username" +
			"FROM player p,"+
			"(SELECT s.player_id as pid, sum(s.score) as score" +
			" FROM score s WHERE s.last_modified_at > :date" +
			"  GROUP by s.player_id ORDER by s.score DESC ) as tempTable"+
			"where tempTable.pid = p.id",
			nativeQuery = true)
	List<ScoreDTO> getTableByTime(@Param("date") Date date);
}

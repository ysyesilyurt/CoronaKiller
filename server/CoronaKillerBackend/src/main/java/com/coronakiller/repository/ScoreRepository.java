package com.coronakiller.repository;

import com.coronakiller.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

	@Query(value = "SELECT p.username, tempTable.score\n" +
			"FROM player AS p,\n" +
			"\t(SELECT s.player_id AS pid, SUM(s.score) AS score\n" +
			"\tFROM score s \n" +
			"\tWHERE s.created_at >= :date\n" +
			"\tGROUP BY s.player_id\n) AS tempTable\n" +
			"WHERE tempTable.pid = p.id\n" +
			"ORDER BY tempTable.score DESC", nativeQuery = true)
	List<Map<String, Long>> getScoreBoardWithDate(@Param("date") Date date);


	@Query(value = "SELECT p.username, tempTable.score\n" +
			"FROM player AS p,\n" +
			"\t(SELECT s.player_id AS pid, SUM(s.score) AS score\n" +
			"\tFROM score s \n" +
			"\tGROUP BY s.player_id\n) AS tempTable\n" +
			"WHERE tempTable.pid = p.id\n" +
			"ORDER BY tempTable.score DESC", nativeQuery = true)
	List<Map<String, Long>> getAllTimeScoreBoard();
}
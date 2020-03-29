package com.coronakiller.Service;

import com.coronakiller.Dto.ScoreDTO;
import com.coronakiller.Entity.Score;
import com.coronakiller.Mapper.ScoreMapper;
import com.coronakiller.Repository.ScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ScoreBoardService {

	private ScoreRepository scoreRepository;
	private ScoreMapper scoreMapper;

	public ScoreBoardService(ScoreRepository scoreRepository, ScoreMapper scoreMapper){
		this.scoreRepository = scoreRepository;
		this.scoreMapper = scoreMapper;
	}

	public List<ScoreDTO> getScoreBoard() {
		List<Score> allTimeScoresList = scoreRepository.findAll();
		return scoreMapper.toScoreDTOList(allTimeScoresList);
	}

	public List<ScoreDTO> getWeeklyScoreBoard() throws ParseException {

		Date previousDate = Date.valueOf(LocalDate.now().minusDays(7));
		List<ScoreDTO> weeklyScoresList = scoreRepository.getTableByTime((previousDate));
		return weeklyScoresList;
	}

	public List<ScoreDTO> getMonthlyScoreBoard() {
		Date previousDate = Date.valueOf(LocalDate.now().minusDays(30));
		List<ScoreDTO> monthlyScoresList = scoreRepository.getTableByTime((previousDate));
		return monthlyScoresList;
	}
}


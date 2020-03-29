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

	//TODO : Should we subtract 7 or 8 ?

	public List<ScoreDTO> getWeeklyScoreBoard() throws ParseException {

		Date previousWeekDate = Date.valueOf(LocalDate.now().minusDays(7));
		List<Score> weeklyScoresList = scoreRepository.findByLastModifiedAtGreaterThanEqual((previousWeekDate));
		return scoreMapper.toScoreDTOList(weeklyScoresList);
	}

	//TODO : Should we subtract 30 or 31 ?

	public List<ScoreDTO> getMonthlyScoreBoard() {
		Date previousWeekDate = Date.valueOf(LocalDate.now().minusDays(30));
		List<Score> weeklyScoresList = scoreRepository.findByLastModifiedAtGreaterThanEqual((previousWeekDate));
		return scoreMapper.toScoreDTOList(weeklyScoresList);
	}
}


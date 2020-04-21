package com.coronakiller.service;

import com.coronakiller.constant.APIConstants;
import com.coronakiller.dto.ResponseDTO;
import com.coronakiller.repository.ScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ScoreBoardService {

	private ScoreRepository scoreRepository;

	public ScoreBoardService(ScoreRepository scoreRepository) {
		this.scoreRepository = scoreRepository;
	}

	public Pair<HttpStatus, ResponseDTO> getScoreBoard(HashMap<String, String> boardType) {
		List<Map<String, Long>> result = null;
		if (boardType.containsValue("weekly")) {
			Date date = Date.valueOf(LocalDate.now().minusDays(7));
			result = scoreRepository.getScoreBoardWithDate(date.getTime());
		} else if (boardType.containsValue("monthly")) {
			Date date = Date.valueOf(LocalDate.now().minusDays(30));
			result = scoreRepository.getScoreBoardWithDate(date.getTime());
		} else {
			/* boardType == "all" or null; both fetches all times scoreboard */
			result = scoreRepository.getAllTimeScoreBoard();
		}
		return Pair.of(HttpStatus.OK, new ResponseDTO(result, null, APIConstants.RESPONSE_SUCCESS));
	}
}


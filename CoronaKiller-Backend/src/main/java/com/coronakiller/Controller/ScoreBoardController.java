package com.coronakiller.Controller;

import com.coronakiller.Dto.ScoreDTO;
import com.coronakiller.Service.ScoreBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/scoreboard", headers = "Accept=application/json")
public class ScoreBoardController {

	private ScoreBoardService scoreBoardService;

	public ScoreBoardController(ScoreBoardService scoreBoardService) {
		this.scoreBoardService = scoreBoardService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<ScoreDTO>> getScoreBoard(){
		List<ScoreDTO> allTimeScoresList = scoreBoardService.getScoreBoard();
		return ResponseEntity.ok().body(allTimeScoresList);
	}

	@GetMapping("/weekly")
	public ResponseEntity<List<ScoreDTO>> getWeeklyScoreBoard() throws ParseException {
		List<ScoreDTO> weeklyScoresList = scoreBoardService.getWeeklyScoreBoard();
		return ResponseEntity.ok().body(weeklyScoresList);
	}

	@GetMapping("/monthly")
	public ResponseEntity<List<ScoreDTO>> getMonthlyScoreBoard(){
		List<ScoreDTO> monthlyScoreBoard = scoreBoardService.getMonthlyScoreBoard();
		return ResponseEntity.ok().body(monthlyScoreBoard);
	}


	/**
	 *
	 * ALL methods are checked and working
	 * TODO: change endpoints to (?type=all or ?type=weekly or ?type=monthly)
	 * TODO:
	 * 	1- getScoreBoard -> ALL TIME SCOREBOARD
	 * 	2- getWeeklyScoreBoard -> Weekly
	 * 	3- getMonthlyScoreBoard -> monthly
	 * 		FOR ALL THESE OPERATIONS ONLY 1 ENDPOINT WILL BE USED:
	 * 			GET /api/scoreboard
 * 			QUERY STRING WILL BE USED TO IDENTIFY BOARD TYPE:
	 * 			GET /api/scoreboard (?type=all or ?type=weekly or ?type=monthly)
	 */
}


package com.coronakiller.Controller;

import com.coronakiller.Dto.PlayerDTO;
import com.coronakiller.Service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/scoreboard", headers = "Accept=application/json")
public class ScoreBoardController {

	public ScoreBoardController() {
	}

	/**
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


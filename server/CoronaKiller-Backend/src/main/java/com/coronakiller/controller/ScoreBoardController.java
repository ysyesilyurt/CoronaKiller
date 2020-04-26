package com.coronakiller.controller;

import com.coronakiller.dto.ResponseDTO;
import com.coronakiller.service.ScoreBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@Api(value = "ScoreBoard Resource REST Endpoints", description = "Contains endpoints to fetch different types of ScoreBoards according to player game scores recorded in CoronaKiller Game API")
@RestController
@RequestMapping(value = "/api/scoreboard", headers = "Accept=application/json")
public class ScoreBoardController {

	private final ScoreBoardService scoreBoardService;

	public ScoreBoardController(ScoreBoardService scoreBoardService) {
		this.scoreBoardService = scoreBoardService;
	}

	@GetMapping
	@ApiOperation(value = "Fetch Scoreboard according to given boardType (weekly, monthly or all) in query string.", response = ResponseDTO.class)
	public ResponseEntity<ResponseDTO> getScoreBoard(@RequestParam HashMap<String, String> boardType) {
		Pair<HttpStatus, ResponseDTO> response = scoreBoardService.getScoreBoard(boardType);
		return ResponseEntity.status(response.getFirst()).body(response.getSecond());
	}
}


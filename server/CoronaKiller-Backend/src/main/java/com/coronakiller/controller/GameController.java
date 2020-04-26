package com.coronakiller.controller;

import com.coronakiller.dto.GameSessionDTO;
import com.coronakiller.dto.ResponseDTO;
import com.coronakiller.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "Game Resource REST Endpoints", description = "Contains endpoints to interact with the gaming backend of CoronaKiller Game API")
@RestController
@RequestMapping(value = "/api/game", headers = "Accept=application/json")
public class GameController {

	private GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@GetMapping("/{playerId}")
	@ApiOperation(value = "Fetches the current game data of player by specified playerId." +
			" Namely returns a GameDataDTO object which contains PlayerDTO and a GameSessionDTO (if exists) that is" +
			" wrapped into ResponseDTO", response = ResponseDTO.class)
	public ResponseEntity<ResponseDTO> getGameDataById(@PathVariable Long playerId) {
		Pair<HttpStatus, ResponseDTO> response = gameService.getGameDataById(playerId);
		return ResponseEntity.status(response.getFirst()).body(response.getSecond());
	}

	@PostMapping("/start/{playerId}")
	@ApiOperation(value = "Start a new game session for player with specified id", response = ResponseDTO.class)
	public ResponseEntity<ResponseDTO> startNewGame(@PathVariable Long playerId) {
		Pair<HttpStatus, ResponseDTO> response = gameService.startNewGame(playerId);
		return ResponseEntity.status(response.getFirst()).body(response.getSecond());
	}

	@PutMapping("/update/{playerId}")
	@ApiOperation(value = "Update an ongoing game session after a level up", response = ResponseDTO.class)
	public ResponseEntity<ResponseDTO> updateGameSession(@PathVariable Long playerId,
														 @RequestBody GameSessionDTO newGameSessionDTO) {
		Pair<HttpStatus, ResponseDTO> response = gameService.updateGameSession(playerId, newGameSessionDTO);
		return ResponseEntity.status(response.getFirst()).body(response.getSecond());
	}

	@PutMapping("/finish/{playerId}")
	@ApiOperation(value = "Finish the game session of player specified by id", response = ResponseDTO.class)
	public ResponseEntity<ResponseDTO> finishGameSession(@PathVariable Long playerId,
														 @RequestBody GameSessionDTO finishedGameSessionDTO) {
		Pair<HttpStatus, ResponseDTO> response = gameService.finishGameSession(playerId, finishedGameSessionDTO);
		return ResponseEntity.status(response.getFirst()).body(response.getSecond());
	}
}


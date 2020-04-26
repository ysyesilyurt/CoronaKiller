package com.coronakiller.controller;

import com.coronakiller.constant.APIConstants;
import com.coronakiller.dto.PlayerDTO;
import com.coronakiller.dto.ResponseDTO;
import com.coronakiller.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(value = "Player Resource REST Endpoints", description = "Contains endpoints to interact with the Players/Users in CoronaKiller Game API")
@RestController
@RequestMapping(value = "/api/players", headers = "Accept=application/json")
public class PlayerController {

	private PlayerService playerService;

	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@GetMapping
	@ApiOperation(value = "Fetch all players", response = ResponseDTO.class)
	public ResponseEntity<ResponseDTO> getAllPlayers() {
		Pair<HttpStatus, ResponseDTO> response = playerService.getAllPlayers();
		return ResponseEntity.status(response.getFirst()).body(response.getSecond());
	}

	@GetMapping("/{playerId}")
	@ApiOperation(value = "Fetch player by specified id", response = ResponseDTO.class)
	public ResponseEntity<ResponseDTO> getPlayerById(@PathVariable Long playerId) {
		Pair<HttpStatus, ResponseDTO> response = playerService.getPlayerById(playerId);
		return ResponseEntity.status(response.getFirst()).body(response.getSecond());
	}

	@PostMapping(value = "/login")
	@ApiOperation(value = "Login player into system", response = ResponseDTO.class)
	public ResponseEntity<ResponseDTO> login(Authentication authRequest) {
		if (authRequest != null) {
			Pair<HttpStatus, ResponseDTO> response = playerService.handleLogin(authRequest);
			return ResponseEntity.status(response.getFirst()).body(response.getSecond());
		} else {
			log.warn("BAD REQUEST on login - missing Authorization Header in the request.");
			return ResponseEntity.badRequest().body(new ResponseDTO(null,
					"Missing Authorization Header in the request.", APIConstants.RESPONSE_FAIL));
		}
	}

	@PostMapping(value = "/register")
	@ApiOperation(value = "Register player into the system (New player creation)", response = ResponseDTO.class)
	public ResponseEntity<ResponseDTO> registerPlayer(@RequestBody PlayerDTO newPlayerDTO) {
		Pair<HttpStatus, ResponseDTO> response = playerService.registerPlayer(newPlayerDTO);
		return ResponseEntity.status(response.getFirst()).body(response.getSecond());
	}

	@DeleteMapping("/{playerId}")
	@ApiOperation(value = "Remove player from the system with id", response = ResponseDTO.class)
	public ResponseEntity<ResponseDTO> removePlayerById(@PathVariable("playerId") Long playerId) {
		Pair<HttpStatus, ResponseDTO> response = playerService.removePlayerById(playerId);
		return ResponseEntity.status(response.getFirst()).body(response.getSecond());
	}

	@PutMapping("/{playerId}")
	@ApiOperation(value = "Remove player from the system with id", response = ResponseDTO.class)
	public ResponseEntity<ResponseDTO> updatePlayerById(@PathVariable("playerId") Long playerId,
														@RequestBody PlayerDTO newPlayerDTO) {
		Pair<HttpStatus, ResponseDTO> response = playerService.updatePlayerById(playerId, newPlayerDTO);
		return ResponseEntity.status(response.getFirst()).body(response.getSecond());
	}
}


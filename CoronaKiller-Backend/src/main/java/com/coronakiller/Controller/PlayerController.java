package com.coronakiller.Controller;

import com.coronakiller.Dto.PlayerDTO;
import com.coronakiller.Entity.Player;
import com.coronakiller.Exception.ResourceNotFoundException;
import com.coronakiller.Service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/players", headers = "Accept=application/json")
public class PlayerController {

	private PlayerService playerService;

	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@GetMapping
	public ResponseEntity<List<PlayerDTO>> getAllPlayers(Pageable pageable) {
		List<PlayerDTO> playerList = playerService.getAllPlayers(pageable);
		return ResponseEntity.ok().body(playerList);
	}

	@GetMapping("/{playerId}")
	public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable long playerId){
		PlayerDTO playerDTO = playerService.getPlayerById(playerId);
		if(playerDTO != null){
			return ResponseEntity.ok().body(playerDTO);
		}
		else{
			throw new ResourceNotFoundException(String.format("Player cannot be found with id : %d", playerId));
		}
	}

	@PostMapping(value = "/login")
	public ResponseEntity<PlayerDTO> login(Authentication authRequest) {
		if (authRequest != null) {
			PlayerDTO playerDTO = playerService.handleLogin(authRequest);
			return ResponseEntity.ok().body(playerDTO);
		} else {
			log.warn("BAD REQUEST on login - missing Authorization Header in the request.");
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping
	public ResponseEntity<PlayerDTO> registerPlayer(@RequestBody Player newPlayer){
		PlayerDTO newPlayerDTO = playerService.registerPlayer(newPlayer);
		return ResponseEntity.ok().body(newPlayerDTO);
	}

	@DeleteMapping("/{playerId}")
	public ResponseEntity<String> removePlayerById(@PathVariable("playerId") long playerID){
		playerService.removePlayerByID(playerID);
		return ResponseEntity.ok().body("Player with " + playerID + " is deleted.");
	}

	@PutMapping("/{playerId}")
	public ResponseEntity<PlayerDTO> updatePlayerById(@PathVariable("playerId") long playerId,
													  @RequestBody PlayerDTO newPlayerDTO){
		PlayerDTO playerDTO = playerService.updatePlayerById(playerId, newPlayerDTO);
		return ResponseEntity.ok().body(playerDTO);
	}

	/**
	 * TODO:
	 * 	1- DONE -getPlayerById => GET /api/players/{id}
	 * 	2- CHECK -registerPlayer (create) => PUT /api/players
	 * 	3- DONE -removePlayer => DELETE /api/players/{id}
	 * 	4- CHECK -updatePlayer => POST /api/players/{id}
	 * 	5- LoginPlayer => WILL BE HANDLED IN ACCORDANCE WITH spring security
	 */
}


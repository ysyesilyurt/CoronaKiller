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

	/**
	 * TODO:
	 * 	1- getPlayerById => GET /api/players/{id}
	 * 	2- registerPlayer (create) => PUT /api/players
	 * 	3- removePlayer => DELETE /api/players/{id}
	 * 	4- updatePlayer => POST /api/players/{id}
	 * 	5- LoginPlayer => WILL BE HANDLED IN ACCORDANCE WITH spring security
	 */
}


package com.coronakiller.Controller;

import com.coronakiller.Entity.Player;
import com.coronakiller.Service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/players", headers = "Accept=application/json")
public class PlayerController {

	private PlayerService playerService;

	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@GetMapping
	public ResponseEntity<Page<Player>> getAllPlayers(Pageable pageable) {
		Page<Player> playerList = playerService.getAllPlayers(pageable);
		return ResponseEntity.ok().body(playerList);
	}

	/**
	 * TODO:
	 * 	1- getPlayerById
	 * 	2- registerPlayer (create)
	 * 	3- removePlayer
	 * 	4- updatePlayer
	 * 	5- LoginPlayer
	 */
}


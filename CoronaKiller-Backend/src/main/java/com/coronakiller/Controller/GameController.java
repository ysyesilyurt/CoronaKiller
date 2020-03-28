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
@RequestMapping(value = "/api/game", headers = "Accept=application/json")
public class GameController {


	public GameController() {
	}

	/**
	 * TODO:
	 * 	1- startGame - start with an initial game session
	 * 	2- continueGame - first check if game session is not null
	 * 	3- updateGame - Only gameSession update
	 * 	4- finishGame - update Score
	 */
}


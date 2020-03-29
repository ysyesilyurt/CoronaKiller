package com.coronakiller.Controller;

import com.coronakiller.Dto.GameSessionDTO;
import com.coronakiller.Entity.GameSession;
import com.coronakiller.Enum.ShipType;
import com.coronakiller.Service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/game", headers = "Accept=application/json")
public class GameController {

	private GameService gameService;

	public GameController(GameService gameService){
		this.gameService = gameService;
	}

	@PostMapping("start/{id}")
	public ResponseEntity<GameSessionDTO> startGame(){

		GameSessionDTO newGameSessionDTO = new GameSessionDTO();
		newGameSessionDTO.setCurrentLevel(1);
		newGameSessionDTO.setSessionScore(0);
		newGameSessionDTO.setShipHealth(100);
		newGameSessionDTO.setShipType(ShipType.ROOKIE);

		gameService.startGame(newGameSessionDTO);
		return ResponseEntity.ok().body(newGameSessionDTO);
	}

	@PutMapping("/continue/{id}")
	public ResponseEntity<GameSessionDTO> continueGame(@PathVariable("id") long gameSessionId){
		GameSessionDTO gameSessionDTO = gameService.continueGame(gameSessionId);
		return ResponseEntity.ok().body(gameSessionDTO);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<GameSessionDTO> updateGame(@PathVariable("id") long gameSessionId,
													 @RequestBody GameSessionDTO newGameSessionDTO){

		Integer newLevel = newGameSessionDTO.getCurrentLevel();
		Integer newSessionScore = newGameSessionDTO.getSessionScore();
		Integer newShipHealth = newGameSessionDTO.getShipHealth();
		ShipType newShipType = newGameSessionDTO.getShipType();


		GameSessionDTO gameSessionDTO = gameService.updateGame(gameSessionId,
				newLevel, newSessionScore, newShipHealth, newShipType);
		return ResponseEntity.ok().body(gameSessionDTO);
	}

	@PutMapping("/finish/{id}")
	public ResponseEntity<GameSessionDTO> finishGame(@PathVariable("id") long gameSessionId,
													 @RequestBody int newSessionScore){
		GameSessionDTO gameSessionDTO = gameService.finishGame(gameSessionId,newSessionScore);
		return ResponseEntity.ok().body(gameSessionDTO);
	}

	/**
	 * TODO:
	 * 	1- NO NEED - displayDashboard => GET /api/game/{id}
	 * 	1- startGame - start with an initial game session => POST /api/game/start/{id}
	 * 	2- continueGame - first check if game session is not null => PUT /api/game/continue/{id}
	 * 	3- updateGame - Only gameSession update => POST /api/game/update/{id}
	 * 	4- finishGame - update Score => POST /api/game/finish/{id}
	 */
}


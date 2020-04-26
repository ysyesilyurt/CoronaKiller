package com.coronakiller.ContollerTest;

import com.coronakiller.constant.APIConstants;
import com.coronakiller.controller.GameController;
import com.coronakiller.dto.GameDataDTO;
import com.coronakiller.dto.GameSessionDTO;
import com.coronakiller.dto.PlayerDTO;
import com.coronakiller.dto.ResponseDTO;
import com.coronakiller.enums.ShipType;
import com.coronakiller.service.GameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameControllerTest {

	@Autowired
	GameController gameController;

	@MockBean
	GameService gameService;

	@Test
	public void getGameDataByIdTest() {
		PlayerDTO playerDTO = PlayerDTO.builder().id(1L).username("yavuz").password("alper").totalScore(16515616L).gameSessionId(1L).build();
		GameSessionDTO gameSessionDTO = GameSessionDTO.builder().currentLevel(2).sessionScore(100L).shipHealth(100).shipType(ShipType.NORMAL).build();
		GameDataDTO gameDataDTO = GameDataDTO.builder().playerDTO(playerDTO).gameSessionDTO(gameSessionDTO).build();
		when(gameService.getGameDataById((long) 1)).thenReturn(Pair.of(HttpStatus.OK,
				new ResponseDTO(gameDataDTO, null, APIConstants.RESPONSE_SUCCESS)));
		Assert.assertEquals(gameDataDTO, gameController.getGameDataById((long) 1).getBody().getData());
	}

	@Test
	public void startNewGameTest() {
		Long playerId = (long) 1;
		when(gameService.startNewGame(playerId)).thenReturn(Pair.of(HttpStatus.OK, new ResponseDTO(null,
				String.format("Started a new game for player with id:%s", playerId), APIConstants.RESPONSE_SUCCESS)));
		Assert.assertEquals("Started a new game for player with id:" + playerId,
				gameController.startNewGame(playerId).getBody().getMessage());
	}

	@Test
	public void updateGameSessionTest() {
		Long playerId = (long) 1;
		GameSessionDTO newGameSessionDTO = new GameSessionDTO();
		newGameSessionDTO.setShipType(ShipType.ROOKIE);
		newGameSessionDTO.setShipHealth(100);
		newGameSessionDTO.setCurrentLevel(1);
		newGameSessionDTO.setSessionScore((long) 22);
		when(gameService.updateGameSession(playerId, newGameSessionDTO)).thenReturn(Pair.of(HttpStatus.OK, new ResponseDTO(null,
				String.format("Game Session of player with id:%s is successfully updated", playerId), APIConstants.RESPONSE_SUCCESS)));
		;
		Assert.assertEquals(APIConstants.RESPONSE_SUCCESS,
				gameController.updateGameSession(playerId, newGameSessionDTO).getBody().getResult());
	}

	@Test
	public void finishGameSessionTest() {
		Long playerId = (long) 2;
		GameSessionDTO finishedGameSessionDTO = new GameSessionDTO();
		finishedGameSessionDTO.setShipType(ShipType.NORMAL);
		finishedGameSessionDTO.setShipHealth(0);
		finishedGameSessionDTO.setCurrentLevel(3);
		finishedGameSessionDTO.setSessionScore((long) 1036);
		when(gameService.finishGameSession(playerId, finishedGameSessionDTO)).thenReturn(Pair.of(HttpStatus.OK, new ResponseDTO(null,
				String.format("Game Session of player with id:%s is finished and player's session score is successfully updated.", playerId),
				APIConstants.RESPONSE_SUCCESS)));
		Assert.assertEquals("Game Session of player with id:" + playerId + " is finished and player's session score is successfully updated.",
				gameController.finishGameSession(playerId, finishedGameSessionDTO).getBody().getMessage());
	}
}

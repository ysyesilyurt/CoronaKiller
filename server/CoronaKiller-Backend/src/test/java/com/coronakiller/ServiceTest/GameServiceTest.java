package com.coronakiller.ServiceTest;

import com.coronakiller.constant.APIConstants;
import com.coronakiller.dto.GameDataDTO;
import com.coronakiller.dto.GameSessionDTO;
import com.coronakiller.dto.PlayerDTO;
import com.coronakiller.dto.ResponseDTO;
import com.coronakiller.entity.GameSession;
import com.coronakiller.entity.Player;
import com.coronakiller.entity.Spaceship;
import com.coronakiller.enums.ShipType;
import com.coronakiller.repository.GameSessionRepository;
import com.coronakiller.repository.PlayerRepository;
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

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {

	@Autowired
	GameService gameService;

	@MockBean
	GameSessionRepository gameSessionRepository;

	@MockBean
	PlayerRepository playerRepository;

	@Test
	public void getGameDataByIdTest() {
		/* DTO */
		PlayerDTO playerDTO = PlayerDTO.builder().id(1L).username("yavuz").password("alper").totalScore(16515616L).gameSessionId(1L).build();
		GameSessionDTO gameSessionDTO = GameSessionDTO.builder().currentLevel(2).sessionScore(100L).shipHealth(100).shipType(ShipType.NORMAL).build();
		GameDataDTO gameDataDTO = GameDataDTO.builder().playerDTO(playerDTO).gameSessionDTO(gameSessionDTO).build();
		/* Entity */
		Player player = Player.builder().id(1L).username("yavuz").password("alper").totalScore(16515616L).build();
		Spaceship spaceship = Spaceship.builder().health(100).type(ShipType.NORMAL).build();
		GameSession gameSession = GameSession.builder().id(1L).player(player).currentLevel(2).sessionScore(100L).spaceship(spaceship).build();
		player.setGameSession(gameSession);

		when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
		when(gameSessionRepository.findById(1L)).thenReturn(Optional.of(gameSession));
		ResponseDTO response = gameService.getGameDataById(1L).getSecond();
		GameDataDTO responseGameData = ((GameDataDTO) response.getData());

		Assert.assertEquals(APIConstants.RESPONSE_SUCCESS, response.getResult());

		Assert.assertEquals(playerDTO.getId(), responseGameData.getPlayerDTO().getId());
		Assert.assertEquals(playerDTO.getUsername(), responseGameData.getPlayerDTO().getUsername());
		Assert.assertEquals(playerDTO.getPassword(), responseGameData.getPlayerDTO().getPassword());
		Assert.assertEquals(playerDTO.getTotalScore(), responseGameData.getPlayerDTO().getTotalScore());
		Assert.assertEquals(playerDTO.getGameSessionId(), responseGameData.getPlayerDTO().getGameSessionId());

		Assert.assertEquals(gameSessionDTO.getSessionScore(), responseGameData.getGameSessionDTO().getSessionScore());
		Assert.assertEquals(gameSessionDTO.getCurrentLevel(), responseGameData.getGameSessionDTO().getCurrentLevel());
		Assert.assertEquals(gameSessionDTO.getShipHealth(), responseGameData.getGameSessionDTO().getShipHealth());
		Assert.assertEquals(gameSessionDTO.getShipType(), responseGameData.getGameSessionDTO().getShipType());
	}
}

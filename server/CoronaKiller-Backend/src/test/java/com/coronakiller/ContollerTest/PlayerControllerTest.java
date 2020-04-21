package com.coronakiller.ContollerTest;

import com.coronakiller.constant.APIConstants;
import com.coronakiller.controller.PlayerController;
import com.coronakiller.dto.PlayerDTO;
import com.coronakiller.dto.ResponseDTO;
import com.coronakiller.service.PlayerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerControllerTest {

	@Autowired
	private PlayerController playerController;

	@MockBean
	private PlayerService playerService;

	@Test
	public void getAllPlayersTest(){
		PlayerDTO playerDTO1 = new PlayerDTO();
		PlayerDTO playerDTO2 = new PlayerDTO();
		playerDTO1.setUsername("Yavuz"); playerDTO1.setPassword("Yavuz'sPass");
		playerDTO1.setUsername("Alper"); playerDTO1.setPassword("Alper'sPass");
		List<PlayerDTO> playerDTOList = new ArrayList<>();
		playerDTOList.add(playerDTO1); playerDTOList.add(playerDTO2);
		when(playerService.getAllPlayers()).thenReturn(Pair.of(HttpStatus.OK,
				new ResponseDTO(playerDTOList, null, APIConstants.RESPONSE_SUCCESS)));
		Assert.assertEquals(playerDTOList, playerController.getAllPlayers().getBody().getData());
	}

	@Test
	public void getPlayerByIdTest(){
		PlayerDTO playerDTO1 = new PlayerDTO();
		playerDTO1.setUsername("Ceng453"); playerDTO1.setPassword("Ceng453'sPass");
		when(playerService.getPlayerById((long) 1)).thenReturn(Pair.of(HttpStatus.OK,
				new ResponseDTO(playerDTO1, null, APIConstants.RESPONSE_SUCCESS)));
		Assert.assertEquals(playerDTO1, playerController.getPlayerById((long)1).getBody().getData());
	}


	@Test
	public void registerPlayerTest(){
		PlayerDTO playerDTO = new PlayerDTO();
		playerDTO.setUsername("Ceng453"); playerDTO.setPassword("Ceng453'sPass");
		Pair<HttpStatus, ResponseDTO> response = Pair.of(HttpStatus.OK, new ResponseDTO(null,
				String.format("Username:%s already exists, please choose another and try again",
						playerDTO.getUsername()), APIConstants.RESPONSE_FAIL));
		when(playerService.registerPlayer(playerDTO)).thenReturn(response);
		Assert.assertEquals(response.getFirst(), playerController.registerPlayer(playerDTO).getStatusCode());
	}

	@Test
	public void removePlayerByIDTest(){
		Long playerId = (long)13;
		when(playerService.removePlayerByID((playerId))).thenReturn(Pair.of(HttpStatus.OK, new ResponseDTO(null,
				String.format("Player with id:%s successfully removed", playerId), APIConstants.RESPONSE_SUCCESS)));
		Assert.assertEquals("Player with id:13 successfully removed",
				playerController.removePlayerById(playerId).getBody().getMessage());

	}

	@Test
	public void updatePlayerByIdTest(){
		Long playerId = (long)7;
		PlayerDTO playerDTO = new PlayerDTO();
		playerDTO.setUsername("Ceng453"); playerDTO.setPassword("Ceng453'sPass");
		when(playerService.updatePlayerById(playerId, playerDTO)).thenReturn(Pair.of(HttpStatus.OK, new ResponseDTO(null,
				String.format("Player with id:%s successfully updated", playerId), APIConstants.RESPONSE_SUCCESS)));
		Assert.assertEquals(APIConstants.RESPONSE_SUCCESS,
				playerController.updatePlayerById(playerId, playerDTO).getBody().getResult());
	}

}

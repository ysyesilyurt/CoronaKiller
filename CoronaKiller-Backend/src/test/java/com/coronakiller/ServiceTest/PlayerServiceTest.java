package com.coronakiller.ServiceTest;

import com.coronakiller.Dto.PlayerDTO;
import com.coronakiller.Entity.Player;
import com.coronakiller.Mapper.PlayerMapper;
import com.coronakiller.Repository.PlayerRepository;
import com.coronakiller.Service.PlayerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerServiceTest {

	@Autowired
	PlayerService playerService;

	@Autowired
	PlayerMapper playerMapper;

	@MockBean
	PlayerRepository playerRepository;

	@Test
	public void getAllPlayersTest(){
		Player player = new Player();
		player.setUsername("CoronaFighters");
		player.setPassword("CoronaPass");

		Player player2 = new Player();
		player2.setUsername("goronooranaburanakorumha");
		player2.setPassword("goronooranaburanakorumhaPass");

		List<Player> playerList = new ArrayList<>();
		playerList.add(player);
		playerList.add(player2);

		when(playerRepository.findAll()).thenReturn(playerList);
		List<PlayerDTO> returnedList = (List<PlayerDTO>) playerService.getAllPlayers().getSecond().getData();
		Assert.assertEquals(playerMapper.toPlayerDTOList(playerList).size(),
				returnedList.size());
	}

	@Test
	public void registerPlayerTest(){
		PlayerDTO playerDTO = new PlayerDTO();
		playerDTO.setUsername("goronooranaburanakorumha");
		playerDTO.setPassword("goronooranaburanakorumhaPass");
		when(playerRepository.findByUsername(playerDTO.getUsername())).thenReturn(Optional.empty());
		Assert.assertEquals("Player is successfully created with username:" + playerDTO.getUsername(),
				playerService.registerPlayer(playerDTO).getSecond().getMessage());
	}

}

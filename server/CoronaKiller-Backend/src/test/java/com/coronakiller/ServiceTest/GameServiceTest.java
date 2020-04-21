package com.coronakiller.ServiceTest;

import com.coronakiller.constant.APIConstants;
import com.coronakiller.repository.GameSessionRepository;
import com.coronakiller.service.GameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

	@Test
	public void continueGameSessionTest(){
		Long playerId = (long)1;
		when(gameSessionRepository.findById(playerId)).thenReturn(Optional.empty());
		Assert.assertEquals(APIConstants.RESPONSE_FAIL,
				gameService.continueGameSession(playerId).getSecond().getResult());
	}
}

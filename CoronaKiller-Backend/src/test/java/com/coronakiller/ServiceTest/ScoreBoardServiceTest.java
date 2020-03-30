package com.coronakiller.ServiceTest;

import com.coronakiller.Dto.ResponseDTO;
import com.coronakiller.Repository.ScoreRepository;
import com.coronakiller.Service.ScoreBoardService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreBoardServiceTest {

	@Autowired
	ScoreBoardService scoreBoardService;

	@MockBean
	ScoreRepository scoreRepository;

	@Test
	public void getScoreBoardTest(){
		HashMap hashMap = new HashMap<String, String>();
		hashMap.put("all", "all");
		List<Map<String, Long>> result = new ArrayList<>();
		Map map = new HashMap();
		map.put("Yavuz", (long)100);
		map.put("Selim", (long)130);
		map.put("Alper", (long)80);
		result.add(map);
		Map map2 = new HashMap();
		map2.put("Player1", (long)18);
		map2.put("Player2", (long)17);
		map2.put("Player3", (long)9);
		result.add(map2);

		when(scoreRepository.getAllTimeScoreBoard()).thenReturn(result);
		ResponseDTO returnedValue = (ResponseDTO) scoreBoardService.getScoreBoard(hashMap).getSecond();
		Assert.assertEquals(result, returnedValue.getData());
	}
}

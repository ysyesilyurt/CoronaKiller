package com.coronakiller.ContollerTest;

import com.coronakiller.constant.APIConstants;
import com.coronakiller.controller.ScoreBoardController;
import com.coronakiller.dto.ResponseDTO;
import com.coronakiller.service.ScoreBoardService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScoreBoardControllerTest {

	@Autowired
	ScoreBoardController scoreBoardController;

	@MockBean
	ScoreBoardService scoreBoardService;

	@Test
	public void getScoreBoardTestWeekly(){
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("weekly", "weekly");
		List<Map<String, Long>> result = new ArrayList<>();
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("Yavuz", (long)100);
		map.put("Selim", (long)130);
		map.put("Alper", (long)80);
		result.add(map);
		Pair<HttpStatus, ResponseDTO> returnValue =
				Pair.of(HttpStatus.OK, new ResponseDTO(result, null, APIConstants.RESPONSE_SUCCESS));
		when(scoreBoardService.getScoreBoard(hashMap))
				.thenReturn(returnValue);
		Assert.assertEquals(returnValue.getSecond(), scoreBoardController.getScoreBoard(hashMap).getBody());
	}

	@Test
	public void getScoreBoardTestMonthly(){
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("monthly", "monthly");
		List<Map<String, Long>> result = new ArrayList<>();
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("Yavuz", (long)100);
		map.put("Selim", (long)130);
		map.put("Alper", (long)80);
		result.add(map);
		Map<String, Long> map2 = new HashMap<String, Long>();
		map2.put("Player1", (long)18);
		map2.put("Player2", (long)17);
		map2.put("Player3", (long)9);
		result.add(map2);
		Pair<HttpStatus, ResponseDTO> returnValue =
				Pair.of(HttpStatus.OK, new ResponseDTO(result, null, APIConstants.RESPONSE_SUCCESS));
		when(scoreBoardService.getScoreBoard(hashMap))
				.thenReturn(returnValue);
		Assert.assertEquals(returnValue.getSecond(), scoreBoardController.getScoreBoard(hashMap).getBody());
	}

	@Test
	public void getScoreBoardTestAll(){
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("all", "all");
		List<Map<String, Long>> result = new ArrayList<>();
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("Yavuz", (long)100);
		map.put("Selim", (long)130);
		map.put("Alper", (long)80);
		result.add(map);
		Map<String, Long> map2 = new HashMap<String, Long>();
		map2.put("Player1", (long)18);
		map2.put("Player2", (long)17);
		map2.put("Player3", (long)9);
		result.add(map2);
		Map<String, Long> map3 = new HashMap<String, Long>();
		map3.put("Player1", (long)18);
		map3.put("Player2", (long)17);
		map3.put("Player3", (long)9);
		result.add(map3);
		Pair<HttpStatus, ResponseDTO> returnValue =
				Pair.of(HttpStatus.OK, new ResponseDTO(result, null, APIConstants.RESPONSE_SUCCESS));
		when(scoreBoardService.getScoreBoard(hashMap))
				.thenReturn(returnValue);
		Assert.assertEquals(returnValue.getSecond(), scoreBoardController.getScoreBoard(hashMap).getBody());
	}
}

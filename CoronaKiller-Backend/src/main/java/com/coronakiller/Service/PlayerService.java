package com.coronakiller.Service;

import com.coronakiller.Dto.PlayerDTO;
import com.coronakiller.Entity.Player;
import com.coronakiller.Mapper.PlayerMapper;
import com.coronakiller.Repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Service
public class PlayerService {

	private PlayerRepository playerRepository;
	private PlayerMapper playerMapper;

	public PlayerService(PlayerRepository playerRepository,
						 PlayerMapper playerMapper) {
		this.playerRepository = playerRepository;
		this.playerMapper = playerMapper;
	}

	public List<PlayerDTO> getAllPlayers(Pageable pageable) {
//		Page<Player> playerList = playerRepository.findAll(pageable); TODO: check, remove if not gonna be used
		List<Player> playerList = playerRepository.findAll();
		return playerMapper.toPlayerDTOList(playerList);
	}
}


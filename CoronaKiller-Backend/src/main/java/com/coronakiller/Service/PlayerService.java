package com.coronakiller.Service;

import com.coronakiller.Entity.Player;
import com.coronakiller.Mapper.PlayerMapper;
import com.coronakiller.Repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

	public Page<Player> getAllPlayers(Pageable pageable) {
		Page<Player> playerList = playerRepository.findAll(pageable);
		return playerList;
	}
}


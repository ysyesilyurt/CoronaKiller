package com.coronakiller.Service;

import com.coronakiller.Dto.PlayerDTO;
import com.coronakiller.Entity.Player;
import com.coronakiller.Exception.ResourceNotFoundException;
import com.coronakiller.Mapper.PlayerMapper;
import com.coronakiller.Repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
		//Page<Player> playerList = playerRepository.findAll(pageable); TODO: check, remove if not gonna be used
		List<Player> playerList = playerRepository.findAll();
		return playerMapper.toPlayerDTOList(playerList);
	}

	public PlayerDTO getPlayerById(long playerId) {
		Optional<Player> queryResult = playerRepository.findById(playerId);
		if(queryResult.isPresent()){
			return playerMapper.toPlayerDTO(queryResult.get());
		}
		else{
			throw new ResourceNotFoundException(String.format("Player cannot be found with id : %d", playerId));
		}
	}

	public PlayerDTO handleLogin(Authentication authRequest) {
		User principal = (User) authRequest.getPrincipal();
		Optional<Player> player = playerRepository.findByUsername(principal.getUsername());
		if (player.isPresent()) {
			PlayerDTO playerDTO = playerMapper.toPlayerDTO(player.get());
			return playerDTO;
		}
		return null;
	}

	public void registerPlayer(PlayerDTO newPlayerDTO) {
		Player newPlayer = playerMapper.toPlayer(newPlayerDTO);
		playerRepository.save(newPlayer);
	}

	public void removePlayerByID(long playerID) {
		Optional<Player> queryResult = playerRepository.findById(playerID);
		if(queryResult.isPresent()) {
			playerRepository.delete(queryResult.get());
		}
	}

	public PlayerDTO updatePlayerById(long playerId,
									  PlayerDTO newPlayerDTO) {
		Optional<Player> queryResult = playerRepository.findById(playerId);
		if(queryResult.isEmpty()){
			throw new ResourceNotFoundException(String.format("Player is not found with id : %d", playerId));
		}

		Player player = queryResult.get();

		if(newPlayerDTO.getUsername() != null){
			player.setUsername(newPlayerDTO.getUsername());
		}

		if(newPlayerDTO.getPassword() != null){
			player.setPassword(newPlayerDTO.getPassword());
		}

		if(newPlayerDTO.getTotalScore() != null){
			player.setTotalScore(newPlayerDTO.getTotalScore());
		}
		playerRepository.save(player);
		return playerMapper.toPlayerDTO(player);
	}
}



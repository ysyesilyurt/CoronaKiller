package com.coronakiller.service;

import com.coronakiller.constant.APIConstants;
import com.coronakiller.dto.PlayerDTO;
import com.coronakiller.dto.ResponseDTO;
import com.coronakiller.entity.Player;
import com.coronakiller.mapper.PlayerMapper;
import com.coronakiller.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
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

	public Pair<HttpStatus, ResponseDTO> getAllPlayers() {
		List<PlayerDTO> playerDTOList = playerMapper.toPlayerDTOList(playerRepository.findAll());
		return Pair.of(HttpStatus.OK, new ResponseDTO(playerDTOList, null, APIConstants.RESPONSE_SUCCESS));
	}

	public Pair<HttpStatus, ResponseDTO> getPlayerById(Long playerId) {
		Optional<Player> queryResult = playerRepository.findById(playerId);
		if (queryResult.isPresent()) {
			PlayerDTO playerDTO = playerMapper.toPlayerDTO(queryResult.get());
			return Pair.of(HttpStatus.OK, new ResponseDTO(playerDTO, null, APIConstants.RESPONSE_SUCCESS));
		} else {
			log.warn("Player not found with id:{}", playerId);
			return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
					String.format("Player not found with id:%s", playerId), APIConstants.RESPONSE_FAIL));
		}
	}

	public Pair<HttpStatus, ResponseDTO> handleLogin(Authentication authRequest) {
		User principal = (User) authRequest.getPrincipal();
		Optional<Player> player = playerRepository.findByUsername(principal.getUsername());
		if (player.isPresent()) {
			PlayerDTO playerDTO = playerMapper.toPlayerDTO(player.get());
			return Pair.of(HttpStatus.OK, new ResponseDTO(playerDTO, null, APIConstants.RESPONSE_SUCCESS));
		}
		log.warn("User not found with username:{}", principal.getUsername());
		return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
				String.format("User not found with username:%s", principal.getUsername()), APIConstants.RESPONSE_FAIL));
	}

	public Pair<HttpStatus, ResponseDTO> registerPlayer(PlayerDTO playerDTO) {
		String validationResult = validateRegister(playerDTO);
		if (validationResult.equals("")) {
			if (!playerRepository.findByUsername(playerDTO.getUsername()).isPresent()) {
				Player player = playerMapper.toPlayer(playerDTO);
				playerRepository.save(player);
				PlayerDTO playerDTOtoRespond = playerMapper.toPlayerDTO(player);
				return Pair.of(HttpStatus.OK, new ResponseDTO(playerDTOtoRespond,
						String.format("Player is successfully created with username:%s", player.getUsername()), APIConstants.RESPONSE_SUCCESS));
			}
			else {
				log.warn("Username exists, could not complete user registration for username:{}", playerDTO.getUsername());
				return Pair.of(HttpStatus.OK, new ResponseDTO(null,
						String.format("Username already exists.\nPlease choose another and try again",
								playerDTO.getUsername()), APIConstants.RESPONSE_FAIL));
			}
		}
		else {
			return Pair.of(HttpStatus.OK, new ResponseDTO(null,
					String.format("Validation Error on registation.\nFollowing constraints must be met: %s", validationResult),
					APIConstants.RESPONSE_FAIL));
		}
	}

	private String validateRegister(PlayerDTO playerDTO) {
		StringBuilder result = new StringBuilder();
		if (playerDTO != null) {
			if (playerDTO.getId() != null)
				result.append("& Can not set ID field of a player");
			if (playerDTO.getUsername() == null)
				result.append("& Missing username field");
			if (playerDTO.getPassword() == null)
				result.append("& Missing password field");
			if (playerDTO.getTotalScore() != null)
				result.append("& Can not set total score of a player");
			if (playerDTO.getGameSessionId() != null)
				result.append("& Can not set game session of a player");
		}
		else {
			result.append("Missing player register credentials as a whole");
		}
		return result.toString();
	}

	public Pair<HttpStatus, ResponseDTO> removePlayerById(Long playerId) {
		Optional<Player> queryResult = playerRepository.findById(playerId);
		if (queryResult.isPresent()) {
			playerRepository.delete(queryResult.get());
			return Pair.of(HttpStatus.OK, new ResponseDTO(null,
					String.format("Player with id:%s successfully removed", playerId), APIConstants.RESPONSE_SUCCESS));
		}
		log.warn("Player not found with id:{}", playerId);
		return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
				String.format("Player not found with id:%s", playerId), APIConstants.RESPONSE_FAIL));
	}

	public Pair<HttpStatus, ResponseDTO> updatePlayerById(Long playerId, PlayerDTO playerDTO) {
		Optional<Player> queryResult = playerRepository.findById(playerId);
		if (queryResult.isEmpty()) {
			log.warn("Player not found with id:{}", playerId);
			return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
					String.format("Player not found with id:%s", playerId), APIConstants.RESPONSE_FAIL));
		}

		Player player = queryResult.get();

		if (playerDTO.getUsername() != null) {
			player.setUsername(playerDTO.getUsername());
		}

		if (playerDTO.getPassword() != null) {
			player.setPassword(playerDTO.getPassword());
		}

		if (playerDTO.getTotalScore() != null) {
			player.setTotalScore(playerDTO.getTotalScore());
		}
		playerRepository.save(player);
		return Pair.of(HttpStatus.OK, new ResponseDTO(null,
				String.format("Player with id:%s successfully updated", playerId), APIConstants.RESPONSE_SUCCESS));
	}
}



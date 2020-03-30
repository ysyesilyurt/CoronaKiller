package com.coronakiller.Service;

import com.coronakiller.Constant.APIConstants;
import com.coronakiller.Dto.GameSessionDTO;
import com.coronakiller.Dto.ResponseDTO;
import com.coronakiller.Entity.GameSession;
import com.coronakiller.Entity.Player;
import com.coronakiller.Entity.Score;
import com.coronakiller.Entity.Spaceship;
import com.coronakiller.Enum.ShipType;
import com.coronakiller.Exception.ResourceNotFoundException;
import com.coronakiller.Mapper.GameSessionMapper;
import com.coronakiller.Repository.GameSessionRepository;
import com.coronakiller.Repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class GameService {

	private GameSessionRepository gameSessionRepository;
	private GameSessionMapper gameSessionMapper;
	private PlayerRepository playerRepository;

	public GameService(GameSessionRepository gameSessionRepository,
					   GameSessionMapper gameSessionMapper,
					   PlayerRepository playerRepository) {
		this.gameSessionRepository = gameSessionRepository;
		this.gameSessionMapper = gameSessionMapper;
		this.playerRepository = playerRepository;
	}

	public Pair<HttpStatus, ResponseDTO> startNewGame(Long playerId) {
		Optional<Player> player = playerRepository.findById(playerId);
		if (player.isPresent()) {

			Spaceship spaceship = Spaceship.builder()
					.health(100)
					.type(ShipType.ROOKIE)
					.build();

			GameSession gameSession = GameSession.builder()
					.currentLevel(1)
					.sessionScore(0L)
					.spaceship(spaceship)
					.build();

			player.get().setGameSession(gameSession); //TODO
			playerRepository.save(player.get());
			return Pair.of(HttpStatus.OK, new ResponseDTO(null,
					String.format("Started a new game for player with id:%s", playerId), APIConstants.RESPONSE_SUCCESS));
		} else {
			log.warn("Player not exists with id:{}", playerId);
			return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
					String.format("Player not exists with id:%s", playerId), APIConstants.RESPONSE_FAIL));
		}
	}

	public Pair<HttpStatus, ResponseDTO> continueGameSession(Long playerId) {
		Optional<Player> player = playerRepository.findById(playerId);
		if (player.isPresent()) {
			if (player.get().getGameSession() != null) {
				GameSessionDTO gameSessionDTO = gameSessionMapper.toGameSessionDTO(player.get().getGameSession());
				return Pair.of(HttpStatus.OK, new ResponseDTO(gameSessionDTO, null, APIConstants.RESPONSE_SUCCESS));
			} else {
				log.warn("Player with id:{}, does not have an ongoing game session!", playerId);
				return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
						String.format("Player with id:%s, does not have an ongoing game session!", playerId), APIConstants.RESPONSE_FAIL));
			}
		} else {
			log.warn("Player not exists with id:{}", playerId);
			return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
					String.format("Player not exists with id:%s", playerId), APIConstants.RESPONSE_FAIL));
		}
	}

	public Pair<HttpStatus, ResponseDTO> updateGameSession(Long playerId, GameSessionDTO newGameSessionDTO) {
		Optional<Player> player = playerRepository.findById(playerId);
		if (player.isPresent()) {
			if (player.get().getGameSession() != null) {
				GameSession gameSession = player.get().getGameSession();
				gameSession.setCurrentLevel(newGameSessionDTO.getCurrentLevel());
				gameSession.setSessionScore(newGameSessionDTO.getSessionScore());

				Spaceship spaceship = gameSession.getSpaceship();
				spaceship.setHealth(newGameSessionDTO.getShipHealth());
				spaceship.setType(newGameSessionDTO.getShipType());

				gameSessionRepository.save(gameSession);
				return Pair.of(HttpStatus.OK, new ResponseDTO(null,
						String.format("Game Session of player with id:%s is successfully updated", playerId), APIConstants.RESPONSE_SUCCESS));
			} else {
				log.warn("Player with id:{}, does not have an ongoing game session!", playerId);
				return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
						String.format("Player with id:%s, does not have an ongoing game session!", playerId), APIConstants.RESPONSE_FAIL));
			}
		} else {
			log.warn("Player not exists with id:{}", playerId);
			return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
					String.format("Player not exists with id:%s", playerId), APIConstants.RESPONSE_FAIL));
		}
	}

	public Pair<HttpStatus, ResponseDTO> finishGameSession(Long playerId, GameSessionDTO finishedGameSessionDTO) {
		Optional<Player> player = playerRepository.findById(playerId);
		if (player.isPresent()) {
			if (player.get().getGameSession() != null) {
				Score newScore = Score.builder()
						.score(finishedGameSessionDTO.getSessionScore())
						.player(player.get())
						.build();

				player.get().getScoreList().add(newScore);
				player.get().setGameSession(null);
				playerRepository.save(player.get()); // TODO: CHECK CASCADE HERE!!!
				return Pair.of(HttpStatus.OK, new ResponseDTO(null,
						String.format("Game Session of player with id:%s is finished and player's session score is successfully updated.", playerId),
						APIConstants.RESPONSE_SUCCESS));
			} else {
				log.warn("Player with id:{}, does not have an ongoing game session!", playerId);
				return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
						String.format("Player with id:%s, does not have an ongoing game session!", playerId), APIConstants.RESPONSE_FAIL));
			}
		} else {
			log.warn("Player not exists with id:{}", playerId);
			return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
					String.format("Player not exists with id:%s", playerId), APIConstants.RESPONSE_FAIL));
		}
	}
}


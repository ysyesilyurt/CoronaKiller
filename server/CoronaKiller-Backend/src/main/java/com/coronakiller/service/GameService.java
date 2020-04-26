package com.coronakiller.service;

import com.coronakiller.constant.APIConstants;
import com.coronakiller.dto.GameDataDTO;
import com.coronakiller.dto.GameSessionDTO;
import com.coronakiller.dto.PlayerDTO;
import com.coronakiller.dto.ResponseDTO;
import com.coronakiller.entity.GameSession;
import com.coronakiller.entity.Player;
import com.coronakiller.entity.Score;
import com.coronakiller.entity.Spaceship;
import com.coronakiller.enums.ShipType;
import com.coronakiller.mapper.GameSessionMapper;
import com.coronakiller.repository.GameSessionRepository;
import com.coronakiller.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class GameService {

	private GameSessionRepository gameSessionRepository;
	private GameSessionMapper gameSessionMapper;
	private PlayerService playerService;
	private PlayerRepository playerRepository;

	public GameService(GameSessionRepository gameSessionRepository,
					   GameSessionMapper gameSessionMapper,
					   PlayerService playerService,
					   PlayerRepository playerRepository) {
		this.gameSessionRepository = gameSessionRepository;
		this.gameSessionMapper = gameSessionMapper;
		this.playerService = playerService;
		this.playerRepository = playerRepository;
	}

	/**
	 * Fetches the current Player and his/her ongoing the GameSession (if exists)
	 * information and wraps those DTOs in a GameDataDTO, then returns that
	 * @param gameSessionId
	 * @return Pair<HttpStatus, ResponseDTO>
	 */
	public Pair<HttpStatus, ResponseDTO> getGameDataById(Long playerId) {
		Pair<HttpStatus, ResponseDTO> result = playerService.getPlayerById(playerId);
		if (result.getSecond().getResult().equals("success")) {
			PlayerDTO playerDTO = ((PlayerDTO) result.getSecond().getData());
			if (playerDTO.getHasOngoingSession()) {
				Optional<GameSession> gameSession = gameSessionRepository.findById(playerDTO.getGameSessionId());
				GameSessionDTO gameSessionDTO = gameSessionMapper.toGameSessionDTO(gameSession.get());
				GameDataDTO gameDataDTO = new GameDataDTO(playerDTO, gameSessionDTO);
				return Pair.of(HttpStatus.OK, new ResponseDTO(gameDataDTO, null, APIConstants.RESPONSE_SUCCESS));
			} else {
				GameDataDTO gameDataDTO = new GameDataDTO(playerDTO, null);
				return Pair.of(HttpStatus.OK, new ResponseDTO(gameDataDTO, null, APIConstants.RESPONSE_SUCCESS));
			}
		} else {
			log.warn("Player not exists with id:{}", playerId);
			return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
					String.format("Player not exists with id:%s", playerId), APIConstants.RESPONSE_FAIL));
		}
	}

	/**
	 * Service for starting a new game session for player with id "playerId".
	 * If player has a previous game session from earlier, it gets overwritten by this service and a new one gets generated (with default values).
	 * @param playerId
	 * @return Pair<HttpStatus, ResponseDTO>
	 */
	public Pair<HttpStatus, ResponseDTO> startNewGame(Long playerId) {
		Optional<Player> player = playerRepository.findById(playerId);
		if (player.isPresent()) {

			GameSession gameSession;
			/* Player's previous Game Session gets overwritten (if exists) */
			if (player.get().getGameSession() != null) {
				gameSession = player.get().getGameSession();
				gameSession.setCurrentLevel(1);
				gameSession.setSessionScore(0L);
				gameSession.getSpaceship().setHealth(APIConstants.INITIAL_SHIP_HEALTH);
				gameSession.getSpaceship().setType(ShipType.ROOKIE);

			}
			else {
				Spaceship spaceship = Spaceship.builder()
						.health(APIConstants.INITIAL_SHIP_HEALTH)
						.type(ShipType.ROOKIE)
						.build();

				 gameSession = GameSession.builder()
						.currentLevel(1)
						.sessionScore(0L)
						.spaceship(spaceship)
						.build();
			}

			player.get().setGameSession(gameSession);
			player.get().setHasOngoingSession(true);
			playerRepository.save(player.get());
			GameSessionDTO gameSessionDTOtoRespond = gameSessionMapper.toGameSessionDTO(gameSession);
			return Pair.of(HttpStatus.OK, new ResponseDTO(gameSessionDTOtoRespond,
					String.format("Started a new game for player with id:%s", playerId), APIConstants.RESPONSE_SUCCESS));
		} else {
			log.warn("Player not exists with id:{}", playerId);
			return Pair.of(HttpStatus.NOT_FOUND, new ResponseDTO(null,
					String.format("Player not exists with id:%s", playerId), APIConstants.RESPONSE_FAIL));
		}
	}

	/**
	 * Updates specified session by the provided one.
	 * Updates are designed to be only performed when player finishes a level.
	 * Player's score is kept in game session score until the game session ends (i.e. game finishes)
	 * @param playerId
	 * @param newGameSessionDTO
	 * @return Pair<HttpStatus, ResponseDTO>
	 */
	public Pair<HttpStatus, ResponseDTO> updateGameSession(Long playerId, GameSessionDTO newGameSessionDTO) {
		Optional<Player> player = playerRepository.findById(playerId);
		if (player.isPresent()) {
			if (player.get().getHasOngoingSession() && player.get().getGameSession() != null) {
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

	/**
	 * Finishes the game session and insert the score of that session to te player's score list.
	 * Player's global score gets updated only if his/her game session has ended.
	 * Game finishes if player dies or player successfully finishes all levels of the game.
	 * @param playerId
	 * @param finishedGameSessionDTO
	 * @return Pair<HttpStatus, ResponseDTO>
	 */
	public Pair<HttpStatus, ResponseDTO> finishGameSession(Long playerId, GameSessionDTO finishedGameSessionDTO) {
		Optional<Player> player = playerRepository.findById(playerId);
		if (player.isPresent()) {
			if (player.get().getHasOngoingSession() && player.get().getGameSession() != null) {
				Score newScore = Score.builder()
						.score(finishedGameSessionDTO.getSessionScore())
						.player(player.get())
						.build();

				player.get().getScoreList().add(newScore);
				player.get().setTotalScore(player.get().getTotalScore() + newScore.getScore());
				player.get().setHasOngoingSession(false);
				playerRepository.save(player.get());
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


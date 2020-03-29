package com.coronakiller.Service;

import com.coronakiller.Dto.GameSessionDTO;
import com.coronakiller.Entity.GameSession;
import com.coronakiller.Enum.ShipType;
import com.coronakiller.Exception.ResourceNotFoundException;
import com.coronakiller.Mapper.GameSessionMapper;
import com.coronakiller.Repository.GameSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class GameService {

	private GameSessionRepository gameSessionRepository;
	private GameSessionMapper gameSessionMapper;

	public GameService(GameSessionRepository gameSessionRepository, GameSessionMapper gameSessionMapper) {
		this.gameSessionRepository = gameSessionRepository;
		this.gameSessionMapper = gameSessionMapper;
	}

	public void startGame(GameSessionDTO newGameSessionDTO) {
		GameSession newGameSession = gameSessionMapper.toGameSession(newGameSessionDTO);
		gameSessionRepository.save(newGameSession);
	}

	public GameSessionDTO continueGame(long gameSessionId) {
		Optional<GameSession> queryResult = gameSessionRepository.findById(gameSessionId);
		if(queryResult.isPresent()){
			return gameSessionMapper.toGameSessionDTO(queryResult.get());
		}
		else{
			throw new ResourceNotFoundException(String.format("GameSession cannot be found with id : %d", gameSessionId));
		}
	}

	public GameSessionDTO updateGame(long gameSessionId, Integer newLevel, Integer newSessionScore, Integer newShipHealth, ShipType newShipType) {
		Optional<GameSession> queryResult = gameSessionRepository.findById(gameSessionId);
		GameSession gameSession;
		if(queryResult.isPresent()){
			gameSession = queryResult.get();
		}
		else{
			throw new ResourceNotFoundException(String.format("GameSession cannot be found with id : %d", gameSessionId));
		}

		if(newLevel != null){
			gameSession.setCurrentLevel(newLevel);
		}

		if(newSessionScore != null){
			gameSession.setSessionScore(newSessionScore);
		}

		if(newShipHealth != null){
			gameSession.getSpaceship().setHealth(newShipHealth);
		}

		if(newShipType != null){
			gameSession.getSpaceship().setType(newShipType);
		}

		gameSessionRepository.save(gameSession);
		return gameSessionMapper.toGameSessionDTO(gameSession);
	}

	public GameSessionDTO finishGame(long gameSessionId,
									 int sessionScore) {
		Optional<GameSession> queryResult = gameSessionRepository.findById(gameSessionId);
		if(queryResult.isPresent()){
			GameSession gameSession = queryResult.get();
			gameSession.setSessionScore(sessionScore);
			return  gameSessionMapper.toGameSessionDTO(gameSession);
		}
		else{
			throw new ResourceNotFoundException(String.format("GameSession cannot be found with id : %d", gameSessionId));
		}
	}


}


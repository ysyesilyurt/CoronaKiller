package com.coronakiller.mapper;


import com.coronakiller.dto.GameSessionDTO;
import com.coronakiller.entity.GameSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface GameSessionMapper {
    List<GameSessionDTO> toGameSessionDTOList(List<GameSession> gameSessionList);

    @Mapping(source = "spaceship.health", target = "shipHealth")
    @Mapping(source = "spaceship.type", target = "shipType")
    GameSessionDTO toGameSessionDTO(GameSession gameSession);

    @Mapping(source = "shipHealth", target = "spaceship.health")
    @Mapping(source = "shipType", target = "spaceship.type")
    GameSession toGameSession(GameSessionDTO newGameSessionDTO);
}

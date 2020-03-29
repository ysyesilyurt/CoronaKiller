package com.coronakiller.Mapper;


import com.coronakiller.Dto.GameSessionDTO;
import com.coronakiller.Dto.PlayerDTO;
import com.coronakiller.Entity.GameSession;
import com.coronakiller.Entity.Player;
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
}

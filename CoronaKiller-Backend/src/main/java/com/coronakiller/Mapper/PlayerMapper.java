package com.coronakiller.Mapper;


import com.coronakiller.Dto.PlayerDTO;
import com.coronakiller.Entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface PlayerMapper {
    List<PlayerDTO> toPlayerDTOList(List<Player> playerList);

    @Mapping(source = "gameSession.id", target = "gameSessionId")
    PlayerDTO toPlayerDTO(Player player);

    @Mapping(source = "gameSessionId", target = "gameSession.id")
    Player toPlayer(PlayerDTO playerDTO);

}

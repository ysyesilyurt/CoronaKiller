package com.coronakiller.Mapper;


import com.coronakiller.Dto.PlayerDTO;
import com.coronakiller.Entity.Player;
import com.coronakiller.Enum.PlayerRole;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface PlayerMapper {
    List<PlayerDTO> toPlayerDTOList(List<Player> playerList);

    @Mapping(source = "gameSession.id", target = "gameSessionId")
    PlayerDTO toPlayerDTO(Player player);

    Player toPlayer(PlayerDTO playerDTO);

    @AfterMapping
    default void afterPlayerMapping(PlayerDTO playerDTO,
                                    @MappingTarget Player player) {
        player.setTotalScore(0L);
        player.setRole(PlayerRole.USER);
        player.setGameSession(null);
        player.setScoreList(null);
    }
}

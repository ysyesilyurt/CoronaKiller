package com.coronakiller.Mapper;

import com.coronakiller.Dto.PlayerDTO;
import com.coronakiller.Dto.ScoreDTO;
import com.coronakiller.Entity.Player;
import com.coronakiller.Entity.Score;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ScoreMapper {
	List<ScoreDTO> toScoreDTOList(List<Score> scoreList);

	@Mapping(source = "player.username", target = "playerUsername")
	ScoreDTO toScoreDTO(Score score);
}

package com.coronakiller.Repository;

import com.coronakiller.Entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
	@Override
	Page<Player> findAll(Pageable pageable);
}

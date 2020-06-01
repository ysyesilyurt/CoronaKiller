package com.coronakiller.repository;

import com.coronakiller.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
	Optional<Player> findByUsername(String username);
}

package me.gogosing.board.adapter.out.persistence.repository;

import me.gogosing.board.adapter.out.persistence.entity.BoardContentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardContentsRepository extends JpaRepository<BoardContentsEntity, Long> {

}

package gachon.mudang.image_upload.repository;

import gachon.mudang.image_upload.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}

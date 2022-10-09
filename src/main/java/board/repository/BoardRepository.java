package board.repository;

import board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Integer> {

    //JPA Repository

    //findBy(컬럼 이름)Containing
    //->컬럼에서 키워드가 포함된 것을 찾는다.
    //*키워드가 포함된 모든 데이터 검색
    //예)'홍길동'을 검색하고 싶을 때 '홍'만 입력해도 됨
    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
}

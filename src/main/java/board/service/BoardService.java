package board.service;

import board.entity.Board;
import board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public void wirte(Board board , MultipartFile file) throws Exception {

        String projectpath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String filename = uuid + "_" + file.getOriginalFilename();

        File savefile = new File(projectpath, filename);

        file.transferTo(savefile);

        board.setFilename(filename);
        board.setFilepath("/files/"+filename);

        boardRepository.save(board);
    }

    public Page<Board> boardlist(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board boardview(Integer id){

        return boardRepository.findById(id).get();
    }

    public Page<Board> boardserchlist(String searchKeyword, Pageable pageable){
        return boardRepository.findByTitleContaining(searchKeyword , pageable);
    }

    public void boarddelete(Integer id){

        boardRepository.deleteById(id);

    }
}

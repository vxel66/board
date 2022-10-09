package board.controller;

import board.entity.Board;
import board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class Boardcontroller {


    @Autowired
    private  BoardService boardService;

    @GetMapping("/board/write") // localhost:8080/board/write
    public String boardwritefrom() {

        return "boardwrite";
    }


    @PostMapping("/board/writepro")
    public String boardwritepro(Board board , Model model , MultipartFile file) throws Exception {
        if(board.getTitle()==""){
            model.addAttribute("message", "글 작성이 실패했습니다.");
            model.addAttribute("searchUrl", "/board/list");
            return "message";
        }
        boardService.wirte(board,file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");

        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }
    @GetMapping("/board/list")
    public String boardlist(Model model ,
                            @PageableDefault(page = 0, size=10 , sort = "id" , direction = Sort.Direction.DESC) Pageable pageable
                            ,String searchKeyword1){

        Page<Board> list = null;

        if(searchKeyword1 == null){
             list = boardService.boardlist(pageable);
        }else{
            list = boardService.boardserchlist(searchKeyword1,pageable);
        }

        int nowpage = list.getPageable().getPageNumber()+1;
        int startpage = Math.max(nowpage - 4,1);
        int endpage = Math.min(nowpage + 5,list.getTotalPages());

        model.addAttribute("nowpage", nowpage);
        model.addAttribute("startpage", startpage);
        model.addAttribute("endpage", endpage);
        model.addAttribute("list", list);

        return "boardlist";
    }
    @GetMapping("/board/view") // localhost:8080/board/view?id=1
    public String boardview(Model model,Integer id){
        model.addAttribute("board",boardService.boardview(id));
        return "boardview";

    }
    @GetMapping("/board/delete")
    public String boarddelete(Integer id){

        boardService.boarddelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/sample")
    public String sample(){
        return "boardlist";
    }
    @GetMapping("/board/modify/{id}")
    public String boardmodify(@PathVariable("id") Integer id , Model model){
        model.addAttribute("board", boardService.boardview(id));
        return "boardmodify";
    }
    @PostMapping("/board/update/{id}")
    public String boardupdate(@PathVariable("id") Integer id ,Board board,MultipartFile file) throws Exception {

        Board boardtemp = boardService.boardview(id);

        boardtemp.setTitle(board.getTitle());
        boardtemp.setContent(board.getContent());

        boardService.wirte(boardtemp,file);

        return "redirect:/board/list";
    }

}

package co.kr.datapia.interfaces;

import co.kr.datapia.application.BoardService;
import co.kr.datapia.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board")
    public List<Board> list() {
        List<Board> boards = boardService.getBoards();

        return boards;
    }

    @GetMapping("/board/{boardId}")
    public Board detail(@PathVariable("boardId") Long boardId ){

        Board board = boardService.getBoard(boardId);
        return board;
    }

    @PostMapping("/board")
    public ResponseEntity<?> create(
            @Valid @RequestBody Board resource)
            throws URISyntaxException {

        Board board = boardService.addBoard(
                Board.builder()
                        .content(resource.getContent())
                        .time(resource.getTime())
                        .build());

        URI location = new URI("/board/" + board.getBoardId());
        return ResponseEntity.created(location).body("{}");
    }
    @PatchMapping("/board/{boardId}")
    public String update(@PathVariable("boardId") Long boardId,
                         @Valid @RequestBody Board resource){
        String content = resource.getContent();
        String time = resource.getTime();
        boardService.updateBoard(boardId, content, time);

        return "{}";
    }
    @DeleteMapping
    public void delete(
            @RequestParam Long boardId){
        boardService.delete(boardId);
    }
}

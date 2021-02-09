package co.kr.datapia.application;

import co.kr.datapia.domain.Board;
import co.kr.datapia.domain.BoardNotFoundException;
import co.kr.datapia.domain.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BoardService {

    private  BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository){
                this.boardRepository = boardRepository;
    }

    // 게시판 아이디로 검색
    public List<Board> getBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards;
    }
    public Board getBoard (Long boardId){

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() ->new BoardNotFoundException((boardId)));
        return board;
    }

    public Board addBoard(Board board) {
        return boardRepository.save(board);
    }

    @Transactional
    public Board updateBoard(Long boardId, String content, String time) {
        //TODO: update Board...

        Board board = boardRepository.findById(boardId).orElse(null);

        board.updateInformation(content , time);

        return board;
    }

    public void delete(Long boardId){
        boardRepository.deleteById(boardId);
    }
}

package co.kr.datapia.domain;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException(long boardId){

        super("Could not find board "+ boardId);
    }

}

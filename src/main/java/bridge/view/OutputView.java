package bridge.view;

import static bridge.constant.BridgeConstant.LOWER_BLOCK;
import static bridge.constant.BridgeConstant.UPPER_BLOCK;

import bridge.domain.GameResult;
import bridge.domain.MoveResult;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {

    private static final String ANSWER_IS_RIGHT = "O";
    private static final String ANSWER_IS_WRONG = "X";
    private static final String BLANK = " ";
    private static final String GAME_CLEAR_SUCCESS = "성공";
    private static final String GAME_CLEAR_FAIL = "실패";

    private final List<String> upperBlocks = new ArrayList<>();
    private final List<String> lowerBlocks = new ArrayList<>();

    public void printMap(MoveResult moveResult) {
        String answer = moveResult.getMessage();
        String resultMark = getResultMark(moveResult.isSuccess());
        setBlocks(answer, resultMark);
        printJoiningMessage();
    }

    private void printJoiningMessage() {
        String upperBlocksMessage = String.join(" | ", upperBlocks);
        String lowerBlocksMessage = String.join(" | ", lowerBlocks);
        System.out.printf("[ %s ]\n", upperBlocksMessage);
        System.out.printf("[ %s ]\n", lowerBlocksMessage);
    }

    private String getResultMark(boolean isCorrect) {
        if (isCorrect) {
            return ANSWER_IS_RIGHT;
        }
        return ANSWER_IS_WRONG;
    }

    private void setBlocks(String answer, String resultMark) {
        if (answer.equals(UPPER_BLOCK)) {
            upperBlocks.add(resultMark);
            lowerBlocks.add(BLANK);
        }

        if (answer.equals(LOWER_BLOCK)) {
            upperBlocks.add(BLANK);
            lowerBlocks.add(resultMark);
        }
    }


    public void clear() {
        upperBlocks.clear();
        lowerBlocks.clear();
    }

    public void printResult(GameResult gameResult) {
        System.out.println("최종 게임 결과");
        printJoiningMessage();
        System.out.printf("게임 성공 여부: %s\n", getIsGameClear(gameResult));
        System.out.printf("총 시도한 횟수: %s\n", gameResult.getTryCount());
    }

    private String getIsGameClear(GameResult gameResult) {
        if (gameResult.isGameClear()) {
            return GAME_CLEAR_SUCCESS;
        }
        return GAME_CLEAR_FAIL;
    }
}

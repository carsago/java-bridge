package bridge.view;

import bridge.domain.BridgeSize;
import bridge.domain.command.MoveCommand;
import bridge.domain.command.RetryCommand;
import camp.nextstep.edu.missionutils.Console;

/**
 * 사용자로부터 입력을 받는 역할을 한다.
 */
public class InputView {


    public InputView() {
        System.out.println("다리 건너기 게임을 시작합니다.");
    }

    public BridgeSize readBridgeSize() {
        System.out.println("다리의 길이를 입력해주세요.");
        int size = convertInputToInt(Console.readLine());
        return new BridgeSize(size);
    }

    public MoveCommand readMoving() {
        System.out.println("이동할 칸을 선택해주세요. (위: U, 아래: D)");
        return new MoveCommand(Console.readLine());
    }

    public RetryCommand readGameCommand() {
        System.out.println("게임을 다시 시도할지 여부를 입력해주세요. (재시도: R, 종료: Q)");
        return new RetryCommand(Console.readLine());
    }

    private int convertInputToInt(String input) {
        if (isNotNumber(input)) {
            throw new IllegalArgumentException("숫자를 입력해주세요. 입력값 : " + input);
        }
        return Integer.parseInt(input);
    }

    private boolean isNotNumber(String input) {
        return !input.chars().allMatch(Character::isDigit);
    }
}

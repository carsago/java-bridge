package bridge.domain.vo;

import static bridge.domain.vo.MoveCommand.MOVE_TO_LOWER_BLOCK;
import static bridge.domain.vo.MoveCommand.MOVE_TO_UPPER_BLOCK;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MoveCommandTest {

    @DisplayName("이동 명령 메시지가")
    @Nested
    class CreateMoveCommand {

        @DisplayName("올바른 값일시 MoveCommand를 정상적으로 생성한다")
        @ValueSource(strings = {MOVE_TO_UPPER_BLOCK, MOVE_TO_LOWER_BLOCK})
        @ParameterizedTest
        void through_right_value_is_ok(String rightValue) {
            assertThatNoException().isThrownBy(() -> new MoveCommand(rightValue));
        }

        @DisplayName("올바른 값이 아니라면 예외를 반환한다.")
        @Test
        void through_wrong_value_is_exception() {
            assertThatThrownBy(() -> new MoveCommand("wrongValue"))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
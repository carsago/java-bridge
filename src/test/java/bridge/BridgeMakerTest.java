package bridge;

import static bridge.domain.command.MoveCommand.MOVE_TO_LOWER_BLOCK;
import static bridge.domain.command.MoveCommand.MOVE_TO_UPPER_BLOCK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BridgeMakerTest {

    private final BridgeMaker bridgeMaker = new BridgeMaker(new BridgeRandomNumberGenerator());

    @DisplayName("입력한 길이와 다리 길이가 일치한다")
    @ValueSource(ints = 10)
    @ParameterizedTest
    void test1(int anyValue) {
        List<String> bridge = bridgeMaker.makeBridge(anyValue);
        assertThat(bridge.size()).isEqualTo(anyValue);
    }

    @DisplayName(MOVE_TO_UPPER_BLOCK + ", " + MOVE_TO_LOWER_BLOCK + " 2개의 값만 생성된다")
    @ValueSource(ints = 10)
    @ParameterizedTest
    void test2(int anyValue) {
        List<String> bridge = bridgeMaker.makeBridge(anyValue);
        assertThat(bridge.stream()
                .allMatch(value -> value.equals(MOVE_TO_UPPER_BLOCK) || value.equals(MOVE_TO_LOWER_BLOCK))).isTrue();
    }

    @DisplayName("numberGenerator를 통해 들어온 값이")
    @Nested
    class ChangeNumber {

        @DisplayName("1이면 " + MOVE_TO_UPPER_BLOCK + "를 반환한다.")
        @Test
        void test1() {
            BridgeMaker bridgeMaker = new BridgeMaker(() -> 1);
            boolean result = isCreateSpecificValue(bridgeMaker, MOVE_TO_UPPER_BLOCK);
            assertThat(result).isTrue();
        }

        @DisplayName("0이면 " + MOVE_TO_LOWER_BLOCK + "를 반환한다.")
        @Test
        void test2() {
            BridgeMaker bridgeMaker = new BridgeMaker(() -> 0);
            boolean result = isCreateSpecificValue(bridgeMaker, MOVE_TO_LOWER_BLOCK);
            assertThat(result).isTrue();
        }

        @DisplayName("그외의 값이면 예외를 반환한다.")
        @Test
        void test3() {
            BridgeMaker bridgeMaker = new BridgeMaker(() -> 2);
            assertThatThrownBy(() -> bridgeMaker.makeBridge(10))
                    .isInstanceOf(IllegalStateException.class);
        }

        private boolean isCreateSpecificValue(BridgeMaker bridgeMaker,
                                              String moveToUpperBlock) {
            List<String> bridge = bridgeMaker.makeBridge(10);
            return bridge.stream()
                    .allMatch(value -> value.equals(moveToUpperBlock));
        }
    }
}
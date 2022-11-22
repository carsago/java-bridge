package bridge.domain;

import bridge.domain.generator.BridgeMaker;
import bridge.domain.vo.BridgeSize;
import bridge.domain.vo.MoveCommand;
import bridge.domain.vo.RetryCommand;
import bridge.dto.GameResult;
import bridge.dto.MoveResult;
import java.util.List;

public class BridgeGame {

    private final BridgeGameRepository bridgeGameRepository;
    private final Bridge bridge;

    public BridgeGame(BridgeGameRepository bridgeGameRepository, Bridge bridge) {
        this.bridgeGameRepository = bridgeGameRepository;
        this.bridge = bridge;
    }

    public static BridgeGame of(BridgeMaker bridgeMaker,
                                BridgeSize bridgeSize) {
        List<String> blocks = bridgeMaker.makeBridge(bridgeSize.getSize());
        return new BridgeGame(new BridgeGameRepository(), new Bridge(blocks));
    }

    public MoveResult move(MoveCommand command) {
        String message = command.getMessage();
        boolean isSuccess = bridge.isMoveSuccess(bridgeGameRepository.getRound(), message);
        return new MoveResult(message, isSuccess);
    }

    public boolean retry(RetryCommand retryCommand) {
        if (retryCommand.isRetry()) {
            bridgeGameRepository.retry();
            return true;
        }
        return false;
    }

    public boolean isGameClear() {
        if (bridgeGameRepository.isFinalRound(bridge)) {
            return true;
        }
        bridgeGameRepository.addOneToRound();
        return false;
    }

    public GameResult closeGame() {
        return new GameResult(bridgeGameRepository.getTryCount(), bridgeGameRepository.isFinalRound(bridge));
    }
}

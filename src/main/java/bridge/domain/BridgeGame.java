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

    public BridgeGame(BridgeGameRepository bridgeGameRepository) {
        this.bridgeGameRepository = bridgeGameRepository;
    }

    public Bridge getBridge(BridgeMaker bridgeMaker,
                            BridgeSize bridgeSize) {
        int size = bridgeSize.getSize();
        bridgeGameRepository.init(size);
        List<String> blocks = bridgeMaker.makeBridge(size);
        return new Bridge(blocks);
    }

    public MoveResult move(Bridge bridge,
                           MoveCommand command) {
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
        if (bridgeGameRepository.isFinalRound()) {
            return true;
        }
        bridgeGameRepository.addOneToRound();
        return false;
    }

    public GameResult closeGame() {
        return new GameResult(bridgeGameRepository.getTryCount(), bridgeGameRepository.isFinalRound());
    }
}

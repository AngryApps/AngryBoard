package br.com.angryapps.angry.api.vm.requests;

import java.util.UUID;

public class ChangeCardPositionRequest {

    private UUID previousCardId;
    private UUID nextCardId;
    private UUID targetColumnId;

    public UUID getNextCardId() {
        return nextCardId;
    }

    public void setNextCardId(UUID nextCardId) {
        this.nextCardId = nextCardId;
    }

    public UUID getPreviousCardId() {
        return previousCardId;
    }

    public void setPreviousCardId(UUID previousCardId) {
        this.previousCardId = previousCardId;
    }

    public UUID getTargetColumnId() {
        return targetColumnId;
    }

    public void setTargetColumnId(UUID targetColumnId) {
        this.targetColumnId = targetColumnId;
    }
}

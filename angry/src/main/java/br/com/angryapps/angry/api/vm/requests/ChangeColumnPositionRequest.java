package br.com.angryapps.angry.api.vm.requests;

import java.util.UUID;

public class ChangeColumnPositionRequest {

    private UUID previousColumnId;
    private UUID nextColumnId;

    public UUID getNextColumnId() {
        return nextColumnId;
    }

    public void setNextColumnId(UUID nextColumnId) {
        this.nextColumnId = nextColumnId;
    }

    public UUID getPreviousColumnId() {
        return previousColumnId;
    }

    public void setPreviousColumnId(UUID previousColumnId) {
        this.previousColumnId = previousColumnId;
    }
}

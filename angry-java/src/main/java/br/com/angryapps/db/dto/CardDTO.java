package br.com.angryapps.db.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class CardDTO implements Serializable {

    private UUID id;
    private String title;
    private String description;
    private double position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID column_id;

    public UUID getColumn_id() {
        return column_id;
    }

    public void setColumn_id(UUID column_id) {
        this.column_id = column_id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

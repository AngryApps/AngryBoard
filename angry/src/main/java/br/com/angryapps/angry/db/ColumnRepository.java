package br.com.angryapps.angry.db;

import br.com.angryapps.angry.models.Column;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ColumnRepository extends JpaRepository<Column, UUID> {
}

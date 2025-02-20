package br.com.angryapps.angry.api.mappers;

import br.com.angryapps.angry.api.vm.ColumnVM;
import br.com.angryapps.angry.api.vm.requests.PatchColumn;
import br.com.angryapps.angry.models.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ColumnMapper {

    @Autowired
    private CardMapper cardMapper;

    public ColumnVM mapToColumnVM(Column column) {
        ColumnVM columnVM = new ColumnVM();
        columnVM.setId(column.getId());
        columnVM.setTitle(column.getTitle());
        columnVM.setDescription(column.getDescription());
        columnVM.setCreatedAt(column.getCreatedAt());
        columnVM.setUpdatedAt(column.getUpdatedAt());
        columnVM.setPosition(column.getPosition());

        if (column.getCards() != null) {
            columnVM.setCards(column.getCards().stream().map(cardMapper::mapToCardVM).toList());
        }

        return columnVM;
    }

    public Column mapToColumn(ColumnVM columnVM) {
        Column column = new Column();
        column.setId(columnVM.getId());
        column.setTitle(columnVM.getTitle());
        column.setDescription(columnVM.getDescription());
        column.setCreatedAt(columnVM.getCreatedAt());
        column.setUpdatedAt(columnVM.getUpdatedAt());
        column.setPosition(columnVM.getPosition());

        if (columnVM.getCards() != null) {
            column.setCards(columnVM.getCards().stream().map(c -> cardMapper.mapToCard(c, column)).toList());
        }

        return column;
    }

    public Column patchColumn(PatchColumn patchColumn, Column column) {
        if (patchColumn.getTitle() != null) {
            column.setTitle(patchColumn.getTitle());
        }

        if (patchColumn.getDescription() != null) {
            column.setDescription(patchColumn.getDescription());
        }

        if (patchColumn.getPosition() != null) {
            column.setPosition(patchColumn.getPosition());
        }

        return column;
    }
}

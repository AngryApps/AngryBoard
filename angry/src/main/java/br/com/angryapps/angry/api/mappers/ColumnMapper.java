package br.com.angryapps.angry.api.mappers;

import br.com.angryapps.angry.api.vm.ColumnVM;
import br.com.angryapps.angry.models.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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
        columnVM.setCards(column.getCards().stream().map(cardMapper::mapToCardVM).collect(Collectors.toList()));
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
        column.setCards(columnVM.getCards().stream().map(
                cardVM -> cardMapper.mapToCard(cardVM, column)).collect(Collectors.toList()));

        return column;

    }
}

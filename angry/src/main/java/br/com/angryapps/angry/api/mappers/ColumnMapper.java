package br.com.angryapps.angry.api.mappers;

import br.com.angryapps.angry.api.vm.CardVM;
import br.com.angryapps.angry.api.vm.ColumnVM;
import br.com.angryapps.angry.api.vm.requests.PatchColumn;
import br.com.angryapps.angry.models.Card;
import br.com.angryapps.angry.models.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

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

        List<Card> cards = column.getCards();
        if (cards != null) {
            cards.sort(Comparator.comparingDouble(Card::getPosition));
            columnVM.setCards(cards.stream().map(cardMapper::mapToCardVM).toList());
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

        List<CardVM> cards = columnVM.getCards();
        if (cards != null) {
            cards.sort(Comparator.comparingDouble(CardVM::getPosition));
            column.setCards(cards.stream().map(c -> cardMapper.mapToCard(c, column)).toList());
        }

        return column;
    }

    public void patchColumn(PatchColumn patchColumn, Column column) {
        if (patchColumn.getTitle() != null) {
            column.setTitle(patchColumn.getTitle());
        }

        if (patchColumn.getDescription() != null) {
            column.setDescription(patchColumn.getDescription());
        }

        column.setUpdatedAt(LocalDateTime.now());
    }
}

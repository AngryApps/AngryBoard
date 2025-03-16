package br.com.angryapps.api.mappers;

import br.com.angryapps.api.vm.CardVM;
import br.com.angryapps.api.vm.ColumnVM;
import br.com.angryapps.api.vm.requests.PatchColumn;
import br.com.angryapps.db.dto.CardDTO;
import br.com.angryapps.db.dto.ColumnDTO;
import br.com.angryapps.utils.Util;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.jvnet.hk2.annotations.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Singleton
public class ColumnMapper {

    private final CardMapper cardMapper;

    @Inject
    public ColumnMapper(CardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }

    public ColumnVM mapToColumnVM(ColumnDTO column) {
        ColumnVM columnVM = new ColumnVM();
        columnVM.setId(column.getId());
        columnVM.setTitle(column.getTitle());
        columnVM.setDescription(column.getDescription());
        columnVM.setCreatedAt(column.getCreatedAt());
        columnVM.setUpdatedAt(column.getUpdatedAt());
        columnVM.setPosition(column.getPosition());

        return columnVM;
    }

    public ColumnVM mapToColumnVM(ColumnDTO column, List<CardDTO> cardsDto) {
        ColumnVM columnVM = mapToColumnVM(column);

        List<CardVM> cards = Util.safeList(cardsDto).stream()
                                 .map(cardMapper::mapToCardVM)
                                 .toList();

        columnVM.setCards(cards);

        return columnVM;
    }

    public ColumnDTO mapToColumn(ColumnVM columnVM) {
        ColumnDTO column = new ColumnDTO();
        column.setId(columnVM.getId());
        column.setTitle(columnVM.getTitle());
        column.setDescription(columnVM.getDescription());
        column.setCreatedAt(columnVM.getCreatedAt());
        column.setUpdatedAt(columnVM.getUpdatedAt());
        column.setPosition(columnVM.getPosition());

        return column;
    }

    public void patchColumn(PatchColumn patchColumn, ColumnDTO column) {
        if (patchColumn.getTitle() != null) {
            column.setTitle(patchColumn.getTitle());
        }

        if (patchColumn.getDescription() != null) {
            column.setDescription(patchColumn.getDescription());
        }

        column.setUpdatedAt(LocalDateTime.now());
    }
}

package br.com.angryapps.angry.api.resources;

import br.com.angryapps.angry.api.mappers.CardMapper;
import br.com.angryapps.angry.api.vm.CardVM;
import br.com.angryapps.angry.db.CardRepository;
import br.com.angryapps.angry.db.ColumnRepository;
import br.com.angryapps.angry.models.Card;
import br.com.angryapps.angry.models.Column;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cards")
public class CardResource {

    private CardRepository cardRepository;
    private ColumnRepository columnRepository;
    private CardMapper cardMapper;

    @Autowired
    public CardResource(CardRepository cardRepository, ColumnRepository columnRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.columnRepository = columnRepository;
        this.cardMapper = cardMapper;
    }

    @PostMapping
    CardVM createCard(@Valid @RequestBody CardVM cardVM) {
        Column column = columnRepository.findById(cardVM.getColumnId())
                                                .orElseThrow(() -> new RuntimeException("Column not found"));

        Card card = cardMapper.mapToCard(cardVM, column);
        Card savedCard = cardRepository.save(card);

        return cardMapper.mapToCardVM(savedCard);
    }
}

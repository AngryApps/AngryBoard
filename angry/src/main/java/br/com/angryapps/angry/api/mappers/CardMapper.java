package br.com.angryapps.angry.api.mappers;

import br.com.angryapps.angry.api.vm.CardVM;
import br.com.angryapps.angry.models.Card;
import br.com.angryapps.angry.models.Column;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public CardVM mapToCardVM(Card card) {
        CardVM cardVM = new CardVM();

        cardVM.setId(card.getId());
        cardVM.setTitle(card.getTitle());
        cardVM.setDescription(card.getDescription());
        cardVM.setCreatedAt(card.getCreatedAt());
        cardVM.setUpdatedAt(card.getUpdatedAt());
        cardVM.setColumnId(card.getColumnId().getId());

        return cardVM;
    }

    public Card mapToCard(CardVM cardVM, Column column) {
        Card card = new Card();

        card.setId(cardVM.getId());
        card.setTitle(cardVM.getTitle());
        card.setDescription(cardVM.getDescription());
        card.setCreatedAt(cardVM.getCreatedAt());
        card.setUpdatedAt(cardVM.getUpdatedAt());
        card.setColumn(column);

        return card;
    }
}

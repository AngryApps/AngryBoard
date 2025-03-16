package br.com.angryapps.api.mappers;

import br.com.angryapps.api.vm.CardVM;
import br.com.angryapps.api.vm.requests.PatchCard;
import br.com.angryapps.models.Card;
import br.com.angryapps.models.Column;
import jakarta.inject.Singleton;
import org.jvnet.hk2.annotations.Service;

import java.time.LocalDateTime;

@Service
@Singleton
public class CardMapper {

    public CardVM mapToCardVM(Card card) {
        CardVM cardVM = new CardVM();

        cardVM.setId(card.getId());
        cardVM.setTitle(card.getTitle());
        cardVM.setDescription(card.getDescription());
        cardVM.setPosition(card.getPosition());
        cardVM.setCreatedAt(card.getCreatedAt());
        cardVM.setUpdatedAt(card.getUpdatedAt());
        cardVM.setColumnId(card.getColumn().getId());

        return cardVM;
    }

    public Card mapToCard(CardVM cardVM, Column column) {
        Card card = new Card();

        card.setId(cardVM.getId());
        card.setTitle(cardVM.getTitle());
        card.setDescription(cardVM.getDescription());
        card.setPosition(cardVM.getPosition());
        card.setCreatedAt(cardVM.getCreatedAt());
        card.setUpdatedAt(cardVM.getUpdatedAt());
        card.setColumn(column);

        return card;
    }

    public void patchCard(PatchCard patchCard, Card card) {
        if (patchCard.getTitle() != null) {
            card.setTitle(patchCard.getTitle());
        }

        if (patchCard.getDescription() != null) {
            card.setDescription(patchCard.getDescription());
        }

        card.setUpdatedAt(LocalDateTime.now());
    }
}

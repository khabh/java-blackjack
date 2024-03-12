package blackjack.domain.card;

import blackjack.testutil.CustomDeck;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HandGeneratorTest {
    @DisplayName("카드를 2장 가진 Hand를 생성한다.")
    @Test
    void generateHandTest() {
        List<Number> numbers = List.of(Number.ACE, Number.EIGHT);
        List<Shape> shapes = List.of(Shape.SPADE, Shape.CLOVER);
        Deck deck = new CustomDeck(numbers, shapes);
        HandGenerator handGenerator = new HandGenerator(deck);

        Hand hand = handGenerator.generate();

        assertThat(hand.getCards().size()).isEqualTo(2);
    }
}

package blackjack.domain.card;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HandTest {
    @DisplayName("NaturalBlackjack 여부를 판단한다.")
    @ParameterizedTest
    @MethodSource("provideCardsWithNaturalBlackjack")
    void isNaturalBlackjackTest(List<Card> cards, boolean isNaturalBlackjack) {
        Hand hand = new Hand(cards);
        assertThat(hand.isNaturalBlackjack()).isEqualTo(isNaturalBlackjack);
    }

    private static Stream<Arguments> provideCardsWithNaturalBlackjack() {
        return Stream.of(
                Arguments.of(createCards(List.of(Number.ACE, Number.JACK)), true),
                Arguments.of(createCards(List.of(Number.FIVE)), false),
                Arguments.of(createCards(List.of(Number.FIVE, Number.SIX, Number.KING)), false),
                Arguments.of(createCards(List.of(Number.ACE, Number.ACE, Number.ACE, Number.EIGHT)), false),
                Arguments.of(createCards(List.of(Number.ACE, Number.QUEEN)), true)
        );
    }

    @DisplayName("scoreBlackjack 여부를 판단한다.")
    @ParameterizedTest
    @MethodSource("provideCardsWithScoreBlackjack")
    void isScoreBlackjackTest(List<Card> cards, boolean isScoreBlackjack) {
        Hand hand = new Hand(cards);
        assertThat(hand.isScoreBlackjack()).isEqualTo(isScoreBlackjack);
    }

    private static Stream<Arguments> provideCardsWithScoreBlackjack() {
        return Stream.of(
                Arguments.of(createCards(List.of(Number.ACE, Number.JACK)), true),
                Arguments.of(createCards(List.of(Number.FIVE)), false),
                Arguments.of(createCards(List.of(Number.FIVE, Number.SIX, Number.KING)), true),
                Arguments.of(createCards(List.of(Number.ACE, Number.ACE, Number.ACE, Number.EIGHT)), true),
                Arguments.of(createCards(List.of(Number.ACE, Number.QUEEN)), true)
        );
    }

    @DisplayName("최적의 점수를 계산한다.")
    @ParameterizedTest
    @MethodSource("provideCardsWithOptimizedScore")
    void getOptimizedScoreTest(List<Card> cards, int score) {
        Hand hand = new Hand(cards);
        assertThat(hand.getOptimizedScore()).isEqualTo(score);
    }

    private static Stream<Arguments> provideCardsWithOptimizedScore() {
        return Stream.of(
                Arguments.of(createCards(List.of(Number.ACE, Number.JACK)), 21),
                Arguments.of(createCards(List.of(Number.FIVE)), 5),
                Arguments.of(createCards(List.of(Number.FIVE, Number.SIX, Number.KING)), 21),
                Arguments.of(createCards(List.of(Number.ACE, Number.ACE, Number.ACE, Number.EIGHT)), 21),
                Arguments.of(createCards(List.of(Number.ACE, Number.FOUR, Number.EIGHT)), 13)
        );
    }

    @DisplayName("카드의 합이 입력된 값을 넘는 지 판단한다.")
    @ParameterizedTest
    @MethodSource("provideCardsWithScoreGreaterThan")
    void isTotalScoreGreaterThanTest(List<Card> cards, int score, boolean isGreaterThan) {
        Hand hand = new Hand(cards);
        assertThat(hand.isTotalScoreGreaterThan(score)).isEqualTo(isGreaterThan);
    }

    private static Stream<Arguments> provideCardsWithScoreGreaterThan() {
        return Stream.of(Arguments.of(createCards(List.of(Number.TWO, Number.THREE)), 5, false),
                Arguments.of(createCards(List.of(Number.QUEEN, Number.JACK)), 20, false),
                Arguments.of(createCards(List.of(Number.FIVE, Number.SIX, Number.ACE)), 11, true)
        );
    }

    @DisplayName("Bust 여부를 판단한다.")
    @ParameterizedTest
    @MethodSource("provideCardsWithBust")
    void isBustTest(List<Card> cards, boolean isBust) {
        Hand hand = new Hand(cards);
        assertThat(hand.isBust()).isEqualTo(isBust);
    }

    private static Stream<Arguments> provideCardsWithBust() {
        return Stream.of(
                Arguments.of(createCards(List.of(Number.ACE, Number.JACK)), false),
                Arguments.of(createCards(List.of(Number.ACE, Number.FIVE, Number.NINE, Number.EIGHT)), true)
        );
    }

    private static List<Card> createCards(List<Number> numbers) {
        return numbers.stream()
                .map(number -> new Card(number, Shape.CLOVER))
                .toList();
    }
}

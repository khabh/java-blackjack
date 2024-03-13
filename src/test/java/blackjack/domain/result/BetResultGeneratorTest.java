package blackjack.domain.result;

import blackjack.domain.card.HandGenerator;
import blackjack.domain.card.Number;
import blackjack.domain.participant.Name;
import blackjack.domain.participant.Participants;
import blackjack.domain.participant.Player;
import blackjack.testutil.CustomDeck;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BetResultGeneratorTest {
    @DisplayName("올바르게 BlackjackGame을 생성한다.")
    @Test
    void generateResultTest() {
        Participants participants = createParticipants();
        PlayerBets playerBets = createPlayerBet(participants, List.of(10_000, 5_000));
        BetResultGenerator betResultGenerator = participants.createBetResultGenerator();
        BlackjackResult blackjackResult = betResultGenerator.generateBetResultOf(playerBets);
        Iterator<ParticipantProfit> expectedProfits = List.of(
                new ParticipantProfit(new Name("딜러"), -10_000),
                new ParticipantProfit(new Name("감자"), 15_000),
                new ParticipantProfit(new Name("고구마"), -5_000)
        ).iterator();

        for (ParticipantProfit participantProfit : blackjackResult.getParticipantProfits()) {
            ParticipantProfit expectedProfit = expectedProfits.next();
            assertThat(participantProfit.getParticipantName()).isEqualTo(expectedProfit.getParticipantName());
            assertThat(participantProfit.getProfit()).isEqualTo(expectedProfit.getProfit());
        }
    }

    private static Participants createParticipants() {
        List<Name> playerNames = List.of(new Name("감자"), new Name("고구마"));
        List<Number> cardNumbers = List.of(Number.ACE, Number.JACK, Number.TWO, Number.TWO, Number.FIVE, Number.SIX);
        HandGenerator handGenerator = new HandGenerator(new CustomDeck(cardNumbers));
        return new Participants(playerNames, handGenerator);
    }

    private static PlayerBets createPlayerBet(Participants participants, List<Integer> bettingPrices) {
        List<Player> players = participants.getPlayers().getValues();
        List<PlayerBet> playerBets = new ArrayList<>();
        while (playerBets.size() < players.size()) {
            int index = playerBets.size();
            Player player = players.get(index);
            int bettingPrice = bettingPrices.get(index);
            playerBets.add(new PlayerBet(player, bettingPrice));
        }
        return new PlayerBets(playerBets);
    }
}
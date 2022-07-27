import { CardStatus } from '../../cards/cardstatus'
import { CardOrganizer } from '../cardorganizer'

function newRecentMistakesFirstSorter (): CardOrganizer {
  function OrderOfRecentFailures (cardStatus: CardStatus): number {
      for (var i : number = cardStatus.getResults().length; i > 0; i--){
          if (cardStatus.getResults()[i-1] == false){
            return i-1
          }
      }
    return -1
  }

  return {
    /**
         * Orders the cards by the number of incorrect answers provided for them.
         *
         * @param cards The {@link CardStatus} objects to order.
         * @return The ordered cards.
         */
    reorganize: function (cards: CardStatus[]): CardStatus[] {
      const c = cards.slice()
      c.sort((a, b) => OrderOfRecentFailures(a) > OrderOfRecentFailures(b) ? -1 : (OrderOfRecentFailures(a) < OrderOfRecentFailures(b) ? 1 : 0))
      return c
    }

  }
}

export { newRecentMistakesFirstSorter }

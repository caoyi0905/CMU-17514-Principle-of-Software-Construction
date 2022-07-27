"use strict";
exports.__esModule = true;
exports.newRecentMistakesFirstSorter = void 0;
function newRecentMistakesFirstSorter() {
    function OrderOfRecentFailures(cardStatus) {
        for (var i = cardStatus.getResults().length; i > 0; i--) {
            if (cardStatus.getResults()[i - 1] == false) {
                return i - 1;
            }
        }
        return -1;
    }
    return {
        /**
             * Orders the cards by the number of incorrect answers provided for them.
             *
             * @param cards The {@link CardStatus} objects to order.
             * @return The ordered cards.
             */
        reorganize: function (cards) {
            var c = cards.slice();
            c.sort(function (a, b) { return OrderOfRecentFailures(a) > OrderOfRecentFailures(b) ? -1 : (OrderOfRecentFailures(a) < OrderOfRecentFailures(b) ? 1 : 0); });
            return c;
        }
    };
}
exports.newRecentMistakesFirstSorter = newRecentMistakesFirstSorter;

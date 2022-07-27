"use strict";
exports.__esModule = true;
exports.newCardStatus = void 0;
/**
 * Creates a new {@link CardStatus} instance.
 *
 * @param card The {@link FlashCard} card to track answer correctness for.
 */
function newCardStatus(card) {
    var successes = [];
    return {
        getCard: function () { return card; },
        getResults: function () { return successes.slice(); },
        recordResult: function (success) { successes.push(success); },
        clearResults: function () { successes = []; }
    };
}
exports.newCardStatus = newCardStatus;

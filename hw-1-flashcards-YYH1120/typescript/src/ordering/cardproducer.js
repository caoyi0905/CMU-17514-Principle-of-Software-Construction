"use strict";
exports.__esModule = true;
exports.newCardDeck = void 0;
var cardstatus_1 = require("../cards/cardstatus");
function newCardDeck(cards, cardOrganizer) {
    var status = cards.map(cardstatus_1.newCardStatus);
    return {
        getCards: function () {
            return status.slice();
        },
        getOrganizer: function () {
            return cardOrganizer;
        },
        reorganize: function () {
            status = cardOrganizer.reorganize(status);
        },
        isComplete: function () {
            return status.length === 0;
        },
        countCards: function () {
            return status.length;
        }
    };
}
exports.newCardDeck = newCardDeck;

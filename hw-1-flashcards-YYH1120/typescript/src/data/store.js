"use strict";
exports.__esModule = true;
exports.loadCards = exports.newInMemoryCardStore = void 0;
var flashcard_1 = require("../cards/flashcard");
var fs = require("fs");
function newInMemoryCardStore(initialCards) {
    var cards = initialCards.slice();
    return {
        getAllCards: function () { return cards.slice(); },
        addCard: function (card) {
            if (cards.includes(card))
                return false;
            cards.push(card);
            return true;
        },
        removeCard: function (card) {
            var idx = cards.indexOf(card);
            if (idx === -1)
                return false;
            cards.splice(idx, 1);
            return true;
        },
        invertCards: function () {
            var newCards = [];
            for (var _i = 0, cards_1 = cards; _i < cards_1.length; _i++) {
                var card = cards_1[_i];
                newCards.push((0, flashcard_1.newFlashCard)(card.getAnswer(), card.getQuestion()));
            }
            return newInMemoryCardStore(newCards);
        }
    };
}
exports.newInMemoryCardStore = newInMemoryCardStore;
function loadCards(file) {
    var lines = fs.readFileSync(file).toString().split(/\r?\n/);
    var result = [];
    for (var _i = 0, lines_1 = lines; _i < lines_1.length; _i++) {
        var line = lines_1[_i];
        var idx = line.indexOf('--');
        if (idx > 0) {
            result.push((0, flashcard_1.newFlashCard)(line.substring(idx + 2), line.substring(0, idx)));
        }
    }
    return newInMemoryCardStore(result);
}
exports.loadCards = loadCards;

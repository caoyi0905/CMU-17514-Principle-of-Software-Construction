"use strict";
exports.__esModule = true;
exports.newUI = void 0;
var readline = require("readline-sync");
function newUI() {
    function cueAllCards(producer) {
        for (var _i = 0, _a = producer.getCards(); _i < _a.length; _i++) {
            var cardStatus = _a[_i];
            var card = cardStatus.getCard();
            var correctAnswer = cueCard(card);
            cardStatus.recordResult(correctAnswer);
        }
    }
    function cueCard(card) {
        console.log('\nNext cue: ' + card.getQuestion());
        var line = readline.question('answer> ');
        var success = card.checkSuccess(line);
        if (success) {
            console.log("That's correct!");
        }
        else {
            console.log('That is incorrect; the correct response was: ' +
                card.getAnswer());
        }
        return success;
    }
    return {
        /**
             * Prompts the user with {@link FlashCard} cards until the {@link CardProducer} is exhausted.
             * @param cardProducer The {@link CardProducer} to use for organizing cards.
             * @param learnTitles Whether to prompt with definitions and require the user to provide titles.
             */
        studyCards: function (producer) {
            while (!producer.isComplete()) {
                console.log("".concat(producer.countCards(), " cards to go..."));
                cueAllCards(producer);
                console.log('Reached the end of the card deck, reorganizing...');
                producer.reorganize();
            }
            console.log('Finished all cards. Yay.');
        }
    };
}
exports.newUI = newUI;

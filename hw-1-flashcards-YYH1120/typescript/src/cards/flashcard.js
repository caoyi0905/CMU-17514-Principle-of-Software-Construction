"use strict";
exports.__esModule = true;
exports.newFlashCard = void 0;
/**
 * create a new flashcard with a title and a definition
 * @param title
 * @param definition
 * @returns new flash card
 */
function newFlashCard(question, answer) {
    return {
        getQuestion: function () { return question; },
        getAnswer: function () { return answer; },
        checkSuccess: function (response) {
            return answer.trim().toLowerCase() === response.trim().toLowerCase();
        },
        toString: function () {
            return 'FlashCard[' + question + ', ' + answer + ']';
        }
    };
}
exports.newFlashCard = newFlashCard;

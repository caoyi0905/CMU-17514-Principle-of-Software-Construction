"use strict";
exports.__esModule = true;
var store_1 = require("./data/store");
var cardproducer_1 = require("./ordering/cardproducer");
var mostmistakes_1 = require("./ordering/prioritization/mostmistakes");
var ui_1 = require("./ui");
var recentmistakes_1 = require("./ordering/prioritization/recentmistakes");
var cardorganizer_1 = require("./ordering/cardorganizer");
var cardshuffler_1 = require("./ordering/prioritization/cardshuffler");
var cardrepeater_1 = require("./ordering/repetition/cardrepeater");
function printHelpMessage() {
    console.log("Options:");
    console.log('-h,  --help', '                Show this help');
    console.log('-o,  --order <order>', '       The type of ordering to use, default "random" [choices: "random", "worst-first", "recent-mistakes-first"]');
    console.log('-r,  --repetitions <num>', '   The number of times to each card should be answered successfully. If not provided, every card is presented once, regardless of the correctness of the answer.');
    console.log('-i,  --invertCards', '         If set, it flips answer and question for each card. That is, it prompts with the cards answer and asks the user to provide the corresponding question. Default: false\n');
}
// TODOs
// 1. load command line options
// 2. set up cards and card organizers depending on command line options
// 3. create a card deck and the UI and start the UI with the card deck
var Invert = false;
var Repenum = 0;
var cardorganizerlist = [];
var Order = '';
var repenum = '';
var filepath = 'D:/CMU/17514 CMU/hw-1-flashcards-YYH1120/typescript/cards/designpatterns.csv';
var program = require('commander');
/*Set the options of help, order, repetitions and invertCards*/
program.option('-h,  --help', 'Show this help')
    .option('-o, --order <order>', 'The type of ordering to use, default "random"[choices: "random", "worst-first", "recent-mistakes-first"]')
    .option('-r, --repetitions <type>', 'The number of times to each card should be answered successfully. If not provided, every card is presented once, regardless of the correctness of the answer.')
    .option('-i,--invertCards', 'If set, it flips answer and question for each card. That is, it prompts with the cards answer and asks the user to provide the corresponding question. Default: false');
program.parse(process.argv);
var options = program.opts();
if (options.help)
    printHelpMessage();
if (options.order)
    Order = "".concat(program.opts().order);
if (options.repetitions) {
    repenum = "".concat(program.opts().repetitions);
    Repenum = Number(repenum.trim());
}
if (options.invertCards)
    Invert = true;
/*Set the mode of card organization*/
if (Order == 'worst-first')
    cardorganizerlist.push((0, mostmistakes_1.newMostMistakesFirstSorter)());
else if (Order == 'recent-mistakes-first')
    cardorganizerlist.push((0, recentmistakes_1.newRecentMistakesFirstSorter)());
else
    cardorganizerlist.push((0, cardshuffler_1.newCardShuffler)());
/*Set the times of repetitions*/
if (Repenum > 0)
    cardorganizerlist.push((0, cardrepeater_1.newRepeatingCardOrganizer)(Repenum));
else
    cardorganizerlist.push((0, cardrepeater_1.newNonRepeatingCardOrganizer)());
var combinedorganizer = (0, cardorganizer_1.newCombinedCardOrganizer)(cardorganizerlist);
if (Invert) {
    var cardDeck = (0, cardproducer_1.newCardDeck)((0, store_1.loadCards)(filepath).invertCards().getAllCards(), combinedorganizer);
    (0, ui_1.newUI)().studyCards(cardDeck);
}
else {
    var cardDeck = (0, cardproducer_1.newCardDeck)((0, store_1.loadCards)(filepath).getAllCards(), combinedorganizer);
    (0, ui_1.newUI)().studyCards(cardDeck);
}

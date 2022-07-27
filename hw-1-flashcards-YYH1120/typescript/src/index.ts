import { loadCards } from './data/store'
import { newCardDeck } from './ordering/cardproducer'
import { newMostMistakesFirstSorter } from './ordering/prioritization/mostmistakes'
import { newUI } from './ui'
import { newRecentMistakesFirstSorter } from './ordering/prioritization/recentmistakes'
import {Command} from 'commander'
import { CardOrganizer, newCombinedCardOrganizer } from './ordering/cardorganizer'
import { newCardShuffler } from './ordering/prioritization/cardshuffler'
import { newNonRepeatingCardOrganizer, newRepeatingCardOrganizer } from './ordering/repetition/cardrepeater'

function printHelpMessage(){
  console.log("Options:")
  console.log('-h,  --help','                Show this help')
  console.log('-o,  --order <order>', '       The type of ordering to use, default "random" [choices: "random", "worst-first", "recent-mistakes-first"]')
  console.log('-r,  --repetitions <num>', '   The number of times to each card should be answered successfully. If not provided, every card is presented once, regardless of the correctness of the answer.')
  console.log('-i,  --invertCards','         If set, it flips answer and question for each card. That is, it prompts with the cards answer and asks the user to provide the corresponding question. Default: false\n')
}
// TODOs
// 1. load command line options
// 2. set up cards and card organizers depending on command line options
// 3. create a card deck and the UI and start the UI with the card deck
var Invert : boolean = false
var Repenum : number = 0
let cardorganizerlist : CardOrganizer[] = []
var Order :string = ''
var repenum :string  = ''
var filepath : string = 'D:/CMU/17514 CMU/hw-1-flashcards-YYH1120/typescript/cards/designpatterns.csv'
const program = require('commander')

/*Set the options of help, order, repetitions and invertCards*/
program.option('-h,  --help','Show this help')
.option('-o, --order <order>', 'The type of ordering to use, default "random"[choices: "random", "worst-first", "recent-mistakes-first"]')
.option('-r, --repetitions <type>', 'The number of times to each card should be answered successfully. If not provided, every card is presented once, regardless of the correctness of the answer.')
.option('-i,--invertCards','If set, it flips answer and question for each card. That is, it prompts with the cards answer and asks the user to provide the corresponding question. Default: false')

program.parse(process.argv)

const options = program.opts()
if(options.help) printHelpMessage()
if(options.order) Order  = `${program.opts().order}`
if(options.repetitions) {
  repenum = `${program.opts().repetitions}`
  Repenum = Number(repenum.trim())
}
if(options.invertCards) Invert = true

/*Set the mode of card organization*/
if (Order == 'worst-first') cardorganizerlist.push(newMostMistakesFirstSorter())
else if (Order == 'recent-mistakes-first') cardorganizerlist.push(newRecentMistakesFirstSorter())
else cardorganizerlist.push(newCardShuffler())

/*Set the times of repetitions*/
if (Repenum>0) cardorganizerlist.push(newRepeatingCardOrganizer(Repenum))
else cardorganizerlist.push(newNonRepeatingCardOrganizer())

const combinedorganizer = newCombinedCardOrganizer(cardorganizerlist)

if (Invert){
  const cardDeck = newCardDeck(
    loadCards(filepath).invertCards().getAllCards(),
    combinedorganizer
  )
  newUI().studyCards(cardDeck)
}else{
  const cardDeck = newCardDeck(
    loadCards(filepath).getAllCards(),
    combinedorganizer
  )

  newUI().studyCards(cardDeck)
}



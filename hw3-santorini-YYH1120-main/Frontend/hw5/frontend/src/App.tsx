import Handlebars from "handlebars"
import { Component } from 'react'
import './App.css'

var oldHref = "http://localhost:3000"
var turn = 0
var GodName = new Set();
var god0 = new String;
var god1 = new String;


function godnamepro (god: String): Set<String>{
  var godnamesplit = god.split(" ");
  let godnameset = new Set<String>();
  for (let i of godnamesplit){
    godnameset.add(i);
  }
  return godnameset
}

interface Cell {
  text: String;
  clazz: String;
  link: String;
}



interface Cells {
  cells: Array<Cell>,
  template: HandlebarsTemplateDelegate<any>,
  instructions: String,
  mode: String,
  godtype: String,
  godinfo: String
}

interface Props {
}

class App extends Component<Props, Cells> {
  constructor(props: Props) {
    super(props);
    this.state = {
      cells: [
        { text: "", clazz: "playable", link: "/play?x=0&y=0" },
        { text: "", clazz: "playable", link: "/play?x=1&y=0" },
        { text: "", clazz: "playable", link: "/play?x=2&y=0" },
        { text: "", clazz: "playable", link: "/play?x=3&y=0" },
        { text: "", clazz: "playable", link: "/play?x=4&y=0" },
        { text: "", clazz: "playable", link: "/play?x=0&y=1" },
        { text: "", clazz: "playable", link: "/play?x=1&y=1" },
        { text: "", clazz: "playable", link: "/play?x=2&y=1" },
        { text: "", clazz: "playable", link: "/play?x=3&y=1" },
        { text: "", clazz: "playable", link: "/play?x=4&y=1" },
        { text: "", clazz: "playable", link: "/play?x=0&y=2" },
        { text: "", clazz: "playable", link: "/play?x=1&y=2" },
        { text: "", clazz: "playable", link: "/play?x=2&y=2" },
        { text: "", clazz: "playable", link: "/play?x=3&y=2" },
        { text: "", clazz: "playable", link: "/play?x=4&y=2" },
        { text: "", clazz: "playable", link: "/play?x=0&y=3" },
        { text: "", clazz: "playable", link: "/play?x=1&y=3" },
        { text: "", clazz: "playable", link: "/play?x=2&y=3" },
        { text: "", clazz: "playable", link: "/play?x=3&y=3" },
        { text: "", clazz: "playable", link: "/play?x=4&y=3" },
        { text: "", clazz: "playable", link: "/play?x=0&y=4" },
        { text: "", clazz: "playable", link: "/play?x=1&y=4" },
        { text: "", clazz: "playable", link: "/play?x=2&y=4" },
        { text: "", clazz: "playable", link: "/play?x=3&y=4" },
        { text: "", clazz: "playable", link: "/play?x=4&y=4" },
      ],
      template: this.loadTemplate(),
      instructions: "It is Player 0's turn",
      mode: "",
      godtype: "",
      godinfo: ""
    };
  }

  loadTemplate (): HandlebarsTemplateDelegate<any> {
    const src = document.getElementById("handlebars");
    return Handlebars.compile(src?.innerHTML, {});
  }

  convertToCell(p: any): Array<Cell> {
    const newCells: Array<Cell> = [];
    for (var i = 0; i < p["cells"].length; i++) {

        var c: Cell = {
          text: p["cells"][i]["text"],
          clazz: p["cells"][i]["clazz"],
          link: p["cells"][i]["link"],
        };

      newCells.push(c);
    }
    return newCells;
  }

  getTurn(p: any): String {
    return p["turn"]
  }

  getWinner(p: any): String | undefined {
    return p["winner"]
  }

  getInstr(turn: String, winner: String | undefined) {
    if (winner === undefined) return "It is Player " + turn + "'s turn"
    else return "Player " + winner + " wins!"
  }

  getGod(p:any): String {
    return p["god"]
  }

  async newGame() {
    console.log("2222")
    const response = await fetch("newgame");
    console.log(response)
    const json = await response.json();
    console.log(json)

    const newCells: Array<Cell> = this.convertToCell(json);
    this.setState({ cells: newCells, instructions: "It is Player 0's turn", mode: "show",godtype:"", godinfo: ""})
  }

  async general(){
    const response = await fetch("general");
    this.setState({  mode: "", godtype:"",godinfo: "general mode"})
  }

  async god(){
    const response = await fetch("general");
    this.setState({ mode: "", godtype:"show", godinfo: "god mode"})
  }



  async pan(){
    const response = await fetch("Pan");
    const json = await response.json();
    let godinfo = "god mode";
    

    const godname = this.getGod(json);
    let godnameset = godnamepro(godname);

    var godtypestate = "show"
    if (godnameset.size >=2){
      godinfo =  "Player 0 (X) : " + Array.from(godnameset)[0] + " ---------"+"    Player 1 (O) : " + Array.from(godnameset)[1];
      godtypestate = ""
    }

    console.log(godnameset)
    this.setState({ mode: "", godtype: godtypestate, godinfo: godinfo})
  }

  async demeter(){
    const response = await fetch("Demeter");
    const json = await response.json();
    let godinfo = "god mode";

    const godname = this.getGod(json);
    let godnameset = godnamepro(godname);

    var godtypestate = "show"
    if (godnameset.size >=2){
      godinfo =  "Player 0 (X) : " + Array.from(godnameset)[0] + " ---------"+"    Player 1 (O) : " + Array.from(godnameset)[1];
      godtypestate = ""
    }

    console.log(godnameset)
    this.setState({ mode: "", godtype: godtypestate, godinfo: godinfo})
  }

  async minotaur(){
    const response = await fetch("Minotaur");
    const json = await response.json();
    let godinfo = "god mode"

    const godname = this.getGod(json);
    let godnameset = godnamepro(godname);

    var godtypestate = "show"
    if (godnameset.size >=2){
      godinfo =  "Player 0 (X) : " + Array.from(godnameset)[0] + " ---------"+" Player 1 (O) : " + Array.from(godnameset)[1];
      godtypestate = ""
    }
    console.log(godnameset)
    this.setState({ mode: "", godtype: godtypestate, godinfo: godinfo})
  }

  async play(url: String) {


    
    const href = "play?" + url.split("?")[1];
    const response = await fetch(href);
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const instr = this.getInstr(turn, winner)

    const godname = this.getGod(json);
    let godnameset = godnamepro(godname);
    god0 = Array.from(godnameset)[0] ;
    god1 = Array.from(godnameset)[1] ;

    var godinfo = "Player 0 (X) : " + god0  + " ---------"+" Player 1 (O) : " + god1;
    if (!god0){
      godinfo = "General Mode: Player 0 : X ------ Player 1: O"
    }


    this.setState({ cells: newCells, instructions: instr, mode: "", godtype:"",godinfo: godinfo})
  }

  async undo() {
    

    const response = await fetch("undo");
    const json = await response.json();

    const newCells: Array<Cell> = this.convertToCell(json);
    const turn = this.getTurn(json)
    const winner = this.getWinner(json)
    const instr = this.getInstr(turn, winner)
    const godname = this.getGod(json);

    let godnameset = godnamepro(godname);
    god0 = Array.from(godnameset)[0];
    god1 = Array.from(godnameset)[1];

    var godinfo = "Player 0 (X) : " + god0  + " ---------"+" Player 1 (O) : " + god1;
    if (!god0){
      godinfo = "General Mode: Player 0 : X ------ Player 1: O"
    }

    this.setState({ cells: newCells, instructions: instr , mode:"", godinfo: godinfo })
  }

  async switch() {
    if (
      window.location.href === "http://localhost:3000/newgame" &&
      oldHref !== window.location.href
    ) {
      this.newGame();
      oldHref = window.location.href;
    }  else if (
      window.location.href === "http://localhost:3000/general" &&
      oldHref !== window.location.href
    ) {
      this.general();
      oldHref = window.location.href;
    } else if (
      window.location.href === "http://localhost:3000/god" &&
      oldHref !== window.location.href
    ) {
      this.god();
      oldHref = window.location.href;
    } 
    else if (
      window.location.href === "http://localhost:3000/Pan" &&
      oldHref !== window.location.href
    ) {
      this.pan();
      oldHref = window.location.href;
    } 
    else if (
      window.location.href === "http://localhost:3000/Demeter" &&
      oldHref !== window.location.href
    ) {
      this.demeter();
      oldHref = window.location.href;
    } 
    else if (
      window.location.href === "http://localhost:3000/Minotaur" &&
      oldHref !== window.location.href
    ) {
      this.minotaur();
      oldHref = window.location.href;
    } 
    else if (
      window.location.href.split("?")[0] === "http://localhost:3000/play" &&
      oldHref !== window.location.href
    ) {
      this.play(window.location.href);
      oldHref = window.location.href;
    } else if (
      window.location.href === "http://localhost:3000/undo" &&
      oldHref !== window.location.href
    ) {
      this.undo();
      oldHref = window.location.href;
    }
  };

  render() {
    this.switch()
    console.log("a")
    return (
      <div className="App">
        <div
          dangerouslySetInnerHTML={{
            __html: this.state.template({ cells: this.state.cells, instructions: this.state.instructions, 
               mode: this.state.mode, godtype:this.state.godtype, godinfo: this.state.godinfo }),
          }}
        />
      </div>
    )
  };
};

export default App;

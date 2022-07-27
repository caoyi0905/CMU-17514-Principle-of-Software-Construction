package game;

import token.Worker;

import java.util.List;


public enum Player{
    PLAYER0(0), PLAYER1(1);

    final int value;
    final List<Worker> workers;

    Player(int value, List<Worker> workers){
        this.value = value;
        this.workers = workers;
    }
    Player(int value){
        this(value,List.of());
    }


    public List<Worker> getWorkers() {
        return workers;
    }

    public int getValue() {
        return value;
    }

    public void setWorkers(Worker worker){
        this.workers.add(worker);
    }
}


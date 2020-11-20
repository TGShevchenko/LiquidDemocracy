package com.testchallenge;

public class Main {
  public static void main(String[] args) {
    UserInputController userInputController = new UserInputController(new VoterController(), new Printer());
    userInputController.run();
  }
}

package com.testchallenge;

import java.io.*;
import java.util.*;

/** Decodes the use input and extracts the commands for voters */
class UserInputController {
  private final IVoterController voterController;
  private final IPrinter printer;

  /**
   * UserInputController constructor with injected voterController and printer with the interfaces
   *
   * @param voterController
   * @param printer
   */
  public UserInputController(IVoterController voterController, IPrinter printer) {
    this.voterController = voterController;
    this.printer = printer;
  }

  /** Runs the main loop that reads a user input, and calls parse and decode methods */
  public void run() {
    final String initialMessage =
        "Please, enter the command as follows: VOTER COMMAND [PICK or DELEGATE]. Press q for exit.";
    Console console = System.console();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println(initialMessage);
      String commandLine = scanner.nextLine();
      String[] splittedLine = commandLine.split(" ");
      switch (splittedLine.length) {
        case 1:
          if (splittedLine[0].toUpperCase().equals("Q")) {
            return;
          }
          break;
        case 3:
          if (decodeSplittedLine(splittedLine)) {
            printer.printItems(voterController.getAggregatedVoterChoicesForValidVotes());
            printer.printLine(voterController.getInvalidVotesNumber() + " invalid");
          }
          break;
        default:
          break;
      }
    }
  }

  /**
   * Decodes the splitted input line with calling corresponding voterController methods.
   *
   * @param splittedLine
   * @return A boolean value that indicates if the decoding process was successful.
   */
  private boolean decodeSplittedLine(String[] splittedLine) {
    String voterName = splittedLine[0];
    String commandString = splittedLine[1];
    String choiceOrDelegate = splittedLine[2];
    if (commandString.toUpperCase().equals("PICK")) {
      voterController.addVoterWithChoice(voterName, choiceOrDelegate);
      return true;
    } else if (commandString.toUpperCase().equals("DELEGATE")) {
      voterController.addVoterWithDelegate(voterName, choiceOrDelegate);
      return true;
    }
    return false;
  }
}

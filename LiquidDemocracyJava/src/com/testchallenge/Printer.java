package com.testchallenge;

import java.util.*;

/** Printer class that prints names and choices */
class Printer implements IPrinter {
  public Printer() {}

  /**
   * Print items from a map
   *
   * @param items
   */
  public void printItems(Map<String, Integer> items) {
    for (Iterator<String> it = items.keySet().iterator(); it.hasNext(); ) {
      String choiceKey = it.next();
      System.out.println(items.get(choiceKey) + " " + choiceKey);
    }
  }

  /**
   * Prints a single line
   * @param line
   */
  public void printLine(String line)
  {
    System.out.println(line);
  }
}

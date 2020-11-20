package com.testchallenge;

import java.util.*;

/** Interface for a Printer class */
interface IPrinter {
  /**
   * Print items from a map
   *
   * @param items
   */
  void printItems(Map<String, Integer> items);
  /**
   * Prints a single line
   * @param line
   */
  void printLine(String line);
}

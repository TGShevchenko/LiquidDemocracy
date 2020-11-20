package com.testchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class VoterControllerTest {
  private final IVoterController voterController = new VoterController();

  @org.junit.jupiter.api.Test
  void addVoterWithChoiceTest() {
    IVoterController voterController = new VoterController();
    voterController.addVoterWithChoice("Bob", "Pizza");
    Map<String, Integer> choices = voterController.getAggregatedVoterChoicesForValidVotes();

    assertTrue(choices.containsKey("Pizza"));
    assertEquals(1, choices.get("Pizza"));
  }

  @org.junit.jupiter.api.Test
  void addVoterWithDelegateTest() {
    IVoterController voterController = new VoterController();
    voterController.addVoterWithDelegate("Alice", "Bob");
    Map<String, Integer> choices = voterController.getAggregatedVoterChoicesForValidVotes();

    assertEquals(0, choices.size());
  }

  @org.junit.jupiter.api.Test
  void addVotersWithChoicesAndDelegatesTest() {
    voterController.addVoterWithChoice("Alice", "Pizza");
    voterController.addVoterWithDelegate("Bob", "Carol");
    voterController.addVoterWithChoice("Carol", "Salad");
    voterController.addVoterWithDelegate("Dave", "Eve");
    voterController.addVoterWithDelegate("Eve", "Mallory");
    voterController.addVoterWithChoice("Eve", "Pizza");
    voterController.addVoterWithDelegate("Mallory", "Eve");

    Map<String, Integer> choices = voterController.getAggregatedVoterChoicesForValidVotes();

    assertTrue(choices.containsKey("Pizza"));
    assertEquals(1, choices.get("Pizza"));
    assertTrue(choices.containsKey("Salad"));
    assertEquals(2, choices.get("Salad"));
  }

  @org.junit.jupiter.api.Test
  void checkAddedVoters() {
    voterController.addVoter(new Voter("Dave"));
    voterController.addVoterWithDelegate("Eve", "Mallory");
    voterController.addVoter(new Voter("Alice"));

    assertEquals(4, voterController.getVotersNumber());
  }

  @org.junit.jupiter.api.Test
  void getInvalidVotesNumberTest() {
    voterController.addVoterWithChoice("Alice", "Pizza");
    voterController.addVoterWithDelegate("Bob", "Carol");
    voterController.addVoterWithChoice("Carol", "Salad");
    voterController.addVoterWithDelegate("Dave", "Eve");
    voterController.addVoterWithDelegate("Eve", "Mallory");
    voterController.addVoterWithChoice("Eve", "Pizza");
    voterController.addVoterWithDelegate("Mallory", "Eve");

    assertEquals(3, voterController.getInvalidVotesNumber());
  }
}
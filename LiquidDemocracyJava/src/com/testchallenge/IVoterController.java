package com.testchallenge;

import java.util.*;

/** Interface for a VoterController class */
public interface IVoterController {
  /**
   *
   * @param voter
   */
  void addVoter(IVoter voter);
  /**
   * Add a voter and a corresponding choice to a voter map. If a voter exist, a choice will be added
   * or updated. Otherwise - a choice will be added.
   *
   * @param voterName
   * @param choice
   */
  void addVoterWithChoice(String voterName, String choice);
  /**
   * Add a voter and a delegate to a voter map. If a voter or a delegate exist, they will be updated
   * in a map. Otherwise, they will be added.
   *
   * @param voterName
   * @param delegateName
   */
  void addVoterWithDelegate(String voterName, String delegateName);
  /**
   * Delegate one voter to another
   *
   * @param fromWhom
   * @param toWhom
   */
  void delegateVote(IVoter fromWhom, IVoter toWhom);
  /**
   * Obtains an aggregated sorted choices for invalid voted
   *
   * @return A map of aggregated sorted choices for valid votes like: "[{2, Salad}, {1, Pizza}]"
   */
  Map<String, Integer> getAggregatedVoterChoicesForValidVotes();
  /**
   * Gets the voters number
   * @return
   */
  int getVotersNumber();
  /**
   * Gets the invalid votes number
   * @return A number of invalid votes
   */
  int getInvalidVotesNumber();
}

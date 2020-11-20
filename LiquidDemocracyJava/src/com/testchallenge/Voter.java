package com.testchallenge;

import java.util.*;

/**
 * Class that represents a Voter with corresponding operations: Pick - to pick a choice and
 * DelegateTo to delegate to another Voter
 */
public class Voter implements IVoter {
  private final String name;
  private IVoter delegateToVoter;
  private IVoter delegateFromVoter;

  Map<String, Integer> choices = new HashMap<>();

  public Voter(String name) {
    this.name = name;
  }

  public Map<String, Integer> getChoices() {
    return choices;
  }

  public void setChoices(Map<String, Integer> choices) {
    this.choices = choices;
  }

  public String getName() {
    return name;
  }

  public IVoter getDelegateFromVoter() {
    return delegateFromVoter;
  }

  public void setDelegateFromVoter(IVoter delegateFromVoter) {
    this.delegateFromVoter = delegateFromVoter;
  }

  public IVoter getDelegateToVoter() {
    return delegateToVoter;
  }

  public void setDelegateToVoter(IVoter delegateToVoter) {
    this.delegateToVoter = delegateToVoter;
  }

  /**
   * Picks a selected choice and adds it to a choice map
   *
   * @param item
   */
  public void pick(String item) {
    Map<String, Integer> choicesMap = getChoices();
    if (getDelegateFromVoter() != null) {
      choicesMap = getDelegateFromVoter().getChoices();
    }
    updateChoiceDictionary(item, choicesMap);
    if (getDelegateFromVoter() != null) {
      setChoices(choicesMap);
    }
  }

  /**
   * Updates a choice map with a given item
   *
   * @param item
   * @param choicesDict
   */
  private void updateChoiceDictionary(String item, Map<String, Integer> choicesDict) {
    choicesDict.put(item, choicesDict.getOrDefault(item, 0) + 1);
  }
}

package com.testchallenge;

import java.util.*;

/** Interface for a Voter class */
public interface IVoter {
  /**
   * Picks a selected choice and adds it to a choice map
   *
   * @param item
   */
  void pick(String item);

  IVoter getDelegateToVoter();

  void setDelegateToVoter(IVoter voter);

  IVoter getDelegateFromVoter();

  void setDelegateFromVoter(IVoter voter);

  Map<String, Integer> getChoices();

  void setChoices(Map<String, Integer> choices);

  String getName();
}

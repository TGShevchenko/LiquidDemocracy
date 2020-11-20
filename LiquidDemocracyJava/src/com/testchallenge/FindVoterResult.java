package com.testchallenge;

/**
 * A storage class that keeps the returning result for Voter. It provides a convenient way to know
 * when a Voter is found, and it avoids null if a Voter is not found.
 */
public class FindVoterResult {
  private final IVoter voter;
  private final boolean isFound;

  public FindVoterResult(IVoter voter, boolean isFound) {
    this.voter = voter;
    this.isFound = isFound;
  };

  public IVoter getVoterItem() {
    return voter;
  }

  public boolean isFound() {
    return isFound;
  }
}

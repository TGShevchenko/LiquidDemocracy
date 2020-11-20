package com.testchallenge;

import java.util.*;
import java.util.stream.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/** A controller class that manipulates the voters collection */
public class VoterController implements IVoterController {
  private Map<String, IVoter> voterDictionary = new HashMap<>();

  public VoterController() {}

  /**
   * Gets the voters number
   * @return
   */
  public int getVotersNumber(){
    return voterDictionary.size();
  }
  /**
   * Add a voter and a corresponding choice to a voter map. If a voter exist, a choice will be added
   * or updated. Otherwise - a choice will be added.
   *
   * @param voterName
   * @param choice
   */
  public void addVoterWithChoice(String voterName, String choice) {
    IVoter voter = findVoter(voterName).getVoterItem();
    voter.pick(choice);
    addVoter(voter);
  }

  /**
   * Add a voter and a delegate to a voter map. If a voter or a delegate exist, they will be updated
   * in a map. Otherwise, they will be added.
   *
   * @param voterName
   * @param delegateName
   */
  public void addVoterWithDelegate(String voterName, String delegateName) {
    delegateVote(findVoter(voterName).getVoterItem(), findVoter(delegateName).getVoterItem());
  }

  /**
   * Delegate one voter to another
   *
   * @param fromWhom
   * @param toWhom
   */
  public void delegateVote(IVoter fromWhom, IVoter toWhom) {
    fromWhom.setDelegateToVoter(toWhom);
    toWhom.setDelegateFromVoter(fromWhom);
    addVoter(fromWhom);
    addVoter(toWhom);
  }

  /**
   * Add a voter to a voter map
   *
   * @param voter
   */
  public void addVoter(IVoter voter) {
    voterDictionary.put(voter.getName(), voter);
  }

  /**
   * Finds a voter in a voter map
   *
   * @param voterName
   * @return
   */
  FindVoterResult findVoter(String voterName) {
    return findVoter(new Voter(voterName));
  }

  /**
   * Finds a voter in a voter map
   *
   * @param voter
   * @return
   */
  FindVoterResult findVoter(IVoter voter) {
    String voterName = (voter != null) ? voter.getName() : "";
    if (voterDictionary.containsKey(voterName)) {
      return new FindVoterResult(voterDictionary.get(voterName), true);
    }
    return new FindVoterResult(new Voter(voterName), false);
  }

  /**
   * Gets the voters list, which contains valid or invalid votes depending on the boolean parameter.
   * If the parameter is true, the method returns a voters list with invalid votes. If false - valid
   * votes
   *
   * @param usePredicateForInvalidVotes
   * @return
   */
  private List<IVoter> getVotersFilteredByValidityOfVotes(boolean usePredicateForInvalidVotes) {
    List voterValuesList = new ArrayList(voterDictionary.values());
    return (List<IVoter>)
        voterValuesList.stream()
            .filter(
                voter ->
                    voterValuesList.stream()
                        .anyMatch(
                            voterItem ->
                                usePredicateForInvalidVotes
                                    ? areVotesInvalidForVoter((IVoter) voter)
                                    : !areVotesInvalidForVoter((IVoter) voter)))
            .collect(Collectors.toList());
  }

  /**
   * Checks if a given voter has in its delegates invalid votes,
   * i.e. those votes, which refer to themselves.
   *
   * @param voter
   * @return
   */
  private boolean areVotesInvalidForVoter(IVoter voter) {
    HashSet<IVoter> uniqueVoters = new HashSet<IVoter>();
    FindVoterResult voterResult = findVoter(voter);
    while (voterResult.isFound()) {
      if (uniqueVoters.contains(voterResult.getVoterItem())) return true;
      uniqueVoters.add(voterResult.getVoterItem());
      voterResult = findVoter(voterResult.getVoterItem().getDelegateToVoter());
    }
    return false;
  }

  /**
   * Obtains an aggregated sorted choices for invalid voted
   *
   * @return A map of aggregated sorted choices for valid votes like: "[{2, Salad}, {1, Pizza}]"
   */
  public Map<String, Integer> getAggregatedVoterChoicesForValidVotes() {
    Map<String, Integer> aggregatedChoicesMap = new HashMap<>();
    for (IVoter voter : getVotersFilteredByValidityOfVotes(false)) {
      fillVoterDictionaryWithChoices(aggregatedChoicesMap, voter.getChoices());
    }
    return getVoterChoicesSorted(
        aggregatedChoicesMap, Map.Entry.comparingByValue(Comparator.reverseOrder()));
  }

  /**
   * Fills up a given voter map with choices.
   *
   * @param voterDictionary
   * @param choices
   */
  private void fillVoterDictionaryWithChoices(
      Map<String, Integer> voterDictionary, Map<String, Integer> choices) {
    for (Iterator<String> it = choices.keySet().iterator(); it.hasNext(); ) {
      String choiceKey = it.next();
      voterDictionary.put(
          choiceKey, voterDictionary.getOrDefault(choiceKey, 0) + choices.get(choiceKey));
    }
  }

  /**
   * Gets the invalid votes number
   * @return A number of invalid votes
   */
  public int getInvalidVotesNumber() {
    return getVotersFilteredByValidityOfVotes(true).size();
  }

  private Map<String, Integer> getVoterChoicesSorted(
      Map<String, Integer> aggregatedChoicesMap, Comparator<?> sortDirectionComparator) {
    return aggregatedChoicesMap.entrySet().stream()
        .sorted((Comparator<? super Map.Entry<String, Integer>>) sortDirectionComparator)
        .collect(
            Collectors.toMap(
                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }
}

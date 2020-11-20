using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace LiquidDemocracy
{

    /// <summary>
    /// A controller class that manipulates the voters collection
    /// </summary>
    public class VoterController : IVoterController
    {
        IDictionary<String, IVoter> voterDictionary = new Dictionary<String, IVoter>();

        public VoterController() { }

        public void AddVoterWithChoice(String voterName, String choice)
        {
            IVoter voter = findVoter(voterName).VoterItem;
            voter.Pick(choice);
            AddVoter(voter);
        }

        public void AddVoterWithDelegate(String voterName, String delegateName)
        {
            IVoter voter = findVoter(voterName).VoterItem;
            voter.DelegateToVoter = new Voter(delegateName);
            AddVoter(voter);
            AddVoter(voter.DelegateToVoter);
        }

        public void AddVoter(IVoter voter)
        {
            if (!voterDictionary.ContainsKey(voter.Name))
            {
                voterDictionary[voter.Name] = voter;
            }
        }

        public void DelegateVote(IVoter fromWhom, IVoter toWhom)
        {
            FindVoterResult voterResult = findVoter(fromWhom);
            if (voterResult.IsFound)
            {
                voterResult.VoterItem.DelegateToVoter = toWhom;
            }
            else
            {
                fromWhom.DelegateToVoter = toWhom;
                AddVoter(fromWhom);
            }
        }

        FindVoterResult findVoter(String voterName)
        {
            return findVoter(new Voter(voterName));
        }

        FindVoterResult findVoter(IVoter voter)
        {
            String voterName = (voter != null) ? voter.Name : "";
            if (voterDictionary.ContainsKey(voterName))
            {
                return new FindVoterResult(voterDictionary[voterName], true);
            }

            return new FindVoterResult(new Voter(voterName), false);
        }

        bool AreVotesInvalidForVoter(IVoter voter)
        {
            HashSet<IVoter> uniqueVoters = new HashSet<IVoter>();
            FindVoterResult voterResult = findVoter(voter);
            while (voterResult.IsFound)
            {
                if (uniqueVoters.Contains(voterResult.VoterItem))
                    return true;
                uniqueVoters.Add(voterResult.VoterItem);
                voterResult = findVoter(voterResult.VoterItem.DelegateToVoter);
            }
            return false;
        }

        public List<IVoter> GetVotersWithValidVotes()
        {
            return voterDictionary.Values.ToList()
                .FindAll(voter => !AreVotesInvalidForVoter(voter));
        }

        public IOrderedEnumerable<KeyValuePair<String, int>> GetAggregatedVoterChoicesForValidVotes()
        {
            IDictionary<String, int> voterDictionary = new Dictionary<String, int>();
            foreach (var voter in GetVotersWithValidVotes())
            {
                FillVoterDictionaryWithChoices(voterDictionary, voter.Choices);
            }
            return voterDictionary.OrderByDescending(v => v.Value);
        }

        public IOrderedEnumerable<KeyValuePair<String, int>> GetAggregatedVoterChoicesForInValidVotes()
        {
            IDictionary<String, int> voterDictionary = new Dictionary<String, int>();
            const String invalidKey = "invalid";
            foreach (var voter in GetVotersWithInValidVotes())
            {
                if(voterDictionary.ContainsKey(invalidKey))
                {
                    voterDictionary[invalidKey] += 1;
                }
                else
                {
                    voterDictionary[invalidKey] = 1;
                }
            }
            return voterDictionary.OrderBy(v => v.Value);
        }

        List<IVoter> GetVotersWithInValidVotes()
        {
            return voterDictionary.Values.ToList()
                .FindAll(voter => AreVotesInvalidForVoter(voter));
        }

        void FillVoterDictionaryWithChoices(IDictionary<String, int> voterDictionary, IDictionary<String, int> choices)
        {
            foreach (var choice in choices)
            {
                if (voterDictionary.ContainsKey(choice.Key))
                {
                    voterDictionary[choice.Key] += choice.Value;
                }
                else
                {
                    voterDictionary[choice.Key] = choice.Value;
                }
            }
        }

    }


}

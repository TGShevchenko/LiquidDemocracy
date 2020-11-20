using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

namespace LiquidDemocracy
{
    /// <summary>
    /// Interface for a VoterController class
    /// </summary>
    public interface IVoterController
    {
        void AddVoter(IVoter voter);
        void AddVoterWithChoice(String voterName, String choice);
        void AddVoterWithDelegate(String voterName, String delegateName);

        void DelegateVote(IVoter fromWhom, IVoter toWhom);
        IOrderedEnumerable<KeyValuePair<String, int>> GetAggregatedVoterChoicesForValidVotes();
        IOrderedEnumerable<KeyValuePair<String, int>> GetAggregatedVoterChoicesForInValidVotes();
    }
}

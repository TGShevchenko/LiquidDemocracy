using System;
using System.Collections.Generic;
using System.Text;

namespace LiquidDemocracy
{
    /// <summary>
    /// A storage class that keeps the returning result for Voter.
    /// It provides a convenient way to know when a Voter is found, and it avoids null if a Voter is not found.
    /// </summary>
    public class FindVoterResult
    {
        public IVoter VoterItem { get; set; }
        public bool IsFound { get; set; }
        public FindVoterResult()
        {
            IsFound = false;
        }

        public FindVoterResult(IVoter voter, bool isFound)
        {
            VoterItem = voter;
            IsFound = isFound;
        }
    }
}

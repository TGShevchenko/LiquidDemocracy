using System;
using System.Collections.Generic;
using System.Text;

namespace LiquidDemocracy
{
    /// <summary>
    /// Interface for a Voter class
    /// </summary>
    public interface IVoter
    {
        void Pick(String item);
        IVoter DelegateToVoter { get; set; }
        IVoter DelegateFromVoter { get; set; }
        IDictionary<String, int> Choices { get; set; }
        String Name { get; set; }
    }
}

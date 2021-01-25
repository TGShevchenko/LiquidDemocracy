using System;
using System.Collections.Generic;
using System.Text;

namespace LiquidDemocracy
{
    /// <summary>
    /// Class that represents a Voter with corresponding operations: 
    ///   Pick - to pick a choice and DelegateTo to delegate to another Voter
    /// </summary>
    public class Voter : IVoter
    {
        private string name;
        private IVoter delegateToVoter;
        private IVoter delegateFromVoter;
        private IDictionary<String, int> choices = new Dictionary<String, int>();

        public IDictionary<String, int> Choices { get => choices; set { choices = value; } }
        public String Name { get => name; set { name = value; } }

        public IVoter DelegateFromVoter
        {
            get => delegateFromVoter;
            set
            {
                delegateFromVoter = value;
            }
        }

        public IVoter DelegateToVoter
        {
            get => delegateToVoter;
            set
            {
                delegateToVoter = value;
                delegateToVoter.Choices = Choices;
                delegateToVoter.DelegateFromVoter = this;
            }
        }
        public Voter(String name)
        {
            this.Name = name;
        }

        public void Pick(String item)
        {
            IDictionary<String, int> choicesDict = Choices;
            if (DelegateFromVoter != null)
            {
                choicesDict = DelegateFromVoter.Choices;
            }
            UpdateChoiceDictionary(item, choicesDict);
            if (DelegateFromVoter != null)
            {
                Choices = choicesDict;
            }
        }

        void UpdateChoiceDictionary(String item, IDictionary<String, int> choicesDict)
        {
            if (choicesDict.ContainsKey(item))
            {
                choicesDict[item]++;
            }
            else
            {
                choicesDict[item] = 1;
            }
        }

        int GetChoiceCount(String item)
        {
            if (choices.ContainsKey(item))
            {
                return choices[item];
            }
            return 0;
        }

        void DelegateTo(Voter delegateTo)
        {
            DelegateToVoter = delegateTo;
        }

        IVoter GetDelegateTo()
        {
            return DelegateToVoter;
        }
    }
}

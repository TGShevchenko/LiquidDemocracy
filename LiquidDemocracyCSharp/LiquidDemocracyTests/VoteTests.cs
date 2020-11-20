using System;
using Xunit;
using LiquidDemocracy;
using System.Collections.Generic;
using System.Linq;

namespace LiquidDemocracyTests
{
    public class VoteTests
    {
        [Fact]
        public void AddVoterWithChoiceTest()
        {
            IVoterController voterController = new VoterController();
            voterController.AddVoterWithChoice("Alice", "Butter");

            IDictionary<String, int> choices = voterController.GetAggregatedVoterChoicesForValidVotes()
                .ToDictionary(pair => pair.Key, pair => pair.Value);
            Assert.True(choices.ContainsKey("Butter"));
            Assert.Equal(1, choices["Butter"]);
        }

        [Fact]
        public void AddVoterWithDelegateTest()
        {
            IVoterController voterController = new VoterController();
            voterController.AddVoterWithDelegate("Alice", "Bob");

            IDictionary<String, int> choices = voterController.GetAggregatedVoterChoicesForValidVotes()
                .ToDictionary(pair => pair.Key, pair => pair.Value);
            Assert.Equal(0, choices.Count);
        }

        [Fact]
        public void AddVoterWithChoiceAndDelegateTest()
        {
            IVoterController voterController = new VoterController();
            voterController.AddVoterWithChoice("Alice", "Pizza");
            voterController.AddVoterWithDelegate("Alice", "Bob");
            IDictionary<String, int> choices = voterController.GetAggregatedVoterChoicesForValidVotes()
                .ToDictionary(pair => pair.Key, pair => pair.Value);
            Assert.True(choices.ContainsKey("Pizza"));
            Assert.Equal(2, choices["Pizza"]);
        }


        [Fact]
        public void GetAggregatedVoterChoicesForValidVotesTest()
        {
            IVoterController voterController = new VoterController();
            voterController.AddVoterWithChoice("Alice", "Pizza");
            voterController.AddVoterWithDelegate("Bob", "Carol");
            voterController.AddVoterWithChoice("Carol", "Salad");
            voterController.AddVoterWithDelegate("Dave", "Eve");
            voterController.AddVoterWithDelegate("Eve", "Mallory");
            voterController.AddVoterWithChoice("Eve", "Pizza");
            voterController.AddVoterWithDelegate("Mallory", "Eve");

            IDictionary<String, int> choices = voterController.GetAggregatedVoterChoicesForValidVotes()
                .ToDictionary(pair => pair.Key, pair => pair.Value);
            Assert.True(choices.ContainsKey("Pizza"));
            Assert.Equal(1, choices["Pizza"]);
            Assert.True(choices.ContainsKey("Salad"));
            Assert.Equal(2, choices["Salad"]);
        }

        [Fact]
        public void GetAggregatedVoterChoicesForInValidVotesTest()
        {
            IVoterController voterController = new VoterController();
            voterController.AddVoterWithChoice("Alice", "Pizza");
            voterController.AddVoterWithDelegate("Bob", "Carol");
            voterController.AddVoterWithChoice("Carol", "Salad");
            voterController.AddVoterWithDelegate("Dave", "Eve");
            voterController.AddVoterWithDelegate("Eve", "Mallory");
            voterController.AddVoterWithChoice("Eve", "Pizza");
            voterController.AddVoterWithDelegate("Mallory", "Eve");

            IDictionary<String, int> invalidVotes = voterController.GetAggregatedVoterChoicesForInValidVotes()
                .ToDictionary(pair => pair.Key, pair => pair.Value);
            Assert.True(invalidVotes.ContainsKey("invalid"));
            Assert.Equal(3, invalidVotes["invalid"]);
        }
    }
}

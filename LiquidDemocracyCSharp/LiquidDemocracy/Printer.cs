using System;
using System.Collections.Generic;
using System.Text;

namespace LiquidDemocracy
{
    /// <summary>
    /// Printer class that prints names and choices
    /// </summary>
    class Printer : IPrinter
    {
        IVoterController voterController;
        public Printer(IVoterController voterController)
        {
            this.voterController = voterController;
        }
        public void Print()
        {
            foreach(var choice in voterController.GetAggregatedVoterChoicesForValidVotes())
            {
                Console.Out.WriteLine(choice.Value + " " + choice.Key);
            }
            foreach (var choice in voterController.GetAggregatedVoterChoicesForInValidVotes())
            {
                Console.Out.WriteLine(choice.Value + " " + choice.Key);
            }
        }
    }
}

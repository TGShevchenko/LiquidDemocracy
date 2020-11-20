using System;
using System.Collections.Generic;

namespace LiquidDemocracy
{
    class Program
    {
        static void Main(string[] args)
        {
            IVoterController voterController = new VoterController();
            IPrinter printer = new Printer(voterController);
            UserInputController userInputController = new UserInputController(voterController, printer);
            userInputController.Run();
        }
    }
}

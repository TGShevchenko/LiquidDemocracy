using System;
using System.Collections.Generic;
using System.Text;
using System.Text.RegularExpressions;

namespace LiquidDemocracy
{
    /// <summary>
    /// Decodes the use input and extracts the commands for voters
    /// </summary>
    class UserInputController
    {
        IVoterController voterController;
        IPrinter printer;
        public UserInputController(IVoterController voterController, IPrinter printer)
        {
            this.voterController = voterController;
            this.printer = printer;
        }
        public void Run()
        {
            const String initialMessage = "Please, enter the command as follows: VOTER COMMAND [PICK or DELEGATE]. Press q for exit.";
            Console.WriteLine(initialMessage);
            while (true)
            {
                string commandLine = Console.ReadLine();
                Regex argReg = new Regex(@"\w+|""[\w\s]*""");

                int commandsCount = argReg.Matches(commandLine).Count;
                string[] splittedLine = ExtractCommandsFromCommandLine(commandLine, argReg);
                switch(splittedLine.Length)
                {
                    case 1:
                        if(splittedLine[0].ToUpper().Equals("Q"))
                        {
                            return;
                        }
                        else
                        {
                            Console.WriteLine(initialMessage);
                        }
                        break;
                    case 3:
                        if(DecodeSplittedLine(splittedLine))
                        {
                            printer.Print();
                        }
                        break;
                    default:
                        Console.WriteLine(initialMessage);
                        break;
                }
            }
        }

        string[] ExtractCommandsFromCommandLine(string commandLine, Regex argReg)
        {
            string[] commands = new string[argReg.Matches(commandLine).Count];

            int i = 0;
            foreach (var enumerator in argReg.Matches(commandLine))
            {
                commands[i++] = (string)enumerator.ToString();
            }

            return commands;
        }

        bool DecodeSplittedLine(string[] splittedLine)
        {
            String voterName = splittedLine[0];
            String commandString = splittedLine[1];
            String choiceOrDelegate = splittedLine[2];
            if (commandString.ToUpper().Equals("PICK"))
            {
                voterController.AddVoterWithChoice(voterName, choiceOrDelegate);
                return true;
            }
            else if (commandString.ToUpper().Equals("DELEGATE"))
            {
                voterController.AddVoterWithDelegate(voterName, choiceOrDelegate);
                return true;
            }
            return false;
        }
    }
}

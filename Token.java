import java.util.HashMap;
import java.util.Map;

public class Token
{

    public enum Category
    {
        Class("Class"), End("End"),Integer("Integer"),Double("Double"),Add("Add"),Subtract("Subtract"),Divide("Divide"),
        Multiple("Multiply"),Equals("Equals"),Greater("Greater"),
        Greaterthan("Greater Than"),Less("Less"),Lessthan("Less Than"),Leftparen("Left Paren"),Rightparen("Right Paren"),
        Leftbracket("Left Bracket"),Rightbracket("Right Bracket"),Not("Not"),
        Notequals("Not Equals"),If ("If"),Elseif("Else if"),Else("Else"),Output("Output"),
        On("On"),Create("Create"),Constant("Constant"),Me("Me"),Until("Until"),Public("Public"),
        Private("Private"),Alert("Alert"),Detect("Detect"),Always("Always"),Check("Check"),
        Parent("Parent"),Blueprint("Blueprint"),Native("Native"),Inherits("Inherits"),Cast("Cast"),
        Input("Input"),Say("Say"),Now("Now"),While("While"),Packagename("Package Name"),
        Times("Times"),Repeat("Repeat"),Returns("Returns"),Return("Return"),And("And"),Or("Or"),
        Undefined("Undefined"),Shared("Shared"),Action("Action"),Colon("Colon"),IntegerKeyword("Integer Keyword"),
        NumberKeyword("Number Keyword"), Text("Text"),BooleanKeyword("Boolean Keyword"),Boolean("Boolean"),Period("."), CustomToken("Custom Token"),
        LineComment("Line Comment"), BlockComment("Block Comment"), Comma("Comma"), Use("Use");


        private String myCate;

        private Category(String myName)
        {
            this.myCate = myName;
        }

        public String getMyCate()
        {
            return myCate;
        }
    }

    HashMap<String, Integer> myLexer;
    Category[] myCat = Category.values();

    public Token()
    {
        myLexer = new HashMap<String, Integer>();
    }

    public void setValues(String read, int x)
    {
        this.myLexer.put(read, x);
    }


    public void goodPrint()
    {
        //imported from chatgpt on how to iterate through the hashmap without knowing the key
        //and getting the two values (String and Integer) for printing
        //added a value for category from the enum above, in the same order as the int categories so
        //used them as a counter
       for (Map.Entry<String, Integer> entry: myLexer.entrySet())
       {
           String token = entry.getKey();
           int category = entry.getValue();
           String myCategory = myCat[category - 1].getMyCate();

           System.out.println("Token Category: " + category + " " + myCategory + " Keyword, Value " + token);
        }
    }

    public void badPrint(String Read)
    {
        System.out.println("Error, Invalid Token " + Read);
    }

    //Function to tell if a passed in string is an integer
    //code from chatgpt, idea from classmates
    public boolean isInt(String read)
    {
           try{
               Integer.parseInt(read);
               return true;
           } catch (NumberFormatException e){
               return false;
         }
    }

    //Function to tell if a passed in string is a double
    //code from chatgpt, idea from classmates
    public boolean isFloat(String read)
    {
        try{
            Double.parseDouble(read);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    // taken from https://www.tutorialspoint.com/java/java_string_matches.htm
    // uses match to check the values in the string
    // changed the code based on input from chatgpt
    public boolean isId(String read)
    {
        if (read.matches("[a-zA-Z_][a-zA-Z0-9_]*"))
        {
            this.setValues(read, 61);

            return true;
        }
        return false;
    }

    public boolean checkID(String x)
    {
        for (Map.Entry<String, Integer> entry: myLexer.entrySet())
        {
            String token = entry.getKey();
            if (token.matches("\\Q" + x + "\\E"))
            {
                return true;
            }
        }

        return false;
    }
    public Boolean longestMatch(String x)
    {

        if (isInt(x)) {
            this.setValues(x, 3);
            return true;
        }
        if (isFloat(x))
        {
            this.setValues(x, 4);
            return true;
        }

        if(this.checkID(x))
        {
            return true;
        }

        return switch (x) {
            case "class" -> {
                this.setValues(x, 1);
                yield true;
            }
            case "end" -> {
                this.setValues(x, 2);
                yield true;
            }
            case "+" -> {
                this.setValues(x, 5);
                yield true;
            }
            case "-" -> {
                this.setValues(x, 6);
                yield true;
            }
            case "/" -> {
                this.setValues(x, 7);
                yield true;
            }
            case "*" -> {
                this.setValues(x, 8);
                yield true;
            }
            case "=" -> {
                this.setValues(x, 9);
                yield true;
            }
            case ">" -> {
                this.setValues(x, 10);
                yield true;
            }
            case ">=" -> {
                this.setValues(x, 11);
                yield true;
            }
            case "<" -> {
                this.setValues(x, 12);
                yield true;
            }
            case "<=" -> {
                this.setValues(x, 13);
                yield true;
            }
            case "(" -> {
                this.setValues(x, 14);
                yield true;
            }
            case ")" -> {
                this.setValues(x, 15);
                yield true;
            }
            case "[" -> {
                this.setValues(x, 16);
                yield true;
            }
            case "]" -> {
                this.setValues(x, 17);
                yield true;
            }
            case "not", "Not" -> {
                this.setValues(x, 18);
                yield true;
            }
            case "not=", "Not=" -> {
                this.setValues(x, 19);
                yield true;
            }
            case "if", "If" -> {
                this.setValues(x, 20);
                yield true;
            }
            case "elseif" -> {
                this.setValues(x, 21);
                yield true;
            }
            case "else" -> {
                this.setValues(x, 22);
                yield true;
            }
            case "output", "Output" -> {
                this.setValues(x, 23);
                yield true;
            }
            case "on", "On" -> {
                this.setValues(x, 24);
                yield true;
            }
            case "create", "Create" -> {
                this.setValues(x, 25);
                yield true;
            }
            case "constant" -> {
                this.setValues(x, 26);
                yield true;
            }
            case "me" -> {
                this.setValues(x, 27);
                yield true;
            }
            case "until" -> {
                this.setValues(x, 28);
                yield true;
            }
            case "public" -> {
                this.setValues(x, 29);
                yield true;
            }
            case "private" -> {
                this.setValues(x, 30);
                yield true;
            }
            case "alert" -> {
                this.setValues(x, 31);
                yield true;
            }
            case "detect" -> {
                this.setValues(x, 32);
                yield true;
            }
            case "always" -> {
                this.setValues(x, 33);
                yield true;
            }
            case "check" -> {
                this.setValues(x, 34);
                yield true;
            }
            case "parent" -> {
                this.setValues(x, 35);
                yield true;
            }
            case "blueprint" -> {
                this.setValues(x, 36);
                yield true;
            }
            case "system" -> {
                this.setValues(x, 37);
                yield true;
            }
            case "is" -> {
                this.setValues(x, 38);
                yield true;
            }
            case "cast" -> {
                this.setValues(x, 39);
                yield true;
            }
            case "input" -> {
                this.setValues(x, 40);
                yield true;
            }
            case "say" -> {
                this.setValues(x, 41);
                yield true;
            }
            case "now" -> {
                this.setValues(x, 42);
                yield true;
            }
            case "while" -> {
                this.setValues(x, 43);
                yield true;
            }
            case "package" -> {
                this.setValues(x, 44);
                yield true;
            }
            case "times" -> {
                this.setValues(x, 45);
                yield true;
            }
            case "repeat" -> {
                this.setValues(x, 46);
                yield true;
            }
            case "returns" -> {
                this.setValues(x, 47);
                yield true;
            }
            case "return" -> {
                this.setValues(x, 48);
                yield true;
            }
            case "and" -> {
                this.setValues(x, 49);
                yield true;
            }
            case "or" -> {
                this.setValues(x, 50);
                yield true;
            }
            case "null" -> {
                this.setValues(x, 51);
                yield true;
            }
            case "static" -> {
                this.setValues(x, 52);
                yield true;
            }
            case "action" -> {
                this.setValues(x, 53);
                yield true;
            }
            case ":" -> {
                this.setValues(x, 54);
                yield true;
            }
            case "integer", "Integer" -> {
                this.setValues(x, 55);
                yield true;
            }
            case "number" -> {
                this.setValues(x, 56);
                yield true;
            }
            case "text" -> {
                this.setValues(x, 57);
                yield true;
            }
            case "boolean" -> {
                this.setValues(x, 58);
                yield true;
            }
            case "true", "false" -> {
                this.setValues(x, 59);
                yield true;
            }
            case "." -> {
                this.setValues(x, 60);
                yield true;
            }
            case ","->{
                this.setValues(x, 64);
                yield true;
            }
            case "use"->{
                this.setValues(x, 65);
                yield true;
            }
            default -> false;
        };//ends the switch statement
    }//ends the isToken method

    public void clearMap()
    {
        this.myLexer.clear();
    }
}

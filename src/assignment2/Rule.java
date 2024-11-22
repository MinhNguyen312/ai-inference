package assignment2;

//Source code is decompiled from a .class file using FernFlower decompiler.
import java.util.List;

public class Rule {
List<String> premises;
String conclusion;

public Rule(List<String> var1, String var2) {
   this.premises = var1;
   this.conclusion = var2;
}

public String toString() {
   String var10000 = this.premises.toString();
   return "\nPremises: " + var10000 + "\nConclusion: " + this.conclusion;
}
}

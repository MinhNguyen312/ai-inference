package assignment2;

import java.io.IOException;
import java.util.*;

public class Main {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
//	        if (args.length < 2) {
//	            System.out.println("Usage: java Main <filename> <method>");
//	            return;
//	        }
//
//        String filename = args[0];
//        String method = args[1].toUpperCase();

//        String filename = "src/main/java/me/discordbot/test_HornKB.txt";
//        String filename = "src/main/java/me/discordbot/test14_horn.txt";
        String filename = "src\\assignment2\\test10_horn.txt";
//        String filename = "src/main/java/me/discordbot/test_genericKB_1.txt";
//        String filename = "src/main/java/me/discordbot/test1_genericKB.txt";
        String method = "TT";

        String query = null;
        switch (method) {
            case "TT":
                Map<String, String> data = InferenceEngine.parseInput(filename);
                List<String> kb = Arrays.asList(data.get("KB").split(";"));
                query = data.get("Query");

                System.out.println(kb);
                System.out.println(query);
                System.out.println("---------------------");

                System.out.println(TruthTable.evaluateTT(kb, query));
                break;
            case "FC":
                Map<String, Object> fcData = InferenceEngine.parseInputForChainingAlgorithm(filename);
                for (String key : fcData.keySet()) {
                    System.out.println("Key [" + key + "]: " + fcData.get(key).toString());
                }
                System.out.println("---------------------");

                Set<String> factsFC = (Set<String>) fcData.get("facts");
                List<Rule> rulesFC =  (List<Rule>) fcData.get("rules");
                query = (String) fcData.get("query");

                System.out.println(ForwardChaining.evaluateFC(factsFC, rulesFC, query));
                break;
            case "BC":
                Map<String, Object> bcData = InferenceEngine.parseInputForChainingAlgorithm(filename);

                Set<String> factsBC = (Set<String>) bcData.get("facts");
                List<Rule> rulesBC = (List<Rule>) bcData.get("rules");
                query = (String) bcData.get("query");

                for (String key : bcData.keySet()) {
                    System.out.println("Key [" + key + "]: " + bcData.get(key).toString());
                }

                System.out.println(BackwardChaining.evaluateBC(factsBC, rulesBC, new HashSet<>(), query));
                break;
            case "RES":
                Map<String, String> parsedData = InferenceEngine.parseInput(filename);
                List<String> generalKb = Arrays.asList(parsedData.get("KB").split(";"));
                query = parsedData.get("query");

                List<CNFConverter.Expression> resKb = new ArrayList<>();

                for (String exp : generalKb) {
                	if(!exp.trim().isEmpty()) {
                        CNFConverter.Expression expression = CNFConverter.parseExpression(exp.trim());
                        CNFConverter.Expression convertedExpression = CNFConverter.toCNF(expression);
                	    resKb.add(convertedExpression);
                	} 
                }

                CNFConverter.Expression kbInCNF = CNFConverter.combineExpressions(resKb);
                System.out.println("Original KB: " + generalKb);
                System.out.println("Converted KB:" + CNFConverter.printExpression(kbInCNF));
                
                break;
            default:
                System.out.println("Invalid method. Use TT, FC, or BC.");
        }
    }
}


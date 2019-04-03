import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    private static String INPUT = "F:\\Project 1\\LogAnalytics\\Resources\\access.log.fullhd.txt";
    private static String OUTPUT = "F:\\Project 1\\LogAnalytics\\Resources\\result.txt";
    private static Map<String, Integer> ipList = new HashMap<>();
    private static Map<String, Integer> apiList = new HashMap<>();
    private static Map<String, Integer> browserList = new HashMap<>();

    public static void main(String[] args) throws IOException {
        PrintStream out = new PrintStream(new FileOutputStream(OUTPUT));
        System.setOut(out);
        Instant start = Instant.now();
        Main mn = new Main();
        String oneLine;

        File folder = new File("F:\\Project 1\\LogAnalytics\\Resources\\INPUT\\");
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                INPUT = file.toString();

                FileLog fileLog = new FileLog(INPUT, OUTPUT);
                while((oneLine = fileLog.br.readLine()) != null){
                    if (oneLine.length() > 0) {
                        String[] splitedLines = oneLine.split(" ");

                        if (splitedLines.length > 0){
                            mn.mapStatistics(splitedLines[0], ipList);
                        }

                        if (splitedLines.length > 6){
                            mn.apiStatistics(splitedLines[6]);

                            String browser = mn.browserValidate(splitedLines);
                            if (browser != null) {
                                mn.mapStatistics(browser, browserList);
                            }
                        }
                    }
                }
            }
        }
        System.out.println("IP List:");
        mn.showMap(ipList);
        System.out.println();

        Map<String, Integer> sortedApiList = sortByValue(apiList);
        System.out.println("Top 5 API Called:");
        mn.showMapApi(sortedApiList);
        System.out.println();

        System.out.println("List of Browser has been recorded:");
        mn.showMap(browserList);
        System.out.println();

        System.out.println("Duration:");
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }

    public String browserValidate (String[] splitedLines){
        int mozillaVerified = 0;
        int chrome = 0;
        int safari = 0;
        int mobileSafari = 0;
        for(int pos=11; pos < splitedLines.length; pos++){
            if ((splitedLines[pos].contains("Mozilla")) || (mozillaVerified == 1)){
                if ((mozillaVerified == 0) && (pos+6 < splitedLines.length)){
                    pos += 6;
                }
                mozillaVerified = 1;
                if (splitedLines[pos].contains("OPR"))
                    return "Opera";
                if (splitedLines[pos].contains("Firefox"))
                    return "Firefox";
                if (splitedLines[pos].contains("Safari Line"))
                    return "Safari";
                if (splitedLines[pos].contains("Edge"))
                    return "IE";
                if (splitedLines[pos].contains("coc_coc"))
                    return "coc_coc";
                if (splitedLines[pos].contains("UCBrowser"))
                    return "UCBrowser";
                if (splitedLines[pos].contains("SamsungBrowser"))
                    return "SamsungBrowser";
                if (splitedLines[pos].contains("FBBV"))
                    return "Facebook In-app Browser";
                if (splitedLines[pos].contains("Chrome"))
                    chrome=1;
                if (splitedLines[pos].contains("Safari"))
                    safari=1;
                if (splitedLines[pos].contains("Mobile Safari"))
                    mobileSafari=1;
            }
            if((mozillaVerified == 0) && (splitedLines[pos].equals("Opera"))){
                    return "Opera Mini";
            }
        }

        if (chrome == 0){
            if (safari == 1){
                return "Safari";
            }
            if (mobileSafari == 1){
                return "Android Browser";
            }
        }else{
            return "Chrome";
        }

        if (mozillaVerified == 0) {
            return null;
        }
        return "UnknownBrowser";
    }

    public void apiStatistics(String apiLine){
        if ((apiLine.length() >= 4) && (apiLine.substring(0, 4).equals("/api"))){
            for(int pos=5; pos < apiLine.length(); pos++){
                Character ch = apiLine.charAt(pos);
                if ((ch.equals('/')) || ch.equals('?')){
                    apiLine = apiLine.substring(0, pos-1);
                    break;
                }
            }

            if(!(apiList.containsKey(apiLine))){
                apiList.put(apiLine, 1);
            }else{
                apiList.put(apiLine, apiList.get(apiLine) + 1);
            }
        }
    }

    public void mapStatistics (String key, Map<String, Integer> tempMap){
        if(!(tempMap.containsKey(key))){
            tempMap.put(key, 1);
        }else{
            tempMap.put(key, tempMap.get(key) + 1);
        }
    }

    public void showMap(Map<String,Integer> tempMap){
        Iterator<Map.Entry<String, Integer>> iter = tempMap.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry<String, Integer> entry = iter.next();
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }
    public void showMapApi(Map<String,Integer> tempMap){
        int i = 0;
        Iterator<Map.Entry<String, Integer>> iter = tempMap.entrySet().iterator();
        while (iter.hasNext() && (i < 5)){
            i++;
            Map.Entry<String, Integer> entry = iter.next();
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
    }

    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap) {

        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}

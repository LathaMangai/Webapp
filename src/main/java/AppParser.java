

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.TreeMap;
import java.util.regex.*;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.collections4.MultiMap;


@SuppressWarnings("deprecation")
public class AppParser {

                public static String REGEX_FIND_WORD = "(?i).*?\\b%s\\b.*?";

                public static boolean containsWord(String text, String word) {
                                // System.out.println("t"+text+"w"+word);
                                String regex = String.format(REGEX_FIND_WORD, Pattern.quote(word));
                                return text.matches(regex);
                }

                public static void main(String args[]) throws Exception {

                                String s = "ss";

                                Pattern pattern = Pattern.compile("s");
                                Matcher matcher = null;
                                matcher = pattern.matcher("Ccontroller");
                                // System.out.print(matcher.find());
                                // System.out.println(matcher.group());
                                String patternString = "Controller";
//(.* word1.* word2.* )|(.* word2.* word1.*)
                                //\bcat\b.*\bmat\b
                                File f = new File("d:/tmp/jcg.txt");
                                BufferedReader br = new BufferedReader(new FileReader(f));
                                System.out.println("read the parseroutput");
                                long len = f.length();
                                System.out.println("file " + len);
                                int count = 0;
                                int totalLines = 0;
                                String cline;
                                //ArrayList list=new ArrayList();
                                @SuppressWarnings("deprecation")
                                MultiValueMap<String, String> mmap = new MultiValueMap<>();
                                while ((cline = br.readLine()) != null) {
                                                totalLines++;

                                                if (containsWord(cline, "M:")) {
                                                                if (containsWord(cline,patternString)) { count++;
                                                                
                                                                  //System.out.println("countdss"+count);
                                                                // System.out.println("cl"+cline); //cmap.put(i,cline); }
                                                                  String splits[]=cline.split(" ",2);
                                                                  
                                                                  //System.out.println("fd"+splits[0]+"(method)"+splits[1]);
                                                                  
                                                                  String controller=splits[0];
                                                                  
                                                                  
                                                                  mmap.put(controller, splits[1]);
                                                                //System.out.println("fd"+count);  
                                                                  }
                                                     
                                                }
                                                
                                }
                                
                                Set ss=mmap.keySet();
                                List<String> keylist = new ArrayList<>(mmap.keySet());
                    Collections.sort(keylist);
                    //for(String key : keylist) {
                     //   System.out.println(key +  " : " + map.get(key));
                   // }

                                Iterator iter=keylist.iterator();
                                int itet=0;
                                while (iter.hasNext())
                                {
                                                itet++;
                                                String key=(String)iter.next();
                                                String ccs[]=key.split(":",3);
                                                
                                                //System.out.println("Controller"+key+":Value:"+mmap.get(key));
                                                System.out.println("controllerclass:::"+ccs[1]+":::::method:"+ccs[2]);
                                                
                                }
                                
                               
                 System.out.println("values"+itet);
                // Pattern pattern =
                                /*
                                * Pattern.compile(patternString);
                                * 
                                 * Matcher matcher =null; //boolean matches = null; int count=0; CZ (contentLine
                                * != null) { // System.out.println(contentLine); contentLine = br.readLine();
                                * matcher = pattern.matcher("Controller"); boolean matches = matcher.matches();
                                * if(matches==true) {
                                * 
                                 * System.out.println("hi"+contentLine); count++; }
                                * System.out.println("ccounnt"+count); }
                                * 
                                 * }
                                */
                
                }}

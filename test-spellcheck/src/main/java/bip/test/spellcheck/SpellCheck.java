package bip.test.spellcheck;

import org.apache.commons.lang3.StringUtils;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.MultiThreadedJLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.language.Persian;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramezani on 8/2/2018.
 *
 * http://wiki.languagetool.org/java-api
 * https://github.com/reza1615/Persian-Spell-checker
 * https://community.languagetool.org/rule/list?lang=fa
 * https://languagetool.org/
 * https://languagetool.org/languages
 */
public class SpellCheck {
    static Language language = new Persian();

    public static boolean hasSuggestion(String text) throws IOException {
        if(text==null || "".equals(text))return false;
        JLanguageTool langTool = new JLanguageTool(language);
        return langTool.check(text).size()>0;
    }

    public static String[] suggester(String text) throws IOException {
        if(text==null || "".equals(text))return new String[]{};
        StringBuilder sb = new StringBuilder(text);
        JLanguageTool langTool = new JLanguageTool(language);
        List<RuleMatch> matches = langTool.check(text);
        for (int i=matches.size()-1;i>=0;i--) {
            RuleMatch match = matches.get(i);
            sb.replace(match.getFromPos(),match.getToPos(),match.getSuggestedReplacements().get(0));//FIXME: get(0)
        }
        String result = sb.toString();
        if(result.equalsIgnoreCase(text)){
            return new String[]{};
        }else{
            return new String[]{sb.toString()};
        }

    }

    public static List<RuleMatch> check(String text) throws IOException {
        if(text==null || "".equals(text))return new ArrayList<RuleMatch>();
        JLanguageTool langTool = new JLanguageTool(language);
        return langTool.check(text);
    }


    public static void main(String[] args) throws IOException {
        String texts[] = {"تسوت"
                , "اختاپوسس"
                , "تست"

/*
                , "لطفا متن خود را اینجا قرار دهید . یا بررسی کنید که این متن را\u200C برای دیدن بعضی بعضی از اشکال\u200Cهایی که ابزار زبان توانسته تشخیس هدد. درباره ی نرم افزارهای بررسی کننده های گرامر چه فکر می کنید؟ لطفا در نظر داشته باشید که آن\u200Cها بی نقص نمی باشند.\u200E"
*/
        };

        for (int i = 0; i < texts.length; i++) {
            String text = texts[i];

            System.out.println("-------------------------------------\ntext="+text);
            System.out.println("hasSuggestion="+hasSuggestion(text));
            System.out.println("suggester="+ StringUtils.join(suggester(text),"\n"));
            System.out.println("check="+check(text));
        }
    }


    public static void main1(String[] args) throws IOException {
        System.out.println("SpellCheck starting....");
        //JLanguageTool langTool = new JLanguageTool(language);
        JLanguageTool langTool = new JLanguageTool(language);
        //JLanguageTool langTool = new MultiThreadedJLanguageTool(language);
// comment in to use statistical ngram data:
//langTool.activateLanguageModelRules(new File("/data/google-ngram-data"));
        //String text ="A sentence with a error in the Hitchhiker's Guide tot he Galaxy";
        String text = "لطفا متن خود را اینجا قرار دهید . یا بررسی کنید که این متن را\u200C برای دیدن بعضی بعضی از اشکال\u200Cهایی که ابزار زبان توانسته تشخیس هدد. درباره ی نرم افزارهای بررسی کننده های گرامر چه فکر می کنید؟ لطفا در نظر داشته باشید که آن\u200Cها بی نقص نمی باشند.\u200E";
        //System.out.println("AnalyzedSentence="+ langTool.analyzeText(text));
        StringBuilder sb = new StringBuilder(text);

        List<RuleMatch> matches = langTool.check(text);
        for (int i=matches.size()-1;i>=0;i--) {
            RuleMatch match = matches.get(i);
            System.out.println("Potential error at characters " +
                    match.getFromPos() + "-" + match.getToPos() + ": " +
                    match.getMessage());
            System.out.println("Suggested correction(s): " +
                    match.getSuggestedReplacements());

            sb.replace(match.getFromPos(),match.getToPos(),match.getSuggestedReplacements().get(0));//FIXME: get(0)
        }

        System.out.println("text="+text);
        System.out.println("result="+sb.toString());

        System.out.println("SpellCheck end.");
    }

}

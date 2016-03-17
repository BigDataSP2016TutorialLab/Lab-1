/**
 * Created by Vamsi on 3/9/2016.
 */

public class TweetWithSentiment {

    private String line;
    private String cssClass;

    public TweetWithSentiment() {
    }

    public TweetWithSentiment(String line, String cssClass) {
        super();
        this.line = line;
        this.cssClass = cssClass;
    }

    public String getLine() {
        return line;
    }

    public String getCssClass() {
        return cssClass;
    }

    @Override
    public String toString() {
        return cssClass;
        //return "TweetWithSentiment [line=" + line + ", cssClass=" + cssClass + "]";
    }

}


package substr;

/*
* Slow when text and pattern are repetative
Needs backup
*/
public class NMTimeAlg {
    public static void main(String[] args) {
        String text = "Hello, world";
        String pattern = "lo";
        for (int i = 0; i < text.length() - pattern.length(); i++) {
            boolean found = true;
            for (int j = 0; j < pattern.length(); j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                System.out.println("found");
                break;
            }

        }
        //Without backup

        for (int i = 0, j = 0; i < text.length() && j < pattern.length(); i++) {
            if (text.charAt(i) == pattern.charAt(j)) j++;
            else {
                i -= j;
                j = 0;
            }
            if(j == pattern.length()){
                System.out.println("found1");
            }
        }

    }


}

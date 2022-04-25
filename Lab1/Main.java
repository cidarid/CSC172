public class Main {

    /**
     * This function executes test cases for isAnagram and isRotation.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("For anagram of QweRty and QweRtY, output is "
                + isAnagram("QweRty", "QweRtY"));
        System.out.println("For anagram of qwe_123_omorw3 and 3123_owrmoq_we, "
                + "output is " + isAnagram("qwe_123_omorw3", "3123_owrmoq_we"));
        System.out.println("For rotation of 123yrewq and yreqw123, output is "
                + isRotation("123yrewq", "yreqw123"));
        System.out.println("For 0 1 2 and 1 20 , output is "
                + isRotation("0 1 2", "1 20 "));
    }

    /**
     * This function checks if two strings are anagrams of eachother. It first
     * checks if the two strings are of equal lengths, if not it returns
     * false. If they are equal lemngth, it loops through one string's
     * chars and checks that the second string has a corresponding char.
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isAnagram(String str1, String str2) {
        String _str2 = str2;
        if (str1.length() != str2.length())
            return false;
        for (char c : str1.toCharArray()) {
            String sc = String.valueOf(c);
            if(_str2.contains(sc)) {
                _str2 = _str2.replaceFirst(sc, "");
            }
            else {
                return false;
            }
        }
        return true;
    }

    /**
     * This function checks if two strings are rotations of eachother by
     * adding one to itself and checking if the second string is contained
     * in the "double string".
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isRotation(String str1, String str2) {
        String str3 = str1 + str1;
        if (str2.length() == str1.length() && str3.contains(str2)) {
            return true;
        }
        else {
            return false;
        }
    }
}

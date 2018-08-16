public class Palindrome {
    public static boolean isPalindrome(String word) {
    	word=word.toLowerCase();
    	for(int i=0; i<(word.length()/2)+1; i++)
    	{
    		System.out.println(word.charAt(i)+"  "+word.charAt(word.length()-1-i));
    		if(word.charAt(i)!=word.charAt(word.length()-1-i))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    public static void main(String[] args) {
        System.out.println(Palindrome.isPalindrome("Deleveled"));
        System.out.println(Palindrome.isPalindrome("RaCecAr"));
        System.out.println(Palindrome.isPalindrome("Hannah"));
        System.out.println(Palindrome.isPalindrome("ABCDEFGHIhgfedCBa"));
    }
}
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import me.neoblade298.neocore.shared.util.FontInfo;
import me.neoblade298.neocore.shared.util.PaginatedList;

public class Tester {

	public static void main(String args[]) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("1", 1);
		map.put("2", 2);
		System.out.println(map);
		Iterator<Integer> iter = map.values().iterator();
		iter.next();
		iter.remove();
		System.out.println(map);
		
	}

	public static int getStringPixels(String msg) {
		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;

		for (char c : msg.toCharArray()) {
			if (c == '§') {
				previousCode = true;
			}
			else if (previousCode) {
				previousCode = false;
				isBold = (c == 'l' || c == 'L');
			}
			else {
				FontInfo fi = FontInfo.getFontInfo(c);
				messagePxSize += isBold ? fi.getBoldLength() : fi.getLength();
				messagePxSize++;
			}
		}
		return messagePxSize;
	}

	public static ArrayList<String> addLineBreaks(String line, int pixelsPerLine) {
		ArrayList<String> lines = new ArrayList<String>();
		String[] words = line.split(" ");
		String curr = "";
		int linePixels = 0;
		for (String word : words) {
			int pixels = getStringPixels(word);
			if (linePixels == 0) {
				curr += word;
				linePixels += pixels;
			}
			else if (linePixels != 0 && linePixels + pixels + FontInfo.getFontInfo(' ').getLength() > pixelsPerLine) {
				lines.add(curr);
				curr = word;
				linePixels = pixels;
				System.out.println(linePixels);
			}
			else {
				curr += " " + word;
				linePixels += pixels + FontInfo.getFontInfo(' ').getLength();
				System.out.println(linePixels);
			}
		}
		lines.add(curr);
		return lines;
	}
}

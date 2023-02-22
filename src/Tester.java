import me.neoblade298.neocore.shared.util.PaginatedList;

public class Tester {

	public static void main(String args[]) {
		PaginatedList<Integer> list = new PaginatedList<Integer>();
		for (int i = 0; i < 100; i++) {
			list.add(i);
		}
		System.out.println(list.remove((i) -> {return 99 - i;}));
		System.out.println(list.get(9));
	}
}

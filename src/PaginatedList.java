

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class PaginatedList<E> implements Iterable<E> {
	private static final int DEFAULT_PAGE_SIZE = 10;
	private LinkedList<LinkedList<E>> pages = new LinkedList<LinkedList<E>>();
	private int pageSize;
	
	public PaginatedList() {
		this(DEFAULT_PAGE_SIZE);
	}
	
	public PaginatedList(int pageSize) {
		this.pageSize = pageSize;
		pages.add(new LinkedList<E>());
	}
	
	public PaginatedList(Collection<E> col, int pageSize) {
		this.pageSize = pageSize;
		Iterator<E> iter = col.iterator();
		
		LinkedList<E> page = new LinkedList<E>();
		pages.add(page);
		while (iter.hasNext()) {
			if (page.size() == pageSize) {
				page = new LinkedList<E>();
				pages.add(page);
			}
			
			page.add(iter.next());
		}
	}
	
	public PaginatedList(Collection<E> col) {
		this(col, DEFAULT_PAGE_SIZE);
	}
	
	public int size() {
		return ((pages.size() - 1) * pageSize) + pages.getLast().size();
	}
	
	public int pages() {
		return pages.size();
	}
	
	public LinkedList<E> get(int page) {
		return pages.get(page);
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public E remove(int pagenum, int index) {
		LinkedList<E> page = pages.get(pagenum);
		E item = page.remove(index);
		
		// Trickle down all pages after
		bubbleUp(pagenum);
		return item;
	}
	
	public E remove(int index) {
		return remove(index / pageSize, index % pageSize);
	}
	
	public E remove(E toRemove) {
		Iterator<LinkedList<E>> iter = pages.descendingIterator();
		int pagenum = pages.size() - 1;
		while (iter.hasNext()) {
			LinkedList<E> page = iter.next();
			int index = page.indexOf(toRemove);
			if (index != -1) {
				E item = page.remove(index);
				bubbleUp(pagenum);
				return item;
			}
			pagenum--;
		}
		return null;
	}
	
	private void bubbleUp(int pagenum) {
		// If this page is not the last page and its size is less than max size
		if (pagenum < pages.size() - 1 && pages.get(pagenum).size() < pageSize) {
			for (int i = pagenum; i + 1 < pages.size(); i++) {
				pages.get(i).add(pages.get(i + 1).removeFirst());
				// If the last page only had 1 item and now has 0, remove it
				if (pages.get(i + 1).size() == 0) {
					pages.remove(i + 1);
				}
			}
		}
	}
	
	private void trickleDown(int pagenum) {
		if (pages.get(pagenum).size() > pageSize) {
			System.out.println("1");
			// Push the last item on current page to first item on next page
			for (int i = pagenum; i + 1 < pages.size(); i++) {
				System.out.println("2");
				pages.get(i + 1).push(pages.get(i).removeLast());
			}
			
			// If the last page has more than max page size
			if (pages.getLast().size() > pageSize) {
				System.out.println("3");
				LinkedList<E> page = new LinkedList<E>();
				page.add(pages.getLast().removeLast());
				pages.add(page);
			}
		}
	}
	
	public void push(E item) {
		pages.getFirst().push(item);
		trickleDown(0);
	}
	
	public void add(E item) {
		pages.getLast().add(item);
		trickleDown(pages.size() - 1);
	}
	
	public void clear() {
		pages.clear();
	}

	@Override
	public Iterator<E> iterator() {
		return new PaginatedListIterator();
	}
	
	private class PaginatedListIterator implements Iterator<E> {
		private int page = 0;
		private Iterator<E> iter = pages.get(0).iterator();

		@Override
		public boolean hasNext() {
			return iter.hasNext() || this.page + 1 < pages.size();
		}

		@Override
		public E next() {
			if (iter.hasNext()) {
				return iter.next();
			}
			else if (this.page + 1 < pages.size()) {
				iter = pages.get(++this.page).iterator();
				return iter.next();
			}
			return null;
		}
		
	}
}

package rltut;

public class Inventory {
	private Item[] items;

	public Inventory(int max) {
		items = new Item[max];
	}

	public Item get(int i) {
		return items[i];
	}

	public Item[] getItems() {
		return items;
	}

	public void add(Item item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				items[i] = item;
				break;
			}
		}
	}

	public void remove(Item item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == item) {
				items[i] = null;
				return;
			}
		}
	}

	public boolean isFull() {
		int size = 0;
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null)
				size++;
		}
		return size == items.length;
	}

}

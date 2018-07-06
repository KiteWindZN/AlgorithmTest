
public class myNodeList {

	class NodeList {
		int value;
		NodeList next = null;

		NodeList(int val) {
			this.value = val;
		}
	}

	public void appendNodeList(NodeList list, int val) {
		NodeList p = list;
		NodeList q = new NodeList(val);
		while (p.next != null) {
			p = p.next;
		}
		p.next = q;
	}

	public NodeList create_List(int[] data) {
		NodeList list = new NodeList(data[0]);
		for (int i = 1; i < data.length; i++) {
			appendNodeList(list, data[i]);
		}
		return list;
	}
	
	public int build_circle(NodeList list, int entry) {
		NodeList p = list;
		NodeList q = list;
		for (int i = 1; i < entry; i++) {
			if (q != null&&q.next!=null)
				q = q.next;
			else {
				System.out.println("entry error");
				return -1;
			}
		}

		while (p.next != null) {
			p = p.next;
		}
		p.next = q;
		return 1;
	}

	

	public NodeList find_encounter(NodeList list) {
		NodeList fast_p = list;
		NodeList slow_p = list;
		while (fast_p != null && fast_p.next != null) {
			fast_p = fast_p.next;
			fast_p = fast_p.next;
			slow_p = slow_p.next;

			if (fast_p == slow_p)
				break;
		}
		return fast_p;
	}

	public NodeList find_entry(NodeList list, NodeList encounter) {
		NodeList p = list;
		NodeList q = encounter;
		while (p != q) {
			p = p.next;
			q = q.next;
		}
		return p;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		myNodeList obj = new myNodeList();
		int[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
		NodeList list = obj.create_List(data);
		int res = obj.build_circle(list, 12);
		if (res == 1) {
			NodeList encounter = obj.find_encounter(list);
			NodeList entry = obj.find_entry(list, encounter);
			System.out.println(entry.value);
		}
	}

}

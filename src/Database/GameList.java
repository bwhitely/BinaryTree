package Database;

/**
 * Class to represent a single linked-list of Database.Game objects.
 * Created for Data Structures, SP2 2017
 * @author James Baumeister
 * @version 1.0
 */
public class GameList {

    public Game head;

	public GameList(Game head) {
	    this.head = head;
    }

    public String toString() {
	    if (size() == 0) {
	        return "Empty game list";
        }
	    else {
	        // string to build upon
	        String toReturn = "";
	        // ref node
	        Game ref = head;
	        // main loop
	        while (ref != null) {
	            toReturn = toReturn + ref + "\n";
	            ref = ref.getNext();
            }
	        // trim unneeded new lines
	        toReturn = toReturn.trim();
	        return toReturn;
        }
    }

	public Game getGame(String name) {
	    // GameNullArg
	    if (name == null) {
	        throw new IllegalArgumentException();
        }
	    // ref node
        Game ref = head;
	    // if name is for head of list
	    if (ref.getName().equals(name)) {
	        return ref;
        }
	    // for node after head
        while (ref != null) {
            if (ref.getName().equals(name)) {
                return ref;
            }
            ref = ref.getNext();
        }
        return null;
	}

	public void addGame (Game game) {
	    // GameNullArg
	    if (game == null) {
	        throw new IllegalArgumentException();
        }
	    // if list is null, set Game object to first element (head)
	    if (head == null) {
            head = game;
        }
	    // ref node
        Game ref = head;
	    // if game is duplicate object of head
	    if (ref == game) {
	        // do nothing
        } else {
            while (ref.getNext() != null) {
                // if duplicate object
                if (ref == game) {
                    ref = ref.getNext();
                }
                ref = ref.getNext();
            }
            // at end of list
            ref.setNext(game);
        }
    }

    public void removeGame (String name) {
	    // NullArg
	    if (name == null){
	        throw new IllegalArgumentException();
        }
	    // if empty
	    if (head == null) {
            System.out.println("list is empty, nothing to remove");
        }
	    // temp node
	    Game ref = head;
	    // if head needs to be removed
        if (head.getName().equals(name)) {
            // if head is only element
            if (head.getNext() == null) {
                head = null;
            }
            else {
                head = ref.getNext();
            }
        }
        // head is not target
        else {
            int pos = 0;
            // get position of element
            for (int i = 0; i < size(); i++) {
                if (ref.getName().equals(name)) {
                    pos = i;
                }
                ref = ref.getNext();
            }
            // reset temp node
            ref = head;
            // delete correct link
            for (int i = 0; i < size(); i++) {
                if (i == pos - 1) {
                    ref.setNext(ref.getNext().getNext());
                }
                ref = ref.getNext();
            }
            //head = ref;
        }
    }

    public void removeGame (Game game) {
	    // NullArg
	    if (game == null) {
	        throw new IllegalArgumentException();
        }
        // if empty
        if (head == null) {
            System.out.println("list is empty, nothing to remove");
        }
        Game ref = head;
        // if head needs to be removed
        if (head == game) {
            // if head is only element
            if (head.getNext() == null) {
                head = null;
            }
            else {
                head = ref.getNext();
            }
        }
        // head is not target
        else {
            int pos = 0;
            // get position of element
            for (int i = 0; i < size(); i++) {
                if (ref == game) {
                    pos = i;
                }
                ref = ref.getNext();
            }
            // reset temp node
            ref = head;
            // delete correct link
            for (int i = 0; i < size(); i++) {
                if (i == pos - 1) {
                    ref.setNext(ref.getNext().getNext());
                }
                ref = ref.getNext();
            }
            //head = ref;
        }

    }

    /**
     * custom size method
     */
    public int size() {
        int size = 0;
        Game ref = head;

        while (ref != null) {
            size++;
            ref = ref.getNext();
        }
        return size;
    }
}

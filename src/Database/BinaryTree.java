package Database;

import java.util.*;

/**
 * Uses a binary search tree to store a database of
 * PlayStation users. Nodes are ordered by user unique key (see the
 * User class for more detail).
 * Created for Data Structures, SP2 2017
 * @author James Baumeister
 * @version 1.0
 */
public class BinaryTree {
    public User root;

    /** PRIVATE METHODS FOR BINARY TREE TEST */
    // search for user target - inorder traversal
    private User search(User root, User target) {
        if (root == null || root.equals(target))
            return root;

        if (root.getKey() > target.getKey())
            return search(root.getLeft(), target);
        return search(root.getRight(), target);
    }

    // convert tree to stack
    private void treeToStack(User root, Stack<User> userStack) {
        // Null root
        if (root == null)
            return;

        // left to right traversal, push to stack
        treeToStack(root.getLeft(), userStack);
        userStack.push(root);
        treeToStack(root.getRight(), userStack);
    }

    // counts platinum trophies
    private int countPlats(User user){
        // get trophies
        ArrayList<Trophy> s = user.getTrophies();
        int count = 0;
        // for each - count PLAT trophies
        for (Trophy trophy : s) {
            if (trophy.getRank() == Trophy.Rank.PLATINUM)
                count++;
        }
        return count;
    }

    // counts gold trophies
    private int countGolds(User user){
        // get trophies
        ArrayList<Trophy> s = user.getTrophies();
        int count = 0;
        // for each - count GOLD trophies
        for (Trophy trophy : s) {
            if (trophy.getRank() == Trophy.Rank.GOLD)
                count++;
        }
        return count;
    }

    // based on inOrderPredecessor algorithm from textbook page 290
    private User InOrderPredecessor(User user, User pred, double key) {
        // null root
        if (user == null)
            return pred;

        // if keys are equal, the predecessor is max in left tree
        if (key == user.getKey()) {
            if (user.getLeft() != null) {
                // get highest key value node and return it
                User ref = user.getLeft();
                while (ref.getRight() != null) {
                    ref = ref.getRight();
                }
                return ref;
            }
        }
        // look at right tree
        else if (key > user.getKey())
            return InOrderPredecessor(user.getRight(), pred, key);

        // look at left tree
        else {
            pred = user;
            return InOrderPredecessor(user.getLeft(), pred, key);
        }
        return pred;
    }

	/**
	 * Making new friends is great. This method should add your new
	 * bestie to your database (tree). Remember that they should be
	 * added according to their key.
	 * @param friend The friend to be added
	 * @return true if  successfully added, false for all error cases
	 * @throws IllegalArgumentException if friend is null
	 */

	public boolean beFriend(User friend) throws IllegalArgumentException {
	    // NullArg
	    if (friend == null)
	        throw new IllegalArgumentException();

        // Null root
        if (root == null) {
            root = friend;
            return true;
        } else {
            // reference nodes
            User parent = null;
            User ref = root;
            // iterative loop
            while (ref != null) {
                parent = ref;
                // duplicate key, return false
                if (ref.getKey() == friend.getKey()) {
                    return false;
                }
                // argument key is greater than or equal to root - RIGHT side of tree
                if (ref.getKey() <= friend.getKey()){
                    ref = ref.getRight();
                // argument is less than or equal to root - LEFT side of tree
                } else {
                    ref = ref.getLeft();
                }
            }
            // check parent key, insert in left or right child node depending on key value
            if (parent.getKey() <= friend.getKey()){
                parent.setRight(friend);
            } else {
                parent.setLeft(friend);
            }
            return true;
        }
	}

	/**
	 * Sometimes friendships don't work out. In those cases it's best
	 * to remove that "friend" altogether. This method should remove
	 * all trace of that "friend" in the database (tree).
	 * @param friend the "friend" to remove
	 * @return true if successfully removed, false for all error cases
	 * @throws IllegalArgumentException if "friend" is null
	 */
	public boolean deFriend(User friend) throws IllegalArgumentException {
        // NullArg
        if (friend == null)
            throw new IllegalArgumentException();

        // Null root
        if (root == null)
            return false;

        // bool for left child detection
        boolean left = true;
        User ref = root;
        User parent = root;

        // root is target
        if (root == friend){
            if (root.getLeft() == null && root.getRight() == null)
                root = null;
        }
        // search for target in tree, return false if not found
        if (search(root, friend) == null)
            return false;

        // find node to delete
        while (ref.getKey() != friend.getKey()){
            parent = ref;

            if (friend.getKey() < ref.getKey()){
                left = true;
                ref = ref.getLeft();
            } else {
                left = false;
                ref = ref.getRight();
            }
        }
        // CASE 1 - NO CHILDREN
        if (ref.getLeft() == null && ref.getRight() == null) {
            // if root
            if (ref == root)
                root = null;
            // left child
            else if (left)
                parent.setLeft(null);
            // right child
            else
                parent.setRight(null);
        }
        // CASE 2 - NO RIGHT CHILD
        else if (ref.getRight() == null && ref.getLeft() != null) {
            // is root
            if (ref == root)
                root = ref.getLeft();
            // left child
            else if (left)
                parent.setLeft(ref.getLeft());
            // right child
            else
                parent.setRight(ref.getLeft());
        }
        // CASE 2 - NO LEFT CHILD
        else if (ref.getLeft() == null && ref.getRight() != null){
            // is root
            if (ref == root)
                root = ref.getRight();
            // left child
            else if (left)
                parent.setLeft(ref.getRight());
            // right child
            else
                parent.setRight(ref.getRight());
        }
        // CASE 3 - TWO CHILDREN
        else {
            // if root is target
            if (friend == root){
                // get highest value on left sub tree and set to root
                User ref2 = root.getLeft();
                while (ref2.getRight() != null) {
                    ref2 = ref2.getRight();
                }
                root = ref2;
            }
            // get predecessor
            User nodeToInsert = InOrderPredecessor(ref, null, ref.getKey());
            // if ref is root, replace root with newNode
            if (ref == root)
                root = nodeToInsert;
            // if we deleted a left child
            else if (left)
                parent.setLeft(nodeToInsert);
            // if we deleted a right child
            else
                parent.setRight(nodeToInsert);

            // set the new node's left/right pointers
            nodeToInsert.setLeft(ref.getLeft());
            nodeToInsert.setRight(ref.getRight());
        }
        // return true by default, edge cases would return false earlier
		return true;
	}

	/**
	 * In your quest to be the very best you need to know how many
	 * of your friends are ranked higher than you. This method should
	 * return the number of higher ranked users that the provided reference
	 * user, or zero if there are none (woot!).
	 * @param reference The starting point in the search
	 * @return Number of higher ranked users or -1 if user not found
	 * @throws IllegalArgumentException if reference is null
	 */

	public int countBetterPlayers(User reference) throws IllegalArgumentException {
	    // NullArg
        /* this stack code is reused for a few methods, i found it easier to just reuse it instead
          of creating methods to do so (apart from treeToStack method) so i left the duplicate code in there */
	    if (reference == null)
	        throw new IllegalArgumentException();
	    // new Stack
        Stack<User> stack = new Stack<User>();
        // player not found
        if (search(root, reference) == null)
            return -1;

	    int count = 0;
	    User ref = root;

	    // traverse tree
	    while (ref != null || stack.size() > 0) {
	        while (ref != null) {
	            stack.push(ref);
	            ref = ref.getLeft();
            }
	        // check if higher level
	        if (stack.peek().getLevel() > reference.getLevel()) {
	            count++;
                ref = stack.pop();
            } else {
                ref = stack.pop();
            }
	        ref = ref.getRight();
        }
	    return count;
	}

	/**
	 * Bragging rights are well earned, but it's good to be sure that you're actually
	 * better than those over whom you're lording your achievements. This method
	 * should find all those friends who have a lower level than you, or zero if
	 * there are none (you suck).
	 * @param reference The starting point in the search
	 * @return Number of lower ranked users
	 * @throws IllegalArgumentException if reference is null
	 */
	public int countWorsePlayers(User reference) throws IllegalArgumentException {
        // NullArg
        if (reference == null)
            throw new IllegalArgumentException();

        // Stack
        Stack<User> stack = new Stack<User>();
        // player not found
        if (search(root, reference) == null)
            return -1;

        int count = 0;
        User ref = root;

        // traverse tree
        while (ref != null || stack.size() > 0) {
            while (ref != null) {
                stack.push(ref);
                ref = ref.getLeft();
            }
            // check if lower level
            if (stack.peek().getLevel() < reference.getLevel()) {
                count++;
                ref = stack.pop();
            } else {
                ref = stack.pop();
            }
            ref = ref.getRight();
        }
        return count;
	}

	/**
	 * The best player may not necessarily be measured by who has the highest level.
	 * Platinum trophies are the holy grail, so it would be good to know who has the
	 * most. This method should return the user with the highest number of platinum trophies.
	 * @return the user with the most platinum trophies, or null if there are none
	 */
	public User mostPlatinums() {
	    User highest = root;
	    Stack<User> stack = new Stack<>();
	    treeToStack(root, stack);

	    // checks all users in stack
        while (stack.size() > 0) {
            User ref = stack.pop();
            // compare platinums
            if (countPlats(ref) > countPlats(highest)){
                highest = ref;
            }
            // if same amount of platinums, compare golds
            if (countPlats(ref) == countPlats(highest)){
                // if new User has more golds, they are the new highest, otherwise no change
                if (countGolds(ref) > countGolds(highest)){
                    highest = ref;
                }
            }

        }
	    return highest;
	}

	/**
	 * You or one of your friends bought a new game! This method should add it to their
	 * GameList.
	 * @param username The user who has bought the game
	 * @param game The game to be added
	 */
	public void addGame(String username, Game game) throws IllegalArgumentException {
	    // NullArg
	    if (username == null || game == null)
	        throw new IllegalArgumentException();

	    // new stack & convert tree to stack
	    Stack<User> stack = new Stack<>();
	    treeToStack(root, stack);
	    // user variables
	    User user = null;
	    User ref;

	    // iterate over stack
	    while (stack.size() > 0){
	        ref = stack.pop();
	        // found User object with matching username
	        if (ref.getUsername().equals(username))
	            user = ref;
        }
	    // search tree to find user
        user = search(root, user);
	    // add game to users games
        user.getGames().addGame(game);
	}

	/**
	 * You or one of your friends achieved a new trophy! This method should add it to
	 * their trophies.
	 * @param username The user who has earned a new trophy
	 * @param trophy The trophy to be added   
	 */
	public void addTrophy(String username, Trophy trophy) throws IllegalArgumentException {
	    // NullArg
        if (username == null || trophy == null)
            throw new IllegalArgumentException();

        // new stack & convert tree to stack
        Stack<User> stack = new Stack<>();
        treeToStack(root, stack);
        // user variables
        User user = null;
        User ref;

        // iterate over stack
        while (stack.size() > 0){
            ref = stack.pop();
            // found User object with matching username
            if (ref.getUsername() == username)
                user = ref;
        }
        // search tree to find user
        user = search(root, user);
        // add trophy to user trophies
        user.getTrophies().add(trophy);
	}

	/**
	 * You or one of your friends has gained one level! This is great news, except that
	 * it may have ruined your tree structure! A node move may be in order.
	 * @param username The user whose level has increased
	 */
	public void levelUp(String username) throws IllegalArgumentException {
	    if (username == null)
	        throw new IllegalArgumentException();

        // new stack & convert tree to stack
        Stack<User> stack = new Stack<>();
        treeToStack(root, stack);
        // user variables
        User user = null;
        User ref;

        // iterate over stack
        while (stack.size() > 0){
            ref = stack.pop();
            // found User object with matching username
            if (ref.getUsername().equals(username))
                user = ref;
        }
        // in order search of tree to find user
        user = search(root, user);
        // add 1 level
        int newLevel = user.getLevel() + 1;
        // create new user Object
        User newUser = new User(username, user.getDob(), newLevel);
        // defriend old User object
        deFriend(user);
        // add new User object
        beFriend(newUser);
	}

	/** AVL
     *
     * CUSTOM
     *
     * METHODS
     *
     * */

	// get max of two ints
	private int max (int x, int y) {
        return Math.max(x, y);
    }

    // get height of tree
    private int treeHeight (User user) {
        if (user == null)
            return -1;

        // get highest path from root to leaf
        return max(treeHeight(user.getLeft()), treeHeight(user.getRight())) +1;
    }

    // get balance of tree
    private int balance (User user) {
	    if (user == null)
            return 0;
        else
            // balance equals height of right tree minus height of left
            return treeHeight(user.getRight()) - treeHeight(user.getLeft());

    }

    // right rotation algorithm
    private User rightRotate (User user) {
        // reference variables
        User ref = user.getLeft();
        User ref2 = ref.getRight();

        // check if user has parent
        if (user.getParent() != null)
            // set parent's left
            user.getParent().setLeft(ref);

        // parents
        ref.setParent(user.getParent());
        user.setParent(ref);

        // rotate right
        ref.setRight(user);
        user.setLeft(ref2);

        // double check if ref needs to be root
        if (ref.getParent() == null)
            root = ref;

        return ref;
    }

    // left rotation algorithm
    private User leftRotate (User user) {
        // reference variables
        User ref = user.getRight();
        User ref2 = ref.getLeft();

        // check if user has a parent
        if (user.getParent() != null)
            // set parent's right
            user.getParent().setRight(ref);

        // parents
        ref.setParent(user.getParent());
        user.setParent(ref);

        // rotate left
        ref.setLeft(user);
        user.setRight(ref2);

        // double check if ref needs to be root
        if (ref.getParent() == null)
            root = ref;

        return ref;
    }

	/**
	 * As your friends list grows, adding with regular binary tree rules will
	 * result in an unbalanced and inefficient tree. One approach to fix this
	 * is to implement an add method that uses AVL balancing. This method should
	 * work in the same way as beFriend, but maintain a balanced tree according to
	 * AVL rules.
	 * @param friend The friend to be added
	 * @return true if  successfully added, false for all error cases
	 * @throws IllegalArgumentException if friend is null
	 */
	public boolean addAVL(User friend) throws IllegalArgumentException {
	    // NullArg check
	    if (friend == null)
	        throw new IllegalArgumentException();
        // duplicate check
        if (search(root, friend) == friend)
            return false;
	    // null root check
        if (root == null){
            root = friend;
            return true;
        }
        else {
            User parent;
            User ref = root;
            // ArrayList to store parent nodes
            ArrayList<User> listOfParents = new ArrayList<User>();

            while (ref != null) {
                parent = ref;
                listOfParents.add(ref);

                // left tree
                if (friend.getKey() < ref.getKey()) {
                    ref = ref.getLeft();

                    if (ref == null) {
                        listOfParents.add(friend);
                        parent.setLeft(friend);
                        friend.setParent(parent);
                    }
                }
                // right tree
                else if (friend.getKey() >= ref.getKey()){
                    ref = ref.getRight();

                    if (ref == null){
                        parent.setRight(friend);
                        friend.setParent(parent);
                    }
                }
            }
            // traverse arraylist in reverse
            for (int i = listOfParents.size(); i > 0; i--) {
                // get parent
                User user = listOfParents.get(listOfParents.size()-1);
                // remove parent
                listOfParents.remove(listOfParents.size()-1);

                // check balance and rotate if required to keep AVL properties met
                if (balance(user) < -1) {
                    // perform right rotation
                    if (treeHeight(user.getLeft().getLeft()) > treeHeight(user.getLeft().getRight())){
                        rightRotate(user);
                    }
                    // perform left-right rotate
                    else {
                        user.setLeft(leftRotate(user.getLeft()));
                        rightRotate(user);
                    }
                } else if (balance(user) > 1) {
                    // perform left rotate
                    if (treeHeight(user.getRight().getRight()) > treeHeight(user.getRight().getLeft())){
                        leftRotate(user);
                    }
                    // perform right-left rotate
                    else {
                        user.setRight(rightRotate(user.getRight()));
                        leftRotate(user);
                    }
                }
            }
        }
        // return true by default because we would have returned false earlier if edge cases appear
		return true;
	}

	/**
	 * A nice, neat print-out of your friends would look great in the secret scrap-book
	 * that you keep hidden underneath your pillow. This method should print out the
	 * details of each user, traversing the tree in order.
	 * @return A string version of the tree
	 */
	public String toString() {
	    // string to build upon
	    String stringToBuild = "";
        // new stack & convert tree to stack
        Stack<User> stack = new Stack<>();
        treeToStack(root, stack);
        // reverse stack to correct ordering
        Collections.reverse(stack);
        // user variables
        User ref;

        // iterate over stack
        while (stack.size() > 0){
            ref = stack.pop();
            stringToBuild = stringToBuild + ref.toString() + "\n";
        }
		return stringToBuild.trim();
	}
}

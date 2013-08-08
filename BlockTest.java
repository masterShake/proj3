import junit.framework.TestCase;


public class BlockTest extends TestCase {

  public void testEquals(){
		Block a = new Block(1,2,3,4);
		Block b = new Block(1,2, 3, 4);
		assertTrue(a.equals(b));
	}
}

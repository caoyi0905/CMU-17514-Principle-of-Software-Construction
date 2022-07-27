package santorini;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import token.Worker;


public class TokenTest {

    Worker worker = new Worker();

    /**
     * Test of setting the position for the workers.
     */
    @Test
    public void testworkersetposition(){
        worker.setPosition(1,2);
        assertEquals(worker.getX(),1);
        assertEquals(worker.getY(),2);
        assertFalse(worker.setPosition(6,7));
        assertFalse(worker.setPosition(10,1));

    }

}

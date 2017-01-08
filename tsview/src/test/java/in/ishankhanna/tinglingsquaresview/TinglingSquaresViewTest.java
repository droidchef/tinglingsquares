package in.ishankhanna.tinglingsquaresview;

import org.junit.Test;

import in.ishankhanna.tinglingsquares.TinglingSquaresView;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TinglingSquaresViewTest {

    @Test
    public void testStartDelayGeneratorForLeftToRightAnimations() {

        long result = 0L;

        result = TinglingSquaresView.getStartDelayForColumn(4, true);

        assertEquals(0, result);

        result = TinglingSquaresView.getStartDelayForColumn(3, true);

        assertEquals(150, result);
    }
}
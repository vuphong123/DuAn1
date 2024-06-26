package udpm.fpt.swing.Chart.blankchart;

import java.awt.Graphics2D;

/**
 *
 * @author NONG HOANG VU
 */
public abstract class BlankPlotChatRender {
    public abstract String getLabelText(int index);

    public abstract void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index);
}

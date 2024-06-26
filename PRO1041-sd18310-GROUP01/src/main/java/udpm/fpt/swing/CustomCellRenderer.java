package udpm.fpt.swing;

import udpm.fpt.swing.table.HoverIndex;
import udpm.fpt.swing.table.TableHeaderCustomCellRender;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomCellRenderer extends DefaultTableCellRenderer {
    private final JTable table;
    private int hoveredRow = -1;

    public CustomCellRenderer(JTable table) {
        this.table = table;
        ListSelectionModel rowSelectionModel = table.getSelectionModel();
        rowSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSelectionModel.addListSelectionListener(new RowSelectionListener());
        table.addMouseMotionListener(new HoverMouseListener());
        table.addMouseListener(new HoverExitMouseListener());
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        table.setRowHeight(40);
        int quantityColumnIndex = 4;
        int quantity = (int) table.getValueAt(row, quantityColumnIndex);

        if (quantity < 1) {
            component.setBackground(isSelected ? new Color(123, 207, 255) : new Color(255, 110, 110));
        } else if (quantity < 10) {
            component.setBackground(isSelected ? new Color(123, 207, 255) : new Color(238, 255, 70));
        } else {
            component.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        }

        if (table.getRowHeight(row) < component.getPreferredSize().height) {
            table.setRowHeight(row, component.getPreferredSize().height);
        }

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setDefaultRenderer(new TableHeaderCustomCellRender(table));
        table.setGridColor(new Color(220, 220, 220));
        table.setForeground(new Color(51, 51, 51));
        table.setSelectionForeground(new Color(51, 51, 51));
        table.getTableHeader().setBackground(new Color(250, 250, 250));

        if (row == hoveredRow) {
            Color originalColor = isSelected ? new Color(123, 207, 255) : new Color(255, 255, 255, 255);
            float[] hsb = Color.RGBtoHSB(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), null);
            Color fadedColor = Color.getHSBColor(hsb[0], hsb[1], hsb[2] * 0.9f);
            component.setBackground(fadedColor);
        }

        return component;
    }

    private class RowSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                ListSelectionModel rowSelectionModel = (ListSelectionModel) e.getSource();
                if (!rowSelectionModel.isSelectionEmpty()) {
                    int selectedRow = rowSelectionModel.getMinSelectionIndex();
                    table.setRowSelectionInterval(selectedRow, selectedRow);
                }
            }
        }
    }

    private class HoverMouseListener extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            int row = table.rowAtPoint(e.getPoint());
            if (row != hoveredRow) {
                hoveredRow = row;
                table.repaint();
            }
        }
    }
    private class HoverExitMouseListener extends MouseAdapter {
        @Override
        public void mouseExited(MouseEvent e) {
            Point point = e.getPoint();
            if (!table.contains(point)) {
                hoveredRow = -1;
                table.repaint();
            }
        }
    }
}

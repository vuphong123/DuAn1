/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.serviceDelivery;

/**
 *
 * @author PHONG PC
 */
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomCellRenderer extends DefaultTableCellRenderer {
    public CustomCellRenderer(JTable table){
        tableMouse(table);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        // Chỉnh màu chữ cho ô cụ thể
        if (column == 3) { // Chỉnh màu chữ cho cột có index là 1 (Age)
            String status = value.toString();
            if (status.equals("Giao thành công")) {
                cellComponent.setForeground(Color.GREEN); // Màu đỏ cho các ô có giá trị Age > 30
            }
            if (status.equals("Đang giao hàng")) {
                cellComponent.setForeground(Color.BLUE); // Màu xanh cho các ô có giá trị Age <= 30
            }
            if (status.equals("Chờ giao hàng")) {
                cellComponent.setForeground(Color.ORANGE);
            }
            if (status.equals("Huỷ giao hàng")) {
                cellComponent.setForeground(Color.RED);
            }
            if (status.equals("Đang đóng gói")) {
                cellComponent.setForeground(Color.LIGHT_GRAY);
            }
        }
        return cellComponent;
    }
    public JTable tableMouse(JTable table){
        // Bắt sự kiện di chuột để thêm hiệu ứng hover
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0 && row < table.getRowCount()) {
                    // Thiết lập màu nền khi di chuột vào dòng
                    table.setSelectionBackground(Color.LIGHT_GRAY);
                    table.setSelectionForeground(Color.BLACK);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Thiết lập lại màu nền khi di chuột ra khỏi dòng
                table.setSelectionBackground(table.getBackground());
                table.setSelectionForeground(table.getForeground());
            }
        });
        return table;
    }
        
  
}

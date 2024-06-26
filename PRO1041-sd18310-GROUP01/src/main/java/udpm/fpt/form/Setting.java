package udpm.fpt.form;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import udpm.fpt.main.Main;
import udpm.fpt.model.User;

/**
 *
 * @author NONG HOANG VU
 */
public class Setting extends javax.swing.JPanel {

    private final Main main;

    public Setting(User user, Main main) {
        initComponents();
        this.main = main;
        init();
    }

    private void init() {
        setCombo();
        setSelected();
    }

    private void setSelected() {
        if (readFile() != null) {
            cbbSize.setSelectedItem(readFile());
        }
    }

    private void setCombo() {
        String[] object = {"Chooser", "Default", "FullSize"};
        for (String s : object) {
            cbbSize.addItem(s);
        }
    }

    public String readFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Setting.ser"))) {
            return (String) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    private void remember(String string) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Setting.ser"))) {
            oos.writeObject(string);
        } catch (IOException e) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbbSize = new udpm.fpt.swing.Combobox();

        setBackground(new java.awt.Color(255, 255, 255));

        cbbSize.setLabeText("Size");
        cbbSize.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbSizeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1229, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbbSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(802, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbbSizeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbSizeItemStateChanged
        switch (cbbSize.getSelectedIndex()) {
            case 0 -> remember(readFile());
            case 1 -> {
                this.main.settingSize(Main.SizeOption.DEFAULT_SIZE);
                remember("Default");
            }
            case 2 -> {
                this.main.settingSize(Main.SizeOption.FULL_SIZE);
                remember("FullSize");
            }
            default -> {
            }
        }
    }//GEN-LAST:event_cbbSizeItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private udpm.fpt.swing.Combobox cbbSize;
    // End of variables declaration//GEN-END:variables
}

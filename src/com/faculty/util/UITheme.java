package com.faculty.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

/**
 * UI Theme utility - provides consistent colors, fonts, and component styling.
 */
public class UITheme {

    // Color Palette
    public static final Color PRIMARY      = new Color(99, 102, 241);   // Violet/Indigo accent (#6366F1)
    public static final Color PRIMARY_DARK = new Color(79, 70, 229);    // Indigo-600 (#4F46E5)
    public static final Color SECONDARY    = new Color(139, 92, 246);   // Violet-500 (#8B5CF6)
    public static final Color SUCCESS      = new Color(16, 185, 129);   // Emerald-500
    public static final Color DANGER       = new Color(244, 63, 94);    // Rose-500
    public static final Color WARNING      = new Color(245, 158, 11);   // Amber-500
    public static final Color BG_DARK      = new Color(248, 250, 252);  // Slate-50 (Very light gray bg)
    public static final Color BG_CARD      = Color.WHITE;               // Card background
    public static final Color BG_SIDEBAR   = new Color(255, 255, 255);  // Sidebar background (pure white)
    public static final Color BG_INPUT     = new Color(248, 250, 252);  // Input background (Slate-50)
    public static final Color TEXT_PRIMARY = new Color(15, 23, 42);     // Slate-900 (Dark text)
    public static final Color TEXT_MUTED   = new Color(100, 116, 139);  // Slate-500 (Muted text)
    public static final Color BORDER_COLOR = new Color(226, 232, 240);  // Slate-200
    public static final Color TABLE_HEADER = new Color(241, 245, 249);  // Slate-100
    public static final Color TABLE_ROW_EVEN = Color.WHITE;
    public static final Color TABLE_ROW_ODD  = new Color(248, 250, 252); // Slate-50
    public static final Color TABLE_SELECT  = new Color(99, 102, 241, 30);


    // Fonts
    public static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD,  22);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD,  16);
    public static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BUTTON  = new Font("Segoe UI", Font.BOLD,  13);
    public static final Font FONT_LABEL   = new Font("Segoe UI", Font.BOLD,  12);

    /**
     * Style a primary action button.
     */
    public static JButton primaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(PRIMARY_DARK); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(PRIMARY); }
        });
        return btn;
    }

    public static JButton dangerButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(DANGER);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        Color hov = new Color(185, 28, 28);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(hov); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(DANGER); }
        });
        return btn;
    }

    public static JButton successButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(SUCCESS);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        Color hov = new Color(16, 124, 58);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(hov); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(SUCCESS); }
        });
        return btn;
    }

    /**
     * Style a dark-themed input field.
     */
    public static JTextField styledField() {
        JTextField field = new JTextField();
        field.setFont(FONT_BODY);
        field.setBackground(BG_INPUT);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }

    public static JPasswordField styledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(FONT_BODY);
        field.setBackground(BG_INPUT);
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }

    public static JTextArea styledTextArea() {
        JTextArea area = new JTextArea();
        area.setFont(FONT_BODY);
        area.setBackground(BG_INPUT);
        area.setForeground(TEXT_PRIMARY);
        area.setCaretColor(TEXT_PRIMARY);
        area.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    public static <T> JComboBox<T> styledCombo() {
        JComboBox<T> combo = new JComboBox<>();
        combo.setFont(FONT_BODY);
        combo.setBackground(BG_INPUT);
        combo.setForeground(TEXT_PRIMARY);
        combo.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean selected, boolean hasFocus) {
                super.getListCellRendererComponent(list, value, index, selected, hasFocus);
                setBackground(selected ? PRIMARY : BG_INPUT);
                setForeground(TEXT_PRIMARY);
                setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                return this;
            }
        });
        return combo;
    }


    public static JLabel label(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_MUTED);
        return lbl;
    }

    /**
     * Apply dark theme to a JTable.
     */
    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setForeground(TEXT_PRIMARY);
        table.setBackground(TABLE_ROW_EVEN);
        table.setSelectionBackground(PRIMARY);
        table.setSelectionForeground(Color.WHITE);
        table.setRowHeight(32);
        table.setGridColor(BORDER_COLOR);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 1));

        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER);
        header.setForeground(TEXT_PRIMARY);
        header.setFont(FONT_LABEL);
        header.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        header.setReorderingAllowed(false);

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean selected,
                    boolean focused, int row, int col) {
                super.getTableCellRendererComponent(t, value, selected, focused, row, col);
                if (selected) {
                    setBackground(PRIMARY);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(row % 2 == 0 ? TABLE_ROW_EVEN : TABLE_ROW_ODD);
                    setForeground(TEXT_PRIMARY);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                return this;
            }
        });
    }

    public static JScrollPane styledScrollPane(Component view) {
        JScrollPane sp = new JScrollPane(view);
        sp.getViewport().setBackground(BG_CARD);
        sp.setBackground(BG_CARD);
        sp.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        sp.getVerticalScrollBar().setBackground(BG_DARK);
        return sp;
    }

    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)
        );
    }

    public static void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}
        UIManager.put("OptionPane.background", BG_CARD);
        UIManager.put("Panel.background", BG_CARD);
        UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
    }
}

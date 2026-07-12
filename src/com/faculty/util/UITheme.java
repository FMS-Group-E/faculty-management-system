package com.faculty.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Enumeration;

/**
 * UI Theme utility - Blue color scheme matching screenshot design.
 * Sidebar: solid blue | Content: white | Buttons: blue/gray | Tables: blue header
 */
public class UITheme {

    // ── Core Blue Palette ──────────────────────────────────────────────────────
    public static final Color PRIMARY       = new Color(37,  99,  235);   // Blue-600  #2563EB
    public static final Color PRIMARY_DARK  = new Color(29,  78,  216);   // Blue-700  #1D4ED8
    public static final Color PRIMARY_LIGHT = new Color(219, 234, 254);   // Blue-100  #DBEAFE
    public static final Color SECONDARY     = new Color(96,  165, 250);   // Blue-300  #60A5FA

    // ── Sidebar (solid blue background) ───────────────────────────────────────
    public static final Color BG_SIDEBAR        = new Color(37,  99,  235);   // same as PRIMARY
    public static final Color SIDEBAR_TEXT       = Color.WHITE;
    public static final Color SIDEBAR_TEXT_MUTED = new Color(191, 219, 254);  // Blue-200
    public static final Color SIDEBAR_HOVER      = new Color(29,  78,  216);  // Blue-700
    public static final Color SIDEBAR_ACTIVE     = new Color(30,  64,  175);  // Blue-800
    public static final Color SIDEBAR_ICON       = Color.WHITE;
    public static final Color SIDEBAR_SEPARATOR  = new Color(59,  130, 246);  // Blue-500

    // ── Content Area ──────────────────────────────────────────────────────────
    public static final Color BG_DARK   = new Color(243, 244, 246);  // Gray-100  light page bg
    public static final Color BG_CARD   = Color.WHITE;
    public static final Color BG_INPUT  = Color.WHITE;

    // ── Text ──────────────────────────────────────────────────────────────────
    public static final Color TEXT_PRIMARY = new Color(17,  24,  39);   // Gray-900
    public static final Color TEXT_MUTED   = new Color(107, 114, 128);  // Gray-500

    // ── Borders ───────────────────────────────────────────────────────────────
    public static final Color BORDER_COLOR = new Color(209, 213, 219);  // Gray-300

    // ── Table ─────────────────────────────────────────────────────────────────
    public static final Color TABLE_HEADER    = new Color(37,  99,  235);   // Blue header row (matches screenshot)
    public static final Color TABLE_HEADER_FG = Color.WHITE;
    public static final Color TABLE_ROW_EVEN  = Color.WHITE;
    public static final Color TABLE_ROW_ODD   = new Color(249, 250, 251);  // Gray-50
    public static final Color TABLE_SELECT    = new Color(219, 234, 254);  // Blue-100

    // ── Status Colors ─────────────────────────────────────────────────────────
    public static final Color SUCCESS = new Color(22,  163, 74);   // Green-600
    public static final Color DANGER  = new Color(220, 38,  38);   // Red-600
    public static final Color WARNING = new Color(217, 119, 6);    // Amber-600

    // ── Fonts ─────────────────────────────────────────────────────────────────
    public static final Font FONT_TITLE   = new Font("Helvetica", Font.BOLD,  22);
    public static final Font FONT_HEADING = new Font("Helvetica", Font.BOLD,  16);
    public static final Font FONT_BODY    = new Font("Helvetica", Font.PLAIN, 13);
    public static final Font FONT_SMALL   = new Font("Helvetica", Font.PLAIN, 12);
    public static final Font FONT_BUTTON  = new Font("Helvetica", Font.BOLD,  13);
    public static final Font FONT_LABEL   = new Font("Helvetica", Font.BOLD,  12);

    // ══════════════════════════════════════════════════════════════════════════
    // Button Factories
    // ══════════════════════════════════════════════════════════════════════════

    /** Blue primary button (Add new / Save) */
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

    /** Gray secondary button (Edit / Delete outline) */
    public static JButton secondaryButton(String text) {
        Color base = new Color(156, 163, 175);   // Gray-400
        Color dark = new Color(107, 114, 128);   // Gray-500
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(base);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(dark); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(base); }
        });
        return btn;
    }

    /** Red danger button */
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

    /** Green success button */
    public static JButton successButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setBackground(SUCCESS);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        Color hov = new Color(21, 128, 61);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(hov); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btn.setBackground(SUCCESS); }
        });
        return btn;
    }

    // ══════════════════════════════════════════════════════════════════════════
    // Input Factories
    // ══════════════════════════════════════════════════════════════════════════

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
                setForeground(selected ? Color.WHITE : TEXT_PRIMARY);
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

    // ══════════════════════════════════════════════════════════════════════════
    // Table Styling  (blue header row matching screenshot)
    // ══════════════════════════════════════════════════════════════════════════

    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setForeground(TEXT_PRIMARY);
        table.setBackground(TABLE_ROW_EVEN);
        table.setSelectionBackground(PRIMARY_LIGHT);
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setRowHeight(34);
        table.setGridColor(BORDER_COLOR);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER);
        header.setForeground(TABLE_HEADER_FG);
        header.setFont(FONT_LABEL);
        header.setBorder(BorderFactory.createLineBorder(PRIMARY_DARK));
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));

        // Alternating rows + border cells
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean selected,
                    boolean focused, int row, int col) {
                super.getTableCellRendererComponent(t, value, selected, focused, row, col);
                if (selected) {
                    setBackground(PRIMARY_LIGHT);
                    setForeground(TEXT_PRIMARY);
                } else {
                    setBackground(row % 2 == 0 ? TABLE_ROW_EVEN : TABLE_ROW_ODD);
                    setForeground(TEXT_PRIMARY);
                }
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 1, BORDER_COLOR),
                    BorderFactory.createEmptyBorder(0, 8, 0, 8)
                ));
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
        for (Enumeration<?> e = UIManager.getDefaults().keys(); e.hasMoreElements();) {
            Object key = e.nextElement();
            Object val = UIManager.get(key);
            if (val instanceof Font) {
                Font f = (Font) val;
                UIManager.put(key, new Font("Helvetica", f.getStyle(), f.getSize()));
            }
        }
    }
}

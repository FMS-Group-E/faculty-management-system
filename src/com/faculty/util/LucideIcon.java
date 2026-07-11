package com.faculty.util;

import javax.swing.Icon;
import java.awt.*;

/**
 * Portable, high-resolution vector icon renderer replicating Lucide React Icons
 * using raw Graphics2D vector operations. Zero dependencies.
 */
public class LucideIcon implements Icon {

    private final String name;
    private final int size;
    private final Color color;

    public LucideIcon(String name, int size, Color color) {
        this.name = name;
        this.size = size;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        
        // Quality rendering hints
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        // Set stroke width proportional to size (aim for ~1.8px stroke on 24px icon)
        float strokeWidth = (size / 24.0f) * 2.0f;
        if (strokeWidth < 1.0f) strokeWidth = 1.0f;
        g2.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        g2.setColor(color != null ? color : c.getForeground());
        
        // Translate and scale to target size (base coordinates are design-time 24x24)
        g2.translate(x, y);
        double scale = size / 24.0;
        g2.scale(scale, scale);

        switch (name.toLowerCase()) {
            case "dashboard":
                // 4 rounded squares representing dashboard layout
                g2.drawRoundRect(3, 3, 7, 7, 2, 2);
                g2.drawRoundRect(14, 3, 7, 10, 2, 2);
                g2.drawRoundRect(3, 14, 7, 7, 2, 2);
                g2.drawRoundRect(14, 17, 7, 4, 2, 2);
                break;
                
            case "student":
                // Graduation cap
                g2.drawPolygon(new int[]{12, 22, 12, 2}, new int[]{3, 8, 13, 8}, 4);
                g2.drawPolyline(new int[]{6, 6, 12, 18, 18}, new int[]{10, 15, 19, 15, 10}, 5);
                g2.drawLine(20, 9, 20, 15);
                g2.drawOval(19, 15, 2, 2);
                break;
                
            case "lecturer":
                // 2 users representing directory/staff
                // Primary User (front)
                g2.drawOval(6, 5, 6, 6);
                g2.drawArc(2, 15, 14, 10, 0, 180);
                // Secondary User (back)
                g2.drawOval(13, 6, 5, 5);
                g2.drawArc(10, 14, 11, 8, 320, 150);
                break;
                
            case "course":
                // Opened book
                g2.drawArc(3, 6, 9, 3, 0, 180);
                g2.drawArc(12, 6, 9, 3, 0, 180);
                g2.drawArc(3, 17, 9, 3, 0, 180);
                g2.drawArc(12, 17, 9, 3, 0, 180);
                g2.drawLine(3, 6, 3, 17);
                g2.drawLine(12, 6, 12, 20);
                g2.drawLine(21, 6, 21, 17);
                break;
                
            case "department":
                // Landmark building representing departments
                g2.drawLine(2, 21, 22, 21); // bottom foundation
                g2.drawPolygon(new int[]{12, 2, 22}, new int[]{2, 9, 9}, 3); // roof triangle
                g2.drawLine(3, 9, 21, 9);
                g2.drawLine(4, 12, 20, 12);
                // Columns
                g2.drawLine(6, 12, 6, 21);
                g2.drawLine(10, 12, 10, 21);
                g2.drawLine(14, 12, 14, 21);
                g2.drawLine(18, 12, 18, 21);
                break;
                
            case "degree":
                // Award medal ribbon
                g2.drawOval(6, 3, 12, 12); // outer medal
                g2.drawOval(8, 5, 8, 8);   // inner details
                // Ribbons hanging down
                g2.drawPolyline(new int[]{8, 8, 12, 10}, new int[]{14, 21, 18, 14}, 4);
                g2.drawPolyline(new int[]{16, 16, 12, 14}, new int[]{14, 21, 18, 14}, 4);
                break;
                
            case "help":
                // Help circle question mark
                g2.drawOval(2, 2, 20, 20);
                g2.drawArc(9, 7, 6, 5, 0, 180);
                g2.drawPolyline(new int[]{15, 15, 12, 12}, new int[]{9, 12, 12, 15}, 4);
                g2.fillRect(11, 17, 2, 2);
                break;
                
            case "logout":
                // Box + exit arrow pointing left/right
                g2.drawPolyline(new int[]{9, 21, 21, 9, 9}, new int[]{3, 3, 21, 21, 21}, 5);
                g2.drawLine(2, 12, 15, 12);
                g2.drawPolyline(new int[]{11, 15, 11}, new int[]{8, 12, 16}, 3);
                break;
                
            case "generate":
                // Document file text
                g2.drawPolygon(new int[]{4, 14, 20, 20, 4}, new int[]{2, 2, 8, 22, 22}, 5);
                g2.drawPolyline(new int[]{14, 14, 20}, new int[]{2, 8, 8}, 3);
                g2.drawLine(8, 13, 16, 13);
                g2.drawLine(8, 17, 14, 17);
                break;

            case "search":
                // Search magnifying glass
                g2.drawOval(3, 3, 11, 11);
                g2.drawLine(11, 11, 21, 21);
                break;
                
            case "edit":
                // Pencil
                g2.drawPolygon(new int[]{18, 21, 7, 2, 4}, new int[]{3, 6, 20, 20, 17}, 5);
                g2.drawLine(15, 6, 18, 9);
                break;
                
            case "trash":
                // Trash bin
                g2.drawLine(4, 6, 20, 6);
                g2.drawPolyline(new int[]{9, 9, 15, 15}, new int[]{6, 3, 3, 6}, 4);
                g2.drawPolygon(new int[]{5, 19, 17, 7}, new int[]{6, 6, 21, 21}, 4);
                g2.drawLine(10, 10, 10, 17);
                g2.drawLine(14, 10, 14, 17);
                break;

            case "live":
                // Blinking or simple pulse dot
                g2.setColor(new Color(6, 182, 212));
                g2.fillOval(8, 8, 8, 8);
                break;

            default:
                // Fallback: simple dot
                g2.fillOval(10, 10, 4, 4);
                break;
        }
        
        g2.dispose();
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}

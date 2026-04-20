package Vist;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

// Clase de utilidades visuales: define colores, fuentes y crea componentes Swing con estilo uniforme
public class Theme {

    // --- COLORES PRINCIPALES ---
    // Color verde oscuro y sus variantes, usados en la barra lateral y botones primarios
    public static final Color PRIMARY = new Color(46, 125, 50);
    public static final Color PRIMARY_DARK = new Color(27, 94, 32);
    public static final Color PRIMARY_LIGHT = new Color(76, 175, 80);

    // Color naranja, usado en botones secundarios y detalles
    public static final Color SECONDARY = new Color(255, 152, 0);
    public static final Color SECONDARY_DARK = new Color(230, 126, 0);
    public static final Color SECONDARY_LIGHT = new Color(255, 183, 77);

    // Color verde azulado, usado como acento en estadísticas
    public static final Color ACCENT = new Color(0, 150, 136);

    // --- COLORES DE FONDO ---
    public static final Color BACKGROUND = new Color(250, 250, 250); // fondo general de los paneles
    public static final Color SURFACE = new Color(255, 255, 255);    // fondo de tarjetas y formularios
    public static final Color CARD_BG = new Color(255, 255, 255);    // fondo de las tarjetas

    // --- COLORES DE TEXTO ---
    public static final Color TEXT_PRIMARY = new Color(33, 33, 33);    // texto principal (casi negro)
    public static final Color TEXT_SECONDARY = new Color(117, 117, 117); // texto secundario (gris)
    public static final Color TEXT_LIGHT = new Color(255, 255, 255);   // texto blanco (sobre fondos oscuros)

    // --- COLORES DE ESTADO ---
    public static final Color ERROR = new Color(211, 47, 47);    // rojo para errores
    public static final Color WARNING = new Color(245, 124, 0);  // naranja para advertencias
    public static final Color SUCCESS = new Color(56, 142, 60);  // verde para éxito
    public static final Color INFO = new Color(33, 150, 243);    // azul para información

    // --- COLORES DE SALUD DE ANIMALES ---
    public static final Color SALUDABLE = new Color(76, 175, 80);
    public static final Color EN_TRATAMIENTO = new Color(255, 193, 7);
    public static final Color CUARENTENA = new Color(255, 152, 0);
    public static final Color CRITICO = new Color(244, 67, 54);

    // --- FUENTES ---
    // Se usa "Segoe UI" porque es legible y está disponible en Windows
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font BODY_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    // Radio de bordes redondeados en píxeles
    public static final int BORDER_RADIUS = 10;
    public static final int CARD_RADIUS = 15;

    // Configura el Look and Feel global de la aplicación
    public static void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Crea un JButton con fondo de color personalizado y bordes redondeados.
    // Se sobreescribe paintComponent para dibujar el fondo manualmente con Graphics2D,
    // porque el botón tiene setContentAreaFilled(false) para evitar el relleno por defecto.
    public static JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Cambia el color según el estado del botón
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }
                // Dibuja el fondo redondeado
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, BORDER_RADIUS, BORDER_RADIUS);
                g2.dispose();
                super.paintComponent(g); // dibuja el texto encima
            }
        };
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_LIGHT);
        button.setFocusPainted(false);   // sin rectángulo de foco
        button.setBorderPainted(false);  // sin borde por defecto
        button.setContentAreaFilled(false); // sin relleno por defecto (lo hacemos nosotros)
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // cursor de mano al pasar por encima
        button.setBorder(new EmptyBorder(12, 24, 12, 24)); // padding interior
        return button;
    }

    // Botón verde (acción principal)
    public static JButton createPrimaryButton(String text) {
        return createButton(text, PRIMARY);
    }

    // Botón naranja (acción secundaria), con texto oscuro para mejor contraste
    public static JButton createSecondaryButton(String text) {
        JButton btn = createButton(text, SECONDARY);
        btn.setForeground(TEXT_PRIMARY);
        return btn;
    }

    // Botón rojo (acción peligrosa, como eliminar)
    public static JButton createDangerButton(String text) {
        return createButton(text, ERROR);
    }

    // Botón verde claro (acción de confirmación)
    public static JButton createSuccessButton(String text) {
        return createButton(text, SUCCESS);
    }

    // Crea un JTextField con bordes redondeados dibujados manualmente.
    // setOpaque(false) permite que el fondo redondeado se vea correctamente.
    public static JTextField createTextField() {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, BORDER_RADIUS, BORDER_RADIUS);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setFont(BODY_FONT);
        // Borde compuesto: uno redondeado por fuera + padding interior
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(224, 224, 224), 1, true) {
                @Override
                public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getLineColor());
                    g2.drawRoundRect(x, y, width - 1, height - 1, BORDER_RADIUS, BORDER_RADIUS);
                    g2.dispose();
                }
            },
            new EmptyBorder(10, 14, 10, 14)
        ));
        field.setBackground(SURFACE);
        field.setOpaque(false); // necesario para que el fondo redondeado funcione
        return field;
    }

    // Igual que createTextField pero para contraseñas (muestra puntos en lugar de letras)
    public static JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, BORDER_RADIUS, BORDER_RADIUS);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setFont(BODY_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(224, 224, 224), 1, true) {
                @Override
                public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getLineColor());
                    g2.drawRoundRect(x, y, width - 1, height - 1, BORDER_RADIUS, BORDER_RADIUS);
                    g2.dispose();
                }
            },
            new EmptyBorder(10, 14, 10, 14)
        ));
        field.setBackground(SURFACE);
        field.setOpaque(false);
        return field;
    }

    // Crea un JComboBox (lista desplegable) con estilo personalizado
    public static JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, BORDER_RADIUS, BORDER_RADIUS);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        combo.setFont(BODY_FONT);
        combo.setBackground(SURFACE);
        combo.setBorder(new EmptyBorder(10, 14, 10, 14));
        return combo;
    }

    // Envuelve un componente en un JScrollPane con la barra de scroll personalizada
    public static JScrollPane createScrollPane(Component view) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setBorder(null); // sin borde exterior
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // velocidad de scroll
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        return scrollPane;
    }

    // Crea un JPanel con fondo blanco y bordes redondeados, usado como tarjeta de contenido
    public static JPanel createCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, CARD_RADIUS, CARD_RADIUS);
                g2.dispose();
            }
        };
        card.setBackground(CARD_BG);
        card.setOpaque(false); // necesario para que el fondo redondeado se vea
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        return card;
    }

    // Métodos de conveniencia para crear JLabel con fuente y color predefinidos
    public static JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    public static JLabel createTitleLabel(String text) {
        return createLabel(text, TITLE_FONT, TEXT_PRIMARY);
    }

    public static JLabel createSubtitleLabel(String text) {
        return createLabel(text, SUBTITLE_FONT, TEXT_PRIMARY);
    }

    public static JLabel createHeaderLabel(String text) {
        return createLabel(text, HEADER_FONT, TEXT_PRIMARY);
    }

    public static JLabel createBodyLabel(String text) {
        return createLabel(text, BODY_FONT, TEXT_SECONDARY);
    }

    // Barra de scroll personalizada: sin flechas, con thumb (pastilla) redondeado
    // Se extiende BasicScrollBarUI para sobreescribir solo las partes visuales
    public static class ModernScrollBarUI extends BasicScrollBarUI {
        private static final int SCROLL_BAR_WIDTH = 8;
        private static final Color SCROLL_BAR_COLOR = new Color(200, 200, 200);
        private static final Color SCROLL_BAR_HOVER_COLOR = new Color(150, 150, 150);

        @Override
        protected void configureScrollBarColors() {
            thumbColor = SCROLL_BAR_COLOR;
            thumbDarkShadowColor = SCROLL_BAR_COLOR;
            thumbHighlightColor = SCROLL_BAR_COLOR;
            trackColor = BACKGROUND;
            trackHighlightColor = BACKGROUND;
        }

        // Elimina los botones de flecha arriba/abajo de la barra de scroll
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        // Botón invisible de tamaño cero para reemplazar las flechas
        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setVisible(false);
            return button;
        }

        // Dibuja la pastilla de la barra de scroll con bordes redondeados
        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isThumbRollover() ? SCROLL_BAR_HOVER_COLOR : SCROLL_BAR_COLOR);
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, SCROLL_BAR_WIDTH, SCROLL_BAR_WIDTH);
            g2.dispose();
        }

        // No dibujamos la pista (fondo de la barra), queda transparente
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        }
    }

    // Panel con sombra simulada dibujada manualmente alrededor del contenido
    public static class ShadowPanel extends JPanel {
        private int shadowSize = 10;
        private float shadowOpacity = 0.15f;
        private Color shadowColor = Color.BLACK;

        public ShadowPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int shadowGap = 4;
            int width = getWidth() - shadowSize - shadowGap;
            int height = getHeight() - shadowSize - shadowGap;

            // Dibuja capas concéntricas cada vez más transparentes para simular la sombra
            for (int i = 0; i < shadowSize; i++) {
                g2.setColor(new Color(0, 0, 0, (int) (shadowOpacity * 255 * (1 - (float) i / shadowSize))));
                g2.drawRoundRect(i, i, width + shadowSize - i * 2, height + shadowSize - i * 2, CARD_RADIUS, CARD_RADIUS);
            }

            // Dibuja el fondo blanco del panel encima de la sombra
            g2.setColor(getBackground());
            g2.fillRoundRect(shadowSize / 2, shadowSize / 2, width, height, CARD_RADIUS, CARD_RADIUS);
            g2.dispose();

            super.paintComponent(g);
        }
    }
}
